using System;

namespace Volo.Abp.Domain.Entities.Events.Distributed;

[Serializable]
public class EntityEto : EtoBase
{
    public String EntityType;// { get; set; }

    public String KeysAsString;// { get; set; }

    public EntityEto()
    {

    }

    public EntityEto(String entityType, String keysAsString)
    {
        EntityType = entityType;
        KeysAsString = keysAsString;
    }
}
