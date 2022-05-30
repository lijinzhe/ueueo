using System;
using System.Collections.Generic;

namespace Volo.Abp.Uow;

public class UnitOfWorkEventRecord
{
    public Object EventData;//  { get; }

    public Type EventType;//  { get; }

    public long EventOrder;//  { get; }

    public boolean UseOutbox;//  { get; }

    /**
     * Extra properties can be used if needed.
    */
    public Dictionary<String, Object> Properties;//  { get; } = new Dictionary<string, object>();

    public UnitOfWorkEventRecord(
        Type eventType,
        Object eventData,
        long eventOrder,
        boolean useOutbox = true)
    {
        EventType = eventType;
        EventData = eventData;
        EventOrder = eventOrder;
        UseOutbox = useOutbox;
    }
}
