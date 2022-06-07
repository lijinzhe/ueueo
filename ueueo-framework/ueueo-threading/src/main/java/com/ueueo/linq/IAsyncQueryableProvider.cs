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

    Boolean>  ContainsAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull T item,
        CancellationToken cancellationToken = default);

    #endregion

    #region Any/All

    Boolean>  AnyAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    Boolean>  AnyAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    Boolean>  AllAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Count/LongCount

    int> CountAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    int> CountAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    long> LongCountAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    long> LongCountAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region First/FirstOrDefault

    T> FirstAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> FirstAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    T> FirstOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> FirstOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Last/LastOrDefault

    T> LastAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> LastAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);


    T> LastOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> LastOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Single/SingleOrDefault

    T> SingleAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T> SingleAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    T> SingleOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    T> SingleOrDefaultAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default);

    #endregion

    #region Min

    T> MinAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    TResult> MinAsync<T, TResult>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Max

    T> MaxAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    TResult> MaxAsync<T, TResult>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, TResult>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Sum

    decimal> SumAsync(
        @NonNull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    decimal?> SumAsync(
        @NonNull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    decimal> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);


    decimal?> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    int> SumAsync(
        @NonNull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    int?> SumAsync(
        @NonNull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    int> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);


    int?> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);


    long> SumAsync(
        @NonNull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    long?> SumAsync(
        @NonNull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    long> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    long?> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    double> SumAsync(
        @NonNull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    double?> SumAsync(
        @NonNull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    double> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    double?> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    float> SumAsync(
        @NonNull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    float?> SumAsync(
        @NonNull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    float> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    float?> SumAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region Average

    decimal> AverageAsync(
        @NonNull IQueryable<decimal> source,
        CancellationToken cancellationToken = default);


    decimal?> AverageAsync(
        @NonNull IQueryable<decimal?> source,
        CancellationToken cancellationToken = default);


    decimal> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, decimal>> selector,
        CancellationToken cancellationToken = default);

    decimal?> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, decimal?>> selector,
        CancellationToken cancellationToken = default);


    double> AverageAsync(
        @NonNull IQueryable<int> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @NonNull IQueryable<int?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, int>> selector,
        CancellationToken cancellationToken = default);

    double?> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, int?>> selector,
        CancellationToken cancellationToken = default);

    double> AverageAsync(
        @NonNull IQueryable<long> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @NonNull IQueryable<long?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, long>> selector,
        CancellationToken cancellationToken = default);


    double?> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, long?>> selector,
        CancellationToken cancellationToken = default);


    double> AverageAsync(
        @NonNull IQueryable<double> source,
        CancellationToken cancellationToken = default);


    double?> AverageAsync(
        @NonNull IQueryable<double?> source,
        CancellationToken cancellationToken = default);


    double> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, double>> selector,
        CancellationToken cancellationToken = default);


    double?> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, double?>> selector,
        CancellationToken cancellationToken = default);


    float> AverageAsync(
        @NonNull IQueryable<float> source,
        CancellationToken cancellationToken = default);


    float?> AverageAsync(
        @NonNull IQueryable<float?> source,
        CancellationToken cancellationToken = default);


    float> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, float>> selector,
        CancellationToken cancellationToken = default);


    float?> AverageAsync<T>(
        @NonNull IQueryable<T> queryable,
        @NonNull Expression<Func<T, float?>> selector,
        CancellationToken cancellationToken = default);

    #endregion

    #region ToList/Array

    List<T>> ToListAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);


    T[]> ToArrayAsync<T>(
        @NonNull IQueryable<T> queryable,
        CancellationToken cancellationToken = default);

    #endregion
}
