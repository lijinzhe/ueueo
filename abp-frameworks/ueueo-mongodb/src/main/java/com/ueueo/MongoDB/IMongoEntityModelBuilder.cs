using System;
using MongoDB.Bson.Serialization;

namespace Volo.Abp.MongoDB;

public interface IMongoEntityModelBuilder<TEntity>
{
    Type EntityType;//  { get; }

    String CollectionName;// { get; set; }

    BsonClassMap<TEntity> BsonMap;//  { get; }
}

public interface IMongoEntityModelBuilder
{
    Type EntityType;//  { get; }

    String CollectionName;// { get; set; }

    BsonClassMap BsonMap;//  { get; }
}
