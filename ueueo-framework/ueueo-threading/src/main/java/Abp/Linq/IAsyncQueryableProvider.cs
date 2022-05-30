using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Linq;

public interface IAsyncQueryableProvider
{
    boolean CanExecute<T>(IQueryable<T> queryable);

    #region Contains

    Task<bool> ContainsAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull T item,
        CancellationToken cancellationToken = default);

    #endregion

    #region Any/All

    Task<bool> AnyAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    Task<bool> AnyAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    Task<bool> AllAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Count/LongCount

    Task<int> CountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<int> CountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    Task<long> LongCountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<long> LongCountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region First/FirstOrDefault

    Task<T> FirstAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T> FirstAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    Task<T> FirstOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T> FirstOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Last/LastOrDefault

    Task<T> LastAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T> LastAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    Task<T> LastOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T> LastOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Single/SingleOrDefault

    Task<T> SingleAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T> SingleAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    Task<T> SingleOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    Task<T> SingleOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Min

    Task<T> MinAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<TResult> MinAsync<T, TResult>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Max

    Task<T> MaxAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<TResult> MaxAsync<T, TResult>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Sum

    Task<decimal> SumAsync(
        @Nonnull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    Task<decimal?> SumAsync(
        @Nonnull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    Task<decimal> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);


    Task<decimal?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    Task<int> SumAsync(
        @Nonnull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    Task<int?> SumAsync(
        @Nonnull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    Task<int> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);


    Task<int?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);


    Task<long> SumAsync(
        @Nonnull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    Task<long?> SumAsync(
        @Nonnull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    Task<long> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    Task<long?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    Task<double> SumAsync(
        @Nonnull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    Task<double?> SumAsync(
        @Nonnull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    Task<double> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    Task<double?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    Task<float> SumAsync(
        @Nonnull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    Task<float?> SumAsync(
        @Nonnull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    Task<float> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    Task<float?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Average

    Task<decimal> AverageAsync(
        @Nonnull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    Task<decimal?> AverageAsync(
        @Nonnull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    Task<decimal> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);

    Task<decimal?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    Task<double> AverageAsync(
        @Nonnull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    Task<double?> AverageAsync(
        @Nonnull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    Task<double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);

    Task<double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);

    Task<double> AverageAsync(
        @Nonnull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    Task<double?> AverageAsync(
        @Nonnull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    Task<double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    Task<double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    Task<double> AverageAsync(
        @Nonnull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    Task<double?> AverageAsync(
        @Nonnull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    Task<double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    Task<double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    Task<float> AverageAsync(
        @Nonnull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    Task<float?> AverageAsync(
        @Nonnull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    Task<float> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    Task<float?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region ToList/Array

    Task<List<T>> ToListAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    Task<T[]> ToArrayAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    #endregion
}
