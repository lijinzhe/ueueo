package com.ueueo.ddd.domain.values;

import java.util.Iterator;
import java.util.List;

/**
 * Inspired from https://docs.microsoft.com/en-us/dotnet/standard/microservices-architecture/microservice-ddd-cqrs-patterns/implement-value-objects
 *
 * @author Lee
 * @date 2022-05-25 14:36
 */
public abstract class ValueObject {

    protected abstract List<Object> getAtomicValues();

    public boolean valueEquals(Object obj) {
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        ValueObject other = (ValueObject) obj;

        Iterator<Object> thisValues = getAtomicValues().iterator();
        Iterator<Object> otherValues = other.getAtomicValues().iterator();

        Object thisValue;
        Object otherValue;
        while (thisValues.hasNext() && otherValues.hasNext()) {
            thisValue = thisValues.next();
            otherValue = otherValues.next();

            if (thisValue != null && !thisValue.equals(otherValue)) {
                return false;
            }
        }
        return !thisValues.hasNext() && !otherValues.hasNext();
    }
}
