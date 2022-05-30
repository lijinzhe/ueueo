using System.Collections.Generic;
using MongoDB.Driver;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.MongoDB;

public abstract class AbpMongoDbContext : IAbpMongoDbContext, ITransientDependency
{
    public IAbpLazyServiceProvider LazyServiceProvider;// { get; set; }

    public IMongoModelSource ModelSource;// { get; set; }

    public IMongoClient Client ;// { get; private set; }

    public IMongoDatabase Database ;// { get; private set; }

    public IClientSessionHandle SessionHandle ;// { get; private set; }

    protected internal   void CreateModel(IMongoModelBuilder modelBuilder)
    {

    }

    public   void InitializeDatabase(IMongoDatabase database, IMongoClient client, IClientSessionHandle sessionHandle)
    {
        Database = database;
        Client = client;
        SessionHandle = sessionHandle;
    }

    public   IMongoCollection<T> Collection<T>()
    {
        return Database.GetCollection<T>(GetCollectionName<T>());
    }

    public   void InitializeCollections(IMongoDatabase database)
    {
        Database = database;
        ModelSource.GetModel(this);
    }

    protected   String GetCollectionName<T>()
    {
        return GetEntityModel<T>().CollectionName;
    }

    protected   IMongoEntityModel GetEntityModel<TEntity>()
    {
        var model = ModelSource.GetModel(this).Entities.GetOrDefault(typeof(TEntity));

        if (model == null)
        {
            throw new AbpException("Could not find a model for given entity type: " + typeof(TEntity).AssemblyQualifiedName);
        }

        return model;
    }
}
