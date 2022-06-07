package com.ueueo.eventbus.distributed;

import com.ueueo.ID;
import com.ueueo.data.objectextending.ExtraPropertyDictionary;
import com.ueueo.data.objectextending.IHasExtraProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Date;

public class OutgoingEventInfo implements IHasExtraProperties {
    public static int MaxEventNameLength = 256;

    private ID id;

    private String eventName;

    private byte[] eventData;

    private Date creationTime;

    private ExtraPropertyDictionary extraProperties;

    protected OutgoingEventInfo() {
        extraProperties = new ExtraPropertyDictionary();
        IHasExtraProperties.Extensions.setDefaultsForExtraProperties(this, null);
    }

    public OutgoingEventInfo(ID id, String eventName, byte[] eventData, Date creationTime) {
        this.id = id;
        Assert.isTrue(StringUtils.isNotBlank(eventName), "EventName must not empty!");
        Assert.isTrue(eventName.length() <= MaxEventNameLength, "EventName must <= " + MaxEventNameLength);
        this.eventName = eventName;
        this.eventData = eventData;
        this.creationTime = creationTime;
        this.extraProperties = new ExtraPropertyDictionary();
        IHasExtraProperties.Extensions.setDefaultsForExtraProperties(this, null);
    }

    @Override
    public ExtraPropertyDictionary getExtraProperties() {
        return extraProperties;
    }

    protected void setExtraProperties(ExtraPropertyDictionary extraProperties) {
        this.extraProperties = extraProperties;
    }

    public ID getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public byte[] getEventData() {
        return eventData;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
