using System;
using Volo.Abp.Data;

namespace Volo.Abp.EventBus.Distributed;

public class IncomingEventInfo : IHasExtraProperties
{
    public static int MaxEventNameLength;// { get; set; } = 256;

    public ExtraPropertyDictionary ExtraProperties { get; protected set; }

    public ID Id;//  { get; }

    public String MessageId;//  { get; }

    public String EventName;//  { get; }

    public byte[] EventData;//  { get; }

    public Date CreationTime;//  { get; }

    protected IncomingEventInfo()
    {
        ExtraProperties = new ExtraPropertyDictionary();
        this.SetDefaultsForExtraProperties();
    }

    public IncomingEventInfo(
        ID id,
        String messageId,
        String eventName,
        byte[] eventData,
        Date creationTime)
    {
        Id = id;
        MessageId = messageId;
        EventName = Check.NotNullOrWhiteSpace(eventName, nameof(eventName), MaxEventNameLength);
        EventData = eventData;
        CreationTime = creationTime;
        ExtraProperties = new ExtraPropertyDictionary();
        this.SetDefaultsForExtraProperties();
    }
}
