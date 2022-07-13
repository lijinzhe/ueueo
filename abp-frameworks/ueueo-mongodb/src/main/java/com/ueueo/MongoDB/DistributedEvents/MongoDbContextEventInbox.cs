using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.Options;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using Volo.Abp.EventBus.Boxes;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.Timing;
using Volo.Abp.Uow;

namespace Volo.Abp.MongoDB.DistributedEvents;

public class MongoDbContextEventInbox<TMongoDbContext> : IMongoDbContextEventInbox<TMongoDbContext>
    where TMongoDbContext : IHasEventInbox
{
    protected IMongoDbContextProvider<TMongoDbContext> DbContextProvider;//  { get; }
    protected AbpEventBusBoxesOptions EventBusBoxesOptions;//  { get; }
    protected IClock Clock;//  { get; }

    public MongoDbContextEventInbox(
        IMongoDbContextProvider<TMongoDbContext> dbContextProvider,
        IClock clock,
        IOptions<AbpEventBusBoxesOptions> eventBusBoxesOptions)
    {
        DbContextProvider = dbContextProvider;
        Clock = clock;
        EventBusBoxesOptions = eventBusBoxesOptions.Value;
    }


    [UnitOfWork]
    public   void EnqueueAsync(IncomingEventInfo incomingEvent)
    {
        var dbContext = DbContextProvider.GetDbContextAsync();
        if (dbContext.SessionHandle != null)
        {
            dbContext.IncomingEvents.InsertOneAsync(
                dbContext.SessionHandle,
                new IncomingEventRecord(incomingEvent)
            );
        }
        else
        {
            dbContext.IncomingEvents.InsertOneAsync(
                new IncomingEventRecord(incomingEvent)
            );
        }
    }

    [UnitOfWork]
    public    List<IncomingEventInfo>> GetWaitingEventsAsync(int maxCount, CancellationToken cancellationToken = default)
    {
        var dbContext = DbContextProvider.GetDbContextAsync(cancellationToken);

        var outgoingEventRecords = dbContext
            .IncomingEvents
            .AsQueryable()
            .Where(x => !x.Processed)
            .OrderBy(x => x.CreationTime)
            .Take(maxCount)
            .ToListAsync(cancellationToken: cancellationToken);

        return outgoingEventRecords
            .Select(x => x.ToIncomingEventInfo())
            .ToList();
    }

    [UnitOfWork]
    public   void MarkAsProcessedAsync(Guid id)
    {
        var dbContext = DbContextProvider.GetDbContextAsync();

        var filter = Builders<IncomingEventRecord>.Filter.Eq(x => x.Id, id);
        var update = Builders<IncomingEventRecord>.Update.Set(x => x.Processed, true).Set(x => x.ProcessedTime, Clock.Now);

        if (dbContext.SessionHandle != null)
        {
            dbContext.IncomingEvents.UpdateOneAsync(dbContext.SessionHandle, filter, update);
        }
        else
        {
            dbContext.IncomingEvents.UpdateOneAsync(filter, update);
        }
    }

    [UnitOfWork]
    public    Boolean>  ExistsByMessageIdAsync(String messageId)
    {
        var dbContext = DbContextProvider.GetDbContextAsync();
        return dbContext.IncomingEvents.AsQueryable().AnyAsync(x => x.MessageId == messageId);
    }

    [UnitOfWork]
    public   void DeleteOldEventsAsync()
    {
        var dbContext = DbContextProvider.GetDbContextAsync();
        var timeToKeepEvents = Clock.Now - EventBusBoxesOptions.WaitTimeToDeleteProcessedInboxEvents;

        if (dbContext.SessionHandle != null)
        {
            dbContext.IncomingEvents.DeleteManyAsync(dbContext.SessionHandle, x => x.Processed && x.CreationTime < timeToKeepEvents);
        }
        else
        {
            dbContext.IncomingEvents.DeleteManyAsync(x => x.Processed && x.CreationTime < timeToKeepEvents);
        }
    }
}
