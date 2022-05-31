package com.ueueo.eventbus.distributed;

import com.ueueo.ID;
import com.ueueo.data.objectextending.ExtraPropertyDictionary;
import com.ueueo.data.objectextending.IHasExtraProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.Date;

@Getter
public class IncomingEventInfo implements IHasExtraProperties
{
    @Setter
    @Getter
    public static int MaxEventNameLength = 256;// { get; set; } = 256;

    @Setter(AccessLevel.PROTECTED)
    public ExtraPropertyDictionary ExtraProperties ;//{ get; protected set; }

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
        Assert.notNull(eventName,"eventName must not null!");
        Assert.isTrue(!eventName.isEmpty(),"eventName must not empty!");
        Assert.isTrue(eventName.length()<=MaxEventNameLength,"eventName length must <= "+MaxEventNameLength);
        EventName = eventName;
        EventData = eventData;
        CreationTime = creationTime;
        ExtraProperties = new ExtraPropertyDictionary();
        this.SetDefaultsForExtraProperties();
    }
}
