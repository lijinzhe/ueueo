package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.domain.entities.events.distributed.EtoBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityEto extends EtoBase {
    private String entityType;

    private String keysAsString;

}
