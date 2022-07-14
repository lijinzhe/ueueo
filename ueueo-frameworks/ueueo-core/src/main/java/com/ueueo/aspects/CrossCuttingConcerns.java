package com.ueueo.aspects;

import com.ueueo.disposable.DisposeAction;
import com.ueueo.disposable.IDisposable;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @author Lee
 * @date 2022-05-18 10:46
 */
public class CrossCuttingConcerns {

    public static void addApplied(Object obj, List<String> concerns) {
        Assert.notEmpty(concerns, "concerns should be provided!");
        if (obj instanceof IAvoidDuplicateCrossCuttingConcerns) {
            ((IAvoidDuplicateCrossCuttingConcerns) obj).getAppliedCrossCuttingConcerns().addAll(concerns);
        }
    }

    public static void removeApplied(Object obj, List<String> concerns) {
        Assert.notEmpty(concerns, "concerns should be provided!");
        if (obj instanceof IAvoidDuplicateCrossCuttingConcerns) {
            for (String concern : concerns) {
                ((IAvoidDuplicateCrossCuttingConcerns) obj).getAppliedCrossCuttingConcerns().removeIf(c -> c.equals(concern));
            }
        }
    }

    public static boolean isApplied(@NonNull Object obj, @NonNull String concern) {
        Assert.notNull(obj, "obj must not null.");
        Assert.notNull(concern, "concern must not null.");
        if (obj instanceof IAvoidDuplicateCrossCuttingConcerns) {
            return ((IAvoidDuplicateCrossCuttingConcerns) obj).getAppliedCrossCuttingConcerns().contains(concern);
        }
        return false;
    }

    public static IDisposable applying(Object obj, List<String> concerns) {
        addApplied(obj, concerns);
        return new DisposeAction(() -> removeApplied(obj, concerns));
    }

    public static List<String> getApplieds(Object obj) {
        if (obj instanceof IAvoidDuplicateCrossCuttingConcerns) {
            return ((IAvoidDuplicateCrossCuttingConcerns) obj).getAppliedCrossCuttingConcerns();
        } else {
            return Collections.emptyList();
        }
    }
}
