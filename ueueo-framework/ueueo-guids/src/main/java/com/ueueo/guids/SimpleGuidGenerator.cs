using System;

namespace Volo.Abp.Guids;

/**
 * Implements <see cref="IGuidGenerator"/> by using <see cref="Guid.NewGuid"/>.
*/
public class SimpleGuidGenerator : IGuidGenerator
{
    public static SimpleGuidGenerator Instance;//  { get; } = new SimpleGuidGenerator();

    public   ID Create()
    {
        return Guid.NewGuid();
    }
}
