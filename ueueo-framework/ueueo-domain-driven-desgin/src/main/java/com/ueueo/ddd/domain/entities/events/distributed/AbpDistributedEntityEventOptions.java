package com.ueueo.ddd.domain.entities.events.distributed;

import lombok.Data;

@Data
public class AbpDistributedEntityEventOptions {
    public IAutoEntityDistributedEventSelectorList autoEventSelectors;

    public EtoMappingDictionary etoMappings;

    public AbpDistributedEntityEventOptions() {
        autoEventSelectors = new AutoEntityDistributedEventSelectorList();
        etoMappings = new EtoMappingDictionary();
    }
}
