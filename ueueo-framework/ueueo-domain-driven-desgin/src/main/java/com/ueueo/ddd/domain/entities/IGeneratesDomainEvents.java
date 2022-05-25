package com.ueueo.ddd.domain.entities;

import java.util.List;

/**
 * TODO: Re-consider this interface
 *
 * @author Lee
 * @date 2022-05-19 11:15
 */
public interface IGeneratesDomainEvents {
    List<DomainEventRecord> getLocalEvents();

    List<DomainEventRecord> getDistributedEvents();

    void clearLocalEvents();

    void clearDistributedEvents();
}
