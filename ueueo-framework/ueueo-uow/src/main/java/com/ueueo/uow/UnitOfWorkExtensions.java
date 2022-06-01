package com.ueueo.uow;

import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Function;

public class UnitOfWorkExtensions {
    public static boolean isReservedFor(@NonNull IUnitOfWork unitOfWork, String reservationName) {
        Objects.requireNonNull(unitOfWork);

        return unitOfWork.isReserved() && unitOfWork.getReservationName().equals(reservationName);
    }

    public static <TValue> void addItem(@NonNull IUnitOfWork unitOfWork, String key, TValue value) {
        Objects.requireNonNull(unitOfWork);

        if (!unitOfWork.getItems().containsKey(key)) {
            unitOfWork.getItems().put(key, value);
        } else {
            unitOfWork.getItems().put(key, value);
        }
    }

    public static <TValue> TValue getItemOrDefault(@NonNull IUnitOfWork unitOfWork, String key) {
        Objects.requireNonNull(unitOfWork);
        return (TValue) unitOfWork.getItems().get(key);
    }

    public static <TValue> TValue getOrAddItem(@NonNull IUnitOfWork unitOfWork, String key, Function<String, TValue> factory) {
        Objects.requireNonNull(unitOfWork);
        return (TValue) unitOfWork.getItems().computeIfAbsent(key, factory);
    }

    public static void removeItem(@NonNull IUnitOfWork unitOfWork, String key) {
        Objects.requireNonNull(unitOfWork);

        unitOfWork.getItems().remove(key);
    }
}
