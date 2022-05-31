using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Linq;
using MongoDB.Driver;
using MongoDB.Driver.Linq;
using Volo.Abp.DynamicProxy;

namespace Volo.Abp.MongoDB;

public class MongoDbAsyncQueryableProvider : IAsyncQueryableProvider, ISingletonDependency
{
    public boolean CanExecute<T>(IQueryable<T> queryable)
    {
        return ProxyHelper.UnProxy(queryable) is IMongoQueryable<T>;
    }

    protected   IMongoQueryable<T> GetMongoQueryable<T>(IQueryable<T> queryable)
    {
        return ProxyHelper.UnProxy(queryable).As<IMongoQueryable<T>>();
    }

    public Boolean>  ContainsAsync<T>(IQueryable<T> queryable, T item, CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).Contains(item));
    }

    public Boolean>  AnyAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AnyAsync(cancellationToken);
    }

    public Boolean>  AnyAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AnyAsync(predicate, cancellationToken);
    }

    public Boolean>  AllAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).All(predicate));
    }

    public int> CountAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).CountAsync(cancellationToken);
    }

    public int> CountAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).CountAsync(predicate, cancellationToken);
    }

    public long> LongCountAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).LongCountAsync(cancellationToken);
    }

    public long> LongCountAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).LongCountAsync(predicate, cancellationToken);
    }

    public T> FirstAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).FirstAsync(cancellationToken);
    }

    public T> FirstAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).FirstAsync(predicate, cancellationToken);
    }

    public T> FirstOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).FirstOrDefaultAsync(cancellationToken);
    }

    public T> FirstOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).FirstOrDefaultAsync(predicate, cancellationToken);
    }

    public T> LastAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).Last());
    }

    public T> LastAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).Last(predicate));
    }

    public T> LastOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).LastOrDefault());
    }

    public T> LastOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        return Task.FromResult(GetMongoQueryable(queryable).LastOrDefault(predicate));
    }

    public T> SingleAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SingleAsync(cancellationToken);
    }

    public T> SingleAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SingleAsync(predicate, cancellationToken);
    }

    public T> SingleOrDefaultAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SingleOrDefaultAsync(cancellationToken);
    }

    public T> SingleOrDefaultAsync<T>(IQueryable<T> queryable, Expression<Func<T, bool>> predicate,
        CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SingleOrDefaultAsync(predicate, cancellationToken);
    }

    public T> MinAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).MinAsync(cancellationToken);
    }

    public TResult> MinAsync<T, TResult>(IQueryable<T> queryable, Expression<Func<T, TResult>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).MinAsync(selector, cancellationToken);
    }

    public T> MaxAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).MaxAsync(cancellationToken);
    }

    public TResult> MaxAsync<T, TResult>(IQueryable<T> queryable, Expression<Func<T, TResult>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).MaxAsync(selector, cancellationToken);
    }

    public decimal> SumAsync(IQueryable<decimal> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public decimal?> SumAsync(IQueryable<decimal?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public decimal> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public decimal?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public int> SumAsync(IQueryable<int> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public int?> SumAsync(IQueryable<int?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public int> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, int>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public int?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, int?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public long> SumAsync(IQueryable<long> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public long?> SumAsync(IQueryable<long?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public long> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, long>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public long?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, long?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public double> SumAsync(IQueryable<double> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public double?> SumAsync(IQueryable<double?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public double> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, double>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public double?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, double?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public float> SumAsync(IQueryable<float> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public float?> SumAsync(IQueryable<float?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(cancellationToken);
    }

    public float> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, float>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public float?> SumAsync<T>(IQueryable<T> queryable, Expression<Func<T, float?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).SumAsync(selector, cancellationToken);
    }

    public decimal> AverageAsync(IQueryable<decimal> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public decimal?> AverageAsync(IQueryable<decimal?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public decimal> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public decimal?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, decimal?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double> AverageAsync(IQueryable<int> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double?> AverageAsync(IQueryable<int?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, int>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, int?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double> AverageAsync(IQueryable<long> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double?> AverageAsync(IQueryable<long?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, long>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, long?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double> AverageAsync(IQueryable<double> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double?> AverageAsync(IQueryable<double?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public double> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, double>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public double?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, double?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public float> AverageAsync(IQueryable<float> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public float?> AverageAsync(IQueryable<float?> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(cancellationToken);
    }

    public float> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, float>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public float?> AverageAsync<T>(IQueryable<T> queryable, Expression<Func<T, float?>> selector, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).AverageAsync(selector, cancellationToken);
    }

    public List<T>> ToListAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return GetMongoQueryable(queryable).ToListAsync(cancellationToken);
    }

    public  T[]> ToArrayAsync<T>(IQueryable<T> queryable, CancellationToken cancellationToken = default)
    {
        return (GetMongoQueryable(queryable).ToListAsync(cancellationToken)).ToArray();
    }
}
