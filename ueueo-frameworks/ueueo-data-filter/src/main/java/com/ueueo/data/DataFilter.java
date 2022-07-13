package com.ueueo.data;

import com.ueueo.disposable.IDisposable;
import org.springframework.beans.factory.BeanFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO 未完成
 * TODO: Create a Volo.Abp.Data.Filtering namespace?
 *
 * @author Lee
 * @date 2022-05-29 15:16
 */
public class DataFilter<TFilter> implements IDataFilter<TFilter> {

    private ConcurrentHashMap<Class<?>, Object> _filters;

    private BeanFactory beanFactory;

    public DataFilter(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        _filters = new ConcurrentHashMap<>();
    }

    @Override
    public IDisposable enable(Class<TFilter> type) {
        return null;
    }

    @Override
    public IDisposable disable(Class<TFilter> type) {
        return null;
    }

    @Override
    public boolean isEnabled(Class<TFilter> type) {
        return false;
    }

    private IDataFilter<?> getFilter(Class<TFilter> type) {
        return (IDataFilter<?>) _filters.computeIfAbsent(type,filterType -> beanFactory.getBean(filterType));
    }

}
