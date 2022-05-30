using MongoDB.Bson.Serialization;
using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using System.Reflection;
using Microsoft.Extensions.Options;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Serializers;
using MongoDB.Driver;
using Volo.Abp.Reflection;
using Volo.Abp.Timing;

namespace Volo.Abp.MongoDB;

public class MongoModelBuilder : IMongoModelBuilder
{
    private readonly Dictionary<Type, Object> _entityModelBuilders;

    private static readonly Object SyncObj = new object();

    public MongoModelBuilder()
    {
        _entityModelBuilders = new Dictionary<Type, Object>();
    }

    public   MongoDbContextModel Build(AbpMongoDbContext dbContext)
    {
        lock (SyncObj)
        {
            var useAbpClockHandleDateTime = dbContext.LazyServiceProvider.LazyGetRequiredService<IOptions<AbpMongoDbOptions>>().Value.UseAbpClockHandleDateTime;

            var entityModels = _entityModelBuilders
                .Select(x => x.Value)
                .Cast<IMongoEntityModel>()
                .ToImmutableDictionary(x => x.EntityType, x => x);

            var baseClasses = new List<Type>();

            for (var entityModel in entityModels.Values)
            {
                var map = entityModel.As<IHasBsonClassMap>().GetMap();

                if (useAbpClockHandleDateTime)
                {
                    var dateTimePropertyInfos = entityModel.EntityType.GetProperties(BindingFlags.DeclaredOnly | BindingFlags.Instance | BindingFlags.NonPublic | BindingFlags.Public)
                        .Where(property =>
                            (property.PropertyType == typeof(DateTime) ||
                             property.PropertyType == typeof(DateTime?)) &&
                            property.CanWrite
                        ).ToList();

                    dateTimePropertyInfos.ForEach(property =>
                    {
                        var disableDateTimeNormalization =
                            entityModel.EntityType.IsDefined(typeof(DisableDateTimeNormalizationAttribute), true) ||
                            ReflectionHelper.GetSingleAttributeOfMemberOrDeclaringTypeOrDefault<DisableDateTimeNormalizationAttribute>(property) != null;

                        if (property.PropertyType == typeof(DateTime?))
                        {
                            map.MapProperty(property.Name).SetSerializer(new NullableSerializer<DateTime>().WithSerializer(new AbpMongoDbDateTimeSerializer(GetDateTimeKind(dbContext), disableDateTimeNormalization)));
                        }
                        else
                        {
                            map.MapProperty(property.Name).SetSerializer(new AbpMongoDbDateTimeSerializer(GetDateTimeKind(dbContext), disableDateTimeNormalization));
                        }
                    });
                }

                if (!BsonClassMap.IsClassMapRegistered(map.ClassType))
                {
                    BsonClassMap.RegisterClassMap(map);
                }

                baseClasses.AddRange(entityModel.EntityType.GetBaseClasses(includeObject: false));

                CreateCollectionIfNotExists(dbContext, entityModel.CollectionName);
            }

            baseClasses = baseClasses.Distinct().ToList();

            for (var baseClass in baseClasses)
            {
                if (!BsonClassMap.IsClassMapRegistered(baseClass))
                {
                    var map = new BsonClassMap(baseClass);
                    map.ConfigureAbpConventions();

                    if (useAbpClockHandleDateTime)
                    {
                        var dateTimePropertyInfos = baseClass.GetProperties(BindingFlags.DeclaredOnly | BindingFlags.Instance | BindingFlags.NonPublic | BindingFlags.Public)
                            .Where(property =>
                                (property.PropertyType == typeof(DateTime) ||
                                 property.PropertyType == typeof(DateTime?)) &&
                                property.CanWrite
                            ).ToList();

                        dateTimePropertyInfos.ForEach(property =>
                        {
                            if (property.PropertyType == typeof(DateTime?))
                            {
                                map.MapProperty(property.Name).SetSerializer(new NullableSerializer<DateTime>().WithSerializer(new AbpMongoDbDateTimeSerializer(GetDateTimeKind(dbContext), false)));
                            }
                            else
                            {
                                map.MapProperty(property.Name).SetSerializer(new AbpMongoDbDateTimeSerializer(GetDateTimeKind(dbContext), false));
                            }
                        });
                    }

                    BsonClassMap.RegisterClassMap(map);
                }
            }

            return new MongoDbContextModel(entityModels);
        }
    }

    private DateTimeKind GetDateTimeKind(AbpMongoDbContext dbContext)
    {
        return dbContext.LazyServiceProvider.LazyGetRequiredService<IOptions<AbpClockOptions>>().Value.Kind;
    }

    public   void Entity<TEntity>(Action<IMongoEntityModelBuilder<TEntity>> buildAction = null)
    {
        var model = (IMongoEntityModelBuilder<TEntity>)_entityModelBuilders.GetOrAdd(
            typeof(TEntity),
            () => new MongoEntityModelBuilder<TEntity>()
        );

        buildAction?.Invoke(model);
    }

    public   void Entity(Type entityType, Action<IMongoEntityModelBuilder> buildAction = null)
    {
        Objects.requireNonNull(entityType, nameof(entityType));

        var model = (IMongoEntityModelBuilder)_entityModelBuilders.GetOrAdd(
            entityType,
            () => (IMongoEntityModelBuilder)Activator.CreateInstance(
                typeof(MongoEntityModelBuilder<>).MakeGenericType(entityType)
            )
        );

        buildAction?.Invoke(model);
    }

    public   IReadOnlyList<IMongoEntityModel> GetEntities()
    {
        return _entityModelBuilders.Values.Cast<IMongoEntityModel>().ToImmutableList();
    }

    protected   void CreateCollectionIfNotExists(AbpMongoDbContext dbContext, String collectionName)
    {
        var filter = new BsonDocument("name", collectionName);
        var options = new ListCollectionNamesOptions { Filter = filter };

        if (!dbContext.Database.ListCollectionNames(options).Any())
        {
            dbContext.Database.CreateCollection(collectionName);
        }
    }
}
