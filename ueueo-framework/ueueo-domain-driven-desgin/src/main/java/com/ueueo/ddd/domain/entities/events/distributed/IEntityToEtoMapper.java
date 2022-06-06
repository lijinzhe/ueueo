package com.ueueo.ddd.domain.entities.events.distributed;

import com.ueueo.ddd.domain.entities.IEntity;

public interface IEntityToEtoMapper {
    Object map(IEntity entityObj);
}
