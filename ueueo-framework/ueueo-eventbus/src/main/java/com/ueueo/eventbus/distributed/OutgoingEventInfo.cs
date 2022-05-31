using System;
using Volo.Abp.Data;

namespace Volo.Abp.EventBus.Distributed;

public class OutgoingEventInfo : IHasExtraProperties
{
    public static int MaxEventNameLength;// { get; set; } = 256;

    public ExtraPropertyDictionary ExtraProperties { get; protected set; }

    public ID Id;//  { get; }

    public String EventName;//  { get; }

    public byte[] EventData;//  { get; }

    public Date CreationTime;//  { get; }

    protected OutgoingEventInfo()
    {
        ExtraProperties = new ExtraPropertyDictionary();
        this.SetDefaultsForExtraProperties();
    }

    public OutgoingEventInfo(
        ID id,
        String eventName,
        byte[] eventData,
        Date creationTime)
    {
        Id = id;
        EventName = Check.NotNullOrWhiteSpace(eventName, nameof(eventName), MaxEventNameLength);
        EventData = eventData;
        CreationTime = creationTime;
        ExtraProperties = new ExtraPropertyDictionary();
        this.SetDefaultsForExtraProperties();
    }
}
