package com.ueueo.threading;

import com.ueueo.DisposeAction;
import com.ueueo.IDisposable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class AmbientDataContextAmbientScopeProvider<T> implements IAmbientScopeProvider<T> {

    private static ConcurrentHashMap<String, ScopeItem> ScopeDictionary = new ConcurrentHashMap<>();

    private IAmbientDataContext dataContext;

    public AmbientDataContextAmbientScopeProvider(@NonNull IAmbientDataContext dataContext) {
        Objects.requireNonNull(dataContext);
        this.dataContext = dataContext;
    }

    @Override
    public T getValue(String contextKey) {
        ScopeItem item = getCurrentItem(contextKey);
        if (item == null) {
            return null;
        }
        return (T) item.value;
    }

    @Override
    public IDisposable beginScope(String contextKey, T value) {
        ScopeItem item = new ScopeItem(value, getCurrentItem(contextKey));
        ScopeDictionary.put(item.id, item);
        dataContext.setData(contextKey, item.id);
        return new DisposeAction(() -> {
            ScopeItem i = ScopeDictionary.remove(item.getId());
            if (i.getOuter() == null) {
                dataContext.setData(contextKey, null);
                return;
            }
            dataContext.setData(contextKey, i.getOuter().id);
        });
    }

    private ScopeItem getCurrentItem(String contextKey) {
        if (dataContext.getData(contextKey) instanceof String) {
            String objKey = (String) dataContext.getData(contextKey);
            return ScopeDictionary.get(objKey);
        }
        return null;
    }

    @Getter
    private static class ScopeItem {
        private final String id;
        private final ScopeItem outer;
        private final Object value;

        public ScopeItem(Object value, ScopeItem outer) {
            this.id = UUID.randomUUID().toString();
            this.value = value;
            this.outer = outer;
        }
    }
}
