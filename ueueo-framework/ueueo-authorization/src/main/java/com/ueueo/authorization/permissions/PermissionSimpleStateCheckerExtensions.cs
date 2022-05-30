﻿using JetBrains.Annotations;
using Volo.Abp.SimpleStateChecking;

namespace Volo.Abp.Authorization.Permissions;

public static class PermissionSimpleStateCheckerExtensions
{
    public static TState RequireAuthenticated<TState>(@Nonnull this TState state)
        //where TState : IHasSimpleStateCheckers<TState>
    {
        state.StateCheckers.Add(new RequireAuthenticatedSimpleStateChecker<TState>());
        return state;
    }

    public static TState RequirePermissions<TState>(
        @Nonnull this TState state,
        params String[] permissions)
        //where TState : IHasSimpleStateCheckers<TState>
    {
        state.RequirePermissions(requiresAll: true, batchCheck: true, permissions);
        return state;
    }

    public static TState RequirePermissions<TState>(
        @Nonnull this TState state,
        boolean requiresAll,
        params String[] permissions)
        //where TState : IHasSimpleStateCheckers<TState>
    {
        state.RequirePermissions(requiresAll: requiresAll, batchCheck: true, permissions);
        return state;
    }

    public static TState RequirePermissions<TState>(
        @Nonnull this TState state,
        boolean requiresAll,
        boolean batchCheck,
        params String[] permissions)
        //where TState : IHasSimpleStateCheckers<TState>
    {
        Objects.requireNonNull(state, nameof(state));
        Check.NotNullOrEmpty(permissions, nameof(permissions));

        if (batchCheck)
        {
            RequirePermissionsSimpleBatchStateChecker<TState>.Current.AddCheckModels(new RequirePermissionsSimpleBatchStateCheckerModel<TState>(state, permissions, requiresAll));
            state.StateCheckers.Add(RequirePermissionsSimpleBatchStateChecker<TState>.Current);
        }
        else
        {
            state.StateCheckers.Add(new RequirePermissionsSimpleStateChecker<TState>(new RequirePermissionsSimpleBatchStateCheckerModel<TState>(state, permissions, requiresAll)));
        }

        return state;
    }
}
