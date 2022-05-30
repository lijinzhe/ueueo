using System;

namespace Volo.Abp.Domain.Repositories.MemoryDb;

public interface IMemoryDbSerializer
{
    byte[] Serialize(Object obj);

    Object Deserialize(byte[] value, Type type);
}
