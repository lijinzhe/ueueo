using System;
using System.Collections.Generic;
using System.Linq;
using JetBrains.Annotations;

namespace Volo.Abp;

/**
 * A shortcut to use <see cref="Random"/> class.
 * Also provides some useful methods.
*/
public static class RandomHelper
{
    private static readonly Random Rnd = new Random();

    /**
     * Returns a random number within a specified range.
    *
     * <param name="minValue">The inclusive lower bound of the random number returned.</param>
     * <param name="maxValue">The exclusive upper bound of the random number returned. maxValue must be greater than or equal to minValue.</param>
     * <returns>
     * A 32-bit signed integer greater than or equal to minValue and less than maxValue;
     * that is, the range of return values includes minValue but not maxValue.
     * If minValue equals maxValue, minValue is returned.
     * </returns>
     */
    public static int GetRandom(int minValue, int maxValue)
    {
        lock (Rnd)
        {
            return Rnd.Next(minValue, maxValue);
        }
    }

    /**
     * Returns a nonnegative random number less than the specified maximum.
    *
     * <param name="maxValue">The exclusive upper bound of the random number to be generated. maxValue must be greater than or equal to zero.</param>
     * <returns>
     * A 32-bit signed integer greater than or equal to zero, and less than maxValue;
     * that is, the range of return values ordinarily includes zero but not maxValue.
     * However, if maxValue equals zero, maxValue is returned.
     * </returns>
     */
    public static int GetRandom(int maxValue)
    {
        lock (Rnd)
        {
            return Rnd.Next(maxValue);
        }
    }

    /**
     * Returns a nonnegative random number.
    */
     * <returns>A 32-bit signed integer greater than or equal to zero and less than <see cref="int.MaxValue"/>.</returns>
    public static int GetRandom()
    {
        lock (Rnd)
        {
            return Rnd.Next();
        }
    }

    /**
     * Gets random of given objects.
    *
     * <typeparam name="T">Type of the objects</typeparam>
     * <param name="objs">List of object to select a random one</param>
     */
    public static T GetRandomOf<T>(@NonNull params T[] objs)
    {
        Check.NotNullOrEmpty(objs, nameof(objs));

        return objs[GetRandom(0, objs.Length)];
    }

    /**
     * Gets random item from the given list.
    *
     * <typeparam name="T">Type of the objects</typeparam>
     * <param name="list">List of object to select a random one</param>
     */
    public static T GetRandomOfList<T>(@NonNull IList<T> list)
    {
        Check.NotNullOrEmpty(list, nameof(list));

        return list[GetRandom(0, list.Count)];
    }

    /**
     * Generates a randomized list from given enumerable.
    *
     * <typeparam name="T">Type of items in the list</typeparam>
     * <param name="items">items</param>
     */
    public static List<T> GenerateRandomizedList<T>(@NonNull IEnumerable<T> items)
    {
        Objects.requireNonNull(items, nameof(items));

        var currentList = new List<T>(items);
        var randomList = new List<T>();

        while (currentList.Any())
        {
            var randomIndex = RandomHelper.GetRandom(0, currentList.Count);
            randomList.Add(currentList[randomIndex]);
            currentList.RemoveAt(randomIndex);
        }

        return randomList;
    }
}
