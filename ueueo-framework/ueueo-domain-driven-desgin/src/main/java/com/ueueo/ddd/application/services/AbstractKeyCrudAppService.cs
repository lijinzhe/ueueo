using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Volo.Abp.Application.Dtos;
using Volo.Abp.Domain.Entities;
using Volo.Abp.Domain.Repositories;
using Volo.Abp.MultiTenancy;
using Volo.Abp.ObjectMapping;

namespace Volo.Abp.Application.Services;

public abstract class AbstractKeyCrudAppService<TEntity, TEntityDto, TKey>
    : AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, PagedAndSortedResultRequestDto>
    //where TEntity : class, IEntity
{
    protected AbstractKeyCrudAppService(IRepository<TEntity> repository)
        : base(repository)
    {

    }
}

public abstract class AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, TGetListInput>
    : AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, TGetListInput, TEntityDto, TEntityDto>
    //where TEntity : class, IEntity
{
    protected AbstractKeyCrudAppService(IRepository<TEntity> repository)
        : base(repository)
    {

    }
}

public abstract class AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, TGetListInput, TCreateInput>
    : AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, TGetListInput, TCreateInput, TCreateInput>
    //where TEntity : class, IEntity
{
    protected AbstractKeyCrudAppService(IRepository<TEntity> repository)
        : base(repository)
    {

    }
}

public abstract class AbstractKeyCrudAppService<TEntity, TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput>
    : AbstractKeyCrudAppService<TEntity, TEntityDto, TEntityDto, TKey, TGetListInput, TCreateInput, TUpdateInput>
    //where TEntity : class, IEntity
{
    protected AbstractKeyCrudAppService(IRepository<TEntity> repository)
        : base(repository)
    {

    }

    protected override Task<TEntityDto> MapToGetListOutputDtoAsync(TEntity entity)
    {
        return MapToGetOutputDtoAsync(entity);
    }

    protected override TEntityDto MapToGetListOutputDto(TEntity entity)
    {
        return MapToGetOutputDto(entity);
    }
}

public abstract class AbstractKeyCrudAppService<TEntity, TGetOutputDto, TGetListOutputDto, TKey, TGetListInput, TCreateInput, TUpdateInput>
    : AbstractKeyReadOnlyAppService<TEntity, TGetOutputDto, TGetListOutputDto, TKey, TGetListInput>,
        ICrudAppService<TGetOutputDto, TGetListOutputDto, TKey, TGetListInput, TCreateInput, TUpdateInput>
    //where TEntity : class, IEntity
{
    protected IRepository<TEntity> Repository;//  { get; }

    protected   String CreatePolicyName;// { get; set; }

    protected   String UpdatePolicyName;// { get; set; }

    protected   String DeletePolicyName;// { get; set; }

    protected AbstractKeyCrudAppService(IRepository<TEntity> repository)
        : base(repository)
    {
        Repository = repository;
    }

    public    Task<TGetOutputDto> CreateAsync(TCreateInput input)
    {
        CheckCreatePolicyAsync();

        var entity = MapToEntityAsync(input);

        TryToSetTenantId(entity);

        Repository.InsertAsync(entity, autoSave: true);

        return MapToGetOutputDtoAsync(entity);
    }

    public    Task<TGetOutputDto> UpdateAsync(TKey id, TUpdateInput input)
    {
        CheckUpdatePolicyAsync();

        var entity = GetEntityByIdAsync(id);
        //TODO: Check if input has id different than given id and normalize if it's default value, throw ex otherwise
        MapToEntityAsync(input, entity);
        Repository.UpdateAsync(entity, autoSave: true);

        return MapToGetOutputDtoAsync(entity);
    }

    public   void DeleteAsync(TKey id)
    {
        CheckDeletePolicyAsync();

        DeleteByIdAsync(id);
    }

    protected abstract void DeleteByIdAsync(TKey id);

    protected   void CheckCreatePolicyAsync()
    {
        CheckPolicyAsync(CreatePolicyName);
    }

    protected   void CheckUpdatePolicyAsync()
    {
        CheckPolicyAsync(UpdatePolicyName);
    }

    protected   void CheckDeletePolicyAsync()
    {
        CheckPolicyAsync(DeletePolicyName);
    }

    /**
     * Maps <typeparamref name="TCreateInput"/> to <typeparamref name="TEntity"/> to create a new entity.
     * It uses <see cref="MapToEntity(TCreateInput)"/> by default.
     * It can be overriden for custom mapping.
     * Overriding this has higher priority than overriding the <see cref="MapToEntity(TCreateInput)"/>
    */
    protected   Task<TEntity> MapToEntityAsync(TCreateInput createInput)
    {
        return Task.FromResult(MapToEntity(createInput));
    }

    /**
     * Maps <typeparamref name="TCreateInput"/> to <typeparamref name="TEntity"/> to create a new entity.
     * It uses <see cref="IObjectMapper"/> by default.
     * It can be overriden for custom mapping.
    */
    protected   TEntity MapToEntity(TCreateInput createInput)
    {
        var entity = ObjectMapper.Map<TCreateInput, TEntity>(createInput);
        SetIdForGuids(entity);
        return entity;
    }

    /**
     * Sets Id value for the entity if <typeparamref name="TKey"/> is <see cref="Guid"/>.
     * It's used while creating a new entity.
    */
    protected   void SetIdForGuids(TEntity entity)
    {
        if (entity is IEntity<Guid> entityWithGuidId && entityWithGuidId.Id == Guid.Empty)
        {
            EntityHelper.TrySetId(
                entityWithGuidId,
                () => GuidGenerator.Create(),
                true
            );
        }
    }

    /**
     * Maps <typeparamref name="TUpdateInput"/> to <typeparamref name="TEntity"/> to update the entity.
     * It uses <see cref="MapToEntity(TUpdateInput, TEntity)"/> by default.
     * It can be overriden for custom mapping.
     * Overriding this has higher priority than overriding the <see cref="MapToEntity(TUpdateInput, TEntity)"/>
    */
    protected void MapToEntityAsync(TUpdateInput updateInput, TEntity entity)
    {
        MapToEntity(updateInput, entity);
        return Task.CompletedTask;
    }

    /**
     * Maps <typeparamref name="TUpdateInput"/> to <typeparamref name="TEntity"/> to update the entity.
     * It uses <see cref="IObjectMapper"/> by default.
     * It can be overriden for custom mapping.
    */
    protected   void MapToEntity(TUpdateInput updateInput, TEntity entity)
    {
        ObjectMapper.Map(updateInput, entity);
    }

    protected   void TryToSetTenantId(TEntity entity)
    {
        if (entity is IMultiTenant && HasTenantIdProperty(entity))
        {
            var tenantId = CurrentTenant.Id;

            if (!tenantId.HasValue)
            {
                return;
            }

            var propertyInfo = entity.GetType().GetProperty(nameof(IMultiTenant.TenantId));

            if (propertyInfo == null || propertyInfo.GetSetMethod(true) == null)
            {
                return;
            }

            propertyInfo.SetValue(entity, tenantId);
        }
    }

    protected   boolean HasTenantIdProperty(TEntity entity)
    {
        return entity.GetType().GetProperty(nameof(IMultiTenant.TenantId)) != null;
    }
}
