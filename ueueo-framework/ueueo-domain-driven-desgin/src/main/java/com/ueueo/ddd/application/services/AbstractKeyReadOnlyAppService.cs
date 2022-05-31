using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Dynamic.Core;
using System.Threading.Tasks;
using Volo.Abp.Application.Dtos;
using Volo.Abp.Auditing;
using Volo.Abp.Domain.Entities;
using Volo.Abp.Domain.Repositories;
using Volo.Abp.ObjectMapping;

namespace Volo.Abp.Application.Services;

public abstract class AbstractKeyReadOnlyAppService<TEntity, TEntityDto, TKey>
    : AbstractKeyReadOnlyAppService<TEntity, TEntityDto, TEntityDto, TKey, PagedAndSortedResultRequestDto>
    //where TEntity : class, IEntity
{
    protected AbstractKeyReadOnlyAppService(IReadOnlyRepository<TEntity> repository)
        : base(repository)
    {

    }
}

public abstract class AbstractKeyReadOnlyAppService<TEntity, TEntityDto, TKey, TGetListInput>
    : AbstractKeyReadOnlyAppService<TEntity, TEntityDto, TEntityDto, TKey, TGetListInput>
    //where TEntity : class, IEntity
{
    protected AbstractKeyReadOnlyAppService(IReadOnlyRepository<TEntity> repository)
        : base(repository)
    {

    }
}

