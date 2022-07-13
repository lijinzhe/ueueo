using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using Volo.Abp.EventBus.Distributed;
using Volo.Abp.Uow;

namespace Volo.Abp.MongoDB.DistributedEvents;

public class MongoDbContextEventOutbox<TMongoDbContext> : IMongoDbContextEventOutbox<TMongoDbContext>
    where TMongoDbContext : IHasEventOutbox
{
    protected IMongoDbContextProvider<TMongoDbContext> MongoDbContextProvider;//  { get; }

    public MongoDbContextEventOutbox(IMongoDbContextProvider<TMongoDbContext> mongoDbContextProvider)
    {
        MongoDbContextProvider = mongoDbContextProvider;
    }

    [UnitOfWork]
    public   void EnqueueAsync(OutgoingEventInfo outgoingEvent)
    {
        var dbContext = (IHasEventOutbox)MongoDbContextProvider.GetDbContextAsync();
        if (dbContext.SessionHandle != null)
        {
            dbContext.OutgoingEvents.InsertOneAsync(
                dbContext.SessionHandle,
                new OutgoingEventRecord(outgoingEvent)
            );
        }
        else
        {
            dbContext.OutgoingEvents.InsertOneAsync(
                new OutgoingEventRecord(outgoingEvent)
            );
        }
    }

    [UnitOfWork]
    public    List<OutgoingEventInfo>> GetWaitingEventsAsync(int maxCount, CancellationToken cancellationToken = default)
    {
        var dbContext = (IHasEventOutbox)MongoDbContextProvider.GetDbContextAsync(cancellationToken);

        var outgoingEventRecords = dbContext
            .OutgoingEvents.AsQueryable()
            .OrderBy(x => x.CreationTime)
            .Take(maxCount)
            .ToListAsync(cancellationToken: cancellationToken);

        return outgoingEventRecords
            .Select(x => x.ToOutgoingEventInfo())
            .ToList();
    }

    [UnitOfWork]
    public   void DeleteAsync(Guid id)
    {
        var dbContext = (IHasEventOutbox)MongoDbContextProvider.GetDbContextAsync();
        if (dbContext.SessionHandle != null)
        {
            dbContext.OutgoingEvents.DeleteOneAsync(dbContext.SessionHandle, x => x.Id.Equals(id));
        }
        else
        {
            dbContext.OutgoingEvents.DeleteOneAsync(x => x.Id.Equals(id));
        }
    }

    [UnitOfWork]
    public   void DeleteManyAsync(IEnumerable<Guid> ids)
    {
        var dbContext = (IHasEventOutbox)MongoDbContextProvider.GetDbContextAsync();
        if (dbContext.SessionHandle != null)
        {
            dbContext.OutgoingEvents.DeleteManyAsync(dbContext.SessionHandle, x => ids.Contains(x.Id));
        }
        else
        {
            dbContext.OutgoingEvents.DeleteManyAsync(x => ids.Contains(x.Id));
        }
    }
}
