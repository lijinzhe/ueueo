using System;
using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp.Uow;

public static class UnitOfWorkExtensions
{
    public static boolean IsReservedFor(@NonNull this IUnitOfWork unitOfWork, String reservationName)
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        return unitOfWork.IsReserved && unitOfWork.ReservationName == reservationName;
    }

    public static void AddItem<TValue>(@NonNull this IUnitOfWork unitOfWork, String key, TValue value)
        where TValue : class
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        if (!unitOfWork.Items.ContainsKey(key))
        {
            unitOfWork.Items[key] = value;
        }
        else
        {
            unitOfWork.Items.Add(key, value);
        }
    }

    public static TValue GetItemOrDefault<TValue>(@NonNull this IUnitOfWork unitOfWork, String key)
        where TValue : class
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        return unitOfWork.Items.FirstOrDefault(x => x.Key == key).Value.As<TValue>();
    }

    public static TValue GetOrAddItem<TValue>(@NonNull this IUnitOfWork unitOfWork, String key, Func<String, TValue> factory)
        where TValue : class
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        return unitOfWork.Items.GetOrAdd(key, factory).As<TValue>();
    }

    public static void RemoveItem(@NonNull this IUnitOfWork unitOfWork, String key)
    {
        Objects.requireNonNull(unitOfWork, nameof(unitOfWork));

        unitOfWork.Items.RemoveAll(x => x.Key == key);
    }
}
