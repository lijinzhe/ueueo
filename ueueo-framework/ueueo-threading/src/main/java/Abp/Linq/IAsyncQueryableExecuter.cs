using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using JetBrains.Annotations;

namespace Volo.Abp.Linq;

public interface IAsyncQueryableExecuter
{
    #region Contains

    Boolean>  ContainsAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull T item,
        CancellationToken cancellationToken = default);

    #endregion

    #region Any/All

    Boolean>  AnyAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    Boolean>  AnyAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    Boolean>  AllAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Count/LongCount

    int> CountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    int> CountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    long> LongCountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    long> LongCountAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region First/FirstOrDefault

    T> FirstAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> FirstAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    T> FirstOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> FirstOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Last/LastOrDefault

    T> LastAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> LastAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    T> LastOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> LastOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Single/SingleOrDefault

    T> SingleAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> SingleAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    T> SingleOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    T> SingleOrDefaultAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Min

    T> MinAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    TResult> MinAsync<T, TResult>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Max

    T> MaxAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    TResult> MaxAsync<T, TResult>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Sum

    decimal> SumAsync(
        @Nonnull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    decimal?> SumAsync(
        @Nonnull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    decimal> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);


    decimal?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    int> SumAsync(
        @Nonnull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    int?> SumAsync(
        @Nonnull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    int> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);


    int?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);


    long> SumAsync(
        @Nonnull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    long?> SumAsync(
        @Nonnull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    long> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    long?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    double> SumAsync(
        @Nonnull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    double?> SumAsync(
        @Nonnull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    double> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    double?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    float> SumAsync(
        @Nonnull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    float?> SumAsync(
        @Nonnull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    float> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    float?> SumAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Average

    decimal> AverageAsync(
        @Nonnull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    decimal?> AverageAsync(
        @Nonnull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    decimal> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);

    decimal?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    double> AverageAsync(
        @Nonnull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @Nonnull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);

    double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);

    double> AverageAsync(
        @Nonnull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @Nonnull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    double> AverageAsync(
        @Nonnull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @Nonnull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    double?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    float> AverageAsync(
        @Nonnull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    float?> AverageAsync(
        @Nonnull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    float> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    float?> AverageAsync<T>(
        @Nonnull IQueryable<T> queryable,
        @Nonnull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region ToList/Array

    List<T>> ToListAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T[]> ToArrayAsync<T>(
        @Nonnull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    #endregion
}
