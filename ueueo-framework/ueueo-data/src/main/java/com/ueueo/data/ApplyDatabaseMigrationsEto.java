package com.ueueo.data;

import com.ueueo.domain.entities.events.distributed.EtoBase;
import com.ueueo.eventbus.EventName;

/**
 * @author Lee
 * @date 2022-05-29 14:53
 */
@EventName(name = "abp.data.apply_database_migrations")
public class ApplyDatabaseMigrationsEto extends EtoBase {

}
