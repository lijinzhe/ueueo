using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Linq;

public class AsyncQueryableExecuter : IAsyncQueryableExecuter, ISingletonDependency
{
    protected IEnumerable<IAsyncQueryableProvider> Providers;//  { get; }

    public AsyncQueryableExecuter(IEnumerable<IAsyncQueryableProvider> providers)
    {
        Providers = providers;
    }

    protected   IAsyncQueryableProvider FindProvider<T>(IQueryable<T> queryable)
    {
        return Providers.FirstOrDefault(p => p.CanExecute(queryable));
    }

    public Boolean>  ContainsAsync<T>(IQueryable<T> queryable, T item, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.ContainsAsync(queryable, item, cancellationToken)
            : Task.FromResult(queryable.Contains(item));
    }

    public Boolean>  AnyAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AnyAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Any());
    }

    public Boolean>  AnyAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AnyAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.Any(predicate));
    }

    public Boolean>  AllAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AllAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.All(predicate));
    }

    public int> CountAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.CountAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Count());
    }

    public int> CountAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.CountAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.Count(predicate));
    }

    public long> LongCountAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LongCountAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.LongCount());
    }

    public long> LongCountAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LongCountAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.LongCount(predicate));
    }

    public T> FirstAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.FirstAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.First());
    }

    public T> FirstAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.FirstAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.First(predicate));
    }

    public T> FirstOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.FirstOrDefaultAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.FirstOrDefault());
    }

    public T> FirstOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.FirstOrDefaultAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.FirstOrDefault(predicate));
    }

    public T> LastAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LastAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Last());
    }

    public T> LastAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LastAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.Last(predicate));
    }

    public T> LastOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LastOrDefaultAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.LastOrDefault());
    }

    public T> LastOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.LastOrDefaultAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.LastOrDefault(predicate));
    }

    public T> SingleAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SingleAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Single());
    }

    public T> SingleAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SingleAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.Single(predicate));
    }

    public T> SingleOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SingleOrDefaultAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.SingleOrDefault());
    }

    public T> SingleOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SingleOrDefaultAsync(queryable, predicate, cancellationToken)
            : Task.FromResult(queryable.SingleOrDefault(predicate));
    }

    public T> MinAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.MinAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Min());
    }

    public TResult> MinAsync<T, TResult>(IQueryable<T> queryable, Expression<Func<T, TResult>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.MinAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Min(selector));
    }

    public T> MaxAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.MaxAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Max());
    }

    public TResult> MaxAsync<T, TResult>(IQueryable<T> queryable, Expression<Func<T, TResult>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.MaxAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Max(selector));
    }

    public decimal> SumAsync(IQueryable<decimal> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public decimal?> SumAsync(IQueryable<decimal?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public decimal> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public decimal?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public int> SumAsync(IQueryable<int> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public int?> SumAsync(IQueryable<int?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public int> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, int>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public int?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, int?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public long> SumAsync(IQueryable<long> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public long?> SumAsync(IQueryable<long?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public long> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, long>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public long?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, long?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public double> SumAsync(IQueryable<double> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public double?> SumAsync(IQueryable<double?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public double> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, double>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public double?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, double?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public float> SumAsync(IQueryable<float> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public float?> SumAsync(IQueryable<float?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Sum());
    }

    public float> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, float>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public float?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, float?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.SumAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Sum(selector));
    }

    public decimal> AverageAsync(IQueryable<decimal> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public decimal?> AverageAsync(IQueryable<decimal?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public decimal> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public decimal?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double> AverageAsync(IQueryable<int> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double?> AverageAsync(IQueryable<int?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, int>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, int?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double> AverageAsync(IQueryable<long> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double?> AverageAsync(IQueryable<long?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, long>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, long?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double> AverageAsync(IQueryable<double> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double?> AverageAsync(IQueryable<double?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, double>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, double?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public float> AverageAsync(IQueryable<float> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public float?> AverageAsync(IQueryable<float?> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.Average());
    }

    public float> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, float>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public float?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, float?>> selector, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.AverageAsync(queryable, selector, cancellationToken)
            : Task.FromResult(queryable.Average(selector));
    }

    public List<T>> ToListAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.ToListAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.ToList());
    }

    public T[]> ToArrayAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        var provider = FindProvider(queryable);
        return provider != null
            ? provider.ToArrayAsync(queryable, cancellationToken)
            : Task.FromResult(queryable.ToArray());
    }
}
