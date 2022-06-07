using System;
using JetBrains.Annotations;
using Volo.Abp.SimpleStateChecking;

namespace Volo.Abp.GlobalFeatures;

public static class GlobalFeatureSimpleStateCheckerExtensions
{
    public static TState RequireGlobalFeatures<TState>(
        @NonNull this TState state,
        params String[] globalFeatures)
        where TState : IHasSimpleStateCheckers<TState>
    {
        return state.RequireGlobalFeatures(true, globalFeatures);
    }

    public static TState RequireGlobalFeatures<TState>(
        @NonNull this TState state,
        boolean requiresAll,
        params String[] globalFeatures)
        where TState : IHasSimpleStateCheckers<TState>
    {
        Objects.requireNonNull(state, nameof(state));
        Check.NotNullOrEmpty(globalFeatures, nameof(globalFeatures));

        state.StateCheckers.Add(new RequireGlobalFeaturesSimpleStateChecker<TState>(requiresAll, globalFeatures));
        return state;
    }

    public static TState RequireGlobalFeatures<TState>(
        @NonNull this TState state,
        params Type[] globalFeatures)
        where TState : IHasSimpleStateCheckers<TState>
    {
        return state.RequireGlobalFeatures(true, globalFeatures);
    }

    public static TState RequireGlobalFeatures<TState>(
        @NonNull this TState state,
        boolean requiresAll,
        params Type[] globalFeatures)
        where TState : IHasSimpleStateCheckers<TState>
    {
        Objects.requireNonNull(state, nameof(state));
        Check.NotNullOrEmpty(globalFeatures, nameof(globalFeatures));

        state.StateCheckers.Add(new RequireGlobalFeaturesSimpleStateChecker<TState>(requiresAll, globalFeatures));
        return state;
    }
}
