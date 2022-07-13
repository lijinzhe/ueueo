using System;

namespace Volo.Abp.MongoDB;

public class MongoCollectionAttribute : Attribute
{
    public String CollectionName;// { get; set; }

    public MongoCollectionAttribute()
    {

    }

    public MongoCollectionAttribute(String collectionName)
    {
        CollectionName = collectionName;
    }
}
