using Volo.Abp.Timing;

namespace Volo.Abp.MongoDB;

public class AbpMongoDbOptions
{
    /**
     * Serializer the datetime based on <see cref="AbpClockOptions.Kind"/> in MongoDb.
     * Default: true.
    */
    public boolean UseAbpClockHandleDateTime;// { get; set; }

    public AbpMongoDbOptions()
    {
        UseAbpClockHandleDateTime = true;
    }
}