public abstract class AbstractKeyReadOnlyAppService<TEntity, TGetOutputDto, TGetListOutputDto, TKey, TGetListInput>
    : ApplicationService
    , IReadOnlyAppService<TGetOutputDto, TGetListOutputDto, TKey, TGetListInput>
    //where TEntity : class, IEntity
{
    protected IReadOnlyRepository<TEntity> ReadOnlyRepository;//  { get; }

    protected   String GetPolicyName;// { get; set; }

    protected   String GetListPolicyName;// { get; set; }

    protected AbstractKeyReadOnlyAppService(IReadOnlyRepository<TEntity> repository)
    {
        ReadOnlyRepository = repository;
    }

    public    TGetOutputDto> GetAsync(TKey id)
    {
        CheckGetPolicyAsync();

        var entity = GetEntityByIdAsync(id);

        return MapToGetOutputDtoAsync(entity);
    }

    public    PagedResultDto<TGetListOutputDto>> GetListAsync(TGetListInput input)
    {
        CheckGetListPolicyAsync();

        var query = CreateFilteredQueryAsync(input);

        var totalCount = AsyncExecuter.CountAsync(query);

        query = ApplySorting(query, input);
        query = ApplyPaging(query, input);

        var entities = AsyncExecuter.ToListAsync(query);
        var entityDtos = MapToGetListOutputDtosAsync(entities);

        return new PagedResultDto<TGetListOutputDto>(
            totalCount,
            entityDtos
        );
    }

    protected abstract TEntity> GetEntityByIdAsync(TKey id);

    protected   void CheckGetPolicyAsync()
    {
        CheckPolicyAsync(GetPolicyName);
    }

    protected   void CheckGetListPolicyAsync()
    {
        CheckPolicyAsync(GetListPolicyName);
    }

    /**
     * Should apply sorting if needed.
    *
     * <param name="query">The query.</param>
     * <param name="input">The input.</param>
     */
    protected   IQueryable<TEntity> ApplySorting(IQueryable<TEntity> query, TGetListInput input)
    {
        //Try to sort query if available
        if (input is ISortedResultRequest sortInput)
        {
            if (!sortInput.Sorting.IsNullOrWhiteSpace())
            {
                return query.OrderBy(sortInput.Sorting);
            }
        }

        //IQueryable.Task requires sorting, so we should sort if Take will be used.
        if (input is ILimitedResultRequest)
        {
            return ApplyDefaultSorting(query);
        }

        //No sorting
        return query;
    }

    /**
     * Applies sorting if no sorting specified but a limited result requested.
    *
     * <param name="query">The query.</param>
     */
    protected   IQueryable<TEntity> ApplyDefaultSorting(IQueryable<TEntity> query)
    {
        if (typeof(TEntity).IsAssignableTo<IHasCreationTime>())
        {
            return query.OrderByDescending(e => ((IHasCreationTime)e).CreationTime);
        }

        throw new AbpException("No sorting specified but this query requires sorting. Override the ApplyDefaultSorting method for your application service derived from AbstractKeyReadOnlyAppService!");
    }

    /**
     * Should apply paging if needed.
    *
     * <param name="query">The query.</param>
     * <param name="input">The input.</param>
     */
    protected   IQueryable<TEntity> ApplyPaging(IQueryable<TEntity> query, TGetListInput input)
    {
        //Try to use paging if available
        if (input is IPagedResultRequest pagedInput)
        {
            return query.PageBy(pagedInput);
        }

        //Try to limit query result if available
        if (input is ILimitedResultRequest limitedInput)
        {
            return query.Take(limitedInput.MaxResultCount);
        }

        //No paging
        return query;
    }

    /**
     * This method should create <see cref="IQueryable{TEntity}"/> based on given input.
     * It should filter query if needed, but should not do sorting or paging.
     * Sorting should be done in <see cref="ApplySorting"/> and paging should be done in <see cref="ApplyPaging"/>
     * methods.
    *
     * <param name="input">The input.</param>
     */
    protected    IQueryable<TEntity>> CreateFilteredQueryAsync(TGetListInput input)
    {
        return ReadOnlyRepository.GetQueryableAsync();
    }

    /**
     * Maps <typeparamref name="TEntity"/> to <typeparamref name="TGetOutputDto"/>.
     * It internally calls the <see cref="MapToGetOutputDto"/> by default.
     * It can be overriden for custom mapping.
     * Overriding this has higher priority than overriding the <see cref="MapToGetOutputDto"/>
    */
    protected   TGetOutputDto> MapToGetOutputDtoAsync(TEntity entity)
    {
        return Task.FromResult(MapToGetOutputDto(entity));
    }

    /**
     * Maps <typeparamref name="TEntity"/> to <typeparamref name="TGetOutputDto"/>.
     * It uses <see cref="IObjectMapper"/> by default.
     * It can be overriden for custom mapping.
    */
    protected   TGetOutputDto MapToGetOutputDto(TEntity entity)
    {
        return ObjectMapper.Map<TEntity, TGetOutputDto>(entity);
    }

    /**
     * Maps a list of <typeparamref name="TEntity"/> to <typeparamref name="TGetListOutputDto"/> objects.
     * It uses <see cref="MapToGetListOutputDtoAsync"/> method for each item in the list.
    */
    protected    List<TGetListOutputDto>> MapToGetListOutputDtosAsync(List<TEntity> entities)
    {
        var dtos = new List<TGetListOutputDto>();

        for (var entity in entities)
        {
            dtos.Add(MapToGetListOutputDtoAsync(entity));
        }

        return dtos;
    }

    /**
     * Maps <typeparamref name="TEntity"/> to <typeparamref name="TGetListOutputDto"/>.
     * It internally calls the <see cref="MapToGetListOutputDto"/> by default.
     * It can be overriden for custom mapping.
     * Overriding this has higher priority than overriding the <see cref="MapToGetListOutputDto"/>
    */
    protected   TGetListOutputDto> MapToGetListOutputDtoAsync(TEntity entity)
    {
        return Task.FromResult(MapToGetListOutputDto(entity));
    }

    /**
     * Maps <typeparamref name="TEntity"/> to <typeparamref name="TGetListOutputDto"/>.
     * It uses <see cref="IObjectMapper"/> by default.
     * It can be overriden for custom mapping.
    */
    protected   TGetListOutputDto MapToGetListOutputDto(TEntity entity)
    {
        return ObjectMapper.Map<TEntity, TGetListOutputDto>(entity);
    }
}
