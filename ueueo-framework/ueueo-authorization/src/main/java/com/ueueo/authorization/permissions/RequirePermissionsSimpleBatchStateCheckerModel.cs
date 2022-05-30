using Volo.Abp.SimpleStateChecking;

namespace Volo.Abp.Authorization.Permissions;

public class RequirePermissionsSimpleBatchStateCheckerModel<TState>
    //where TState : IHasSimpleStateCheckers<TState>
{
    public TState State;//  { get; }

    public String[] Permissions;//  { get; }

    public boolean RequiresAll;//  { get; }

    public RequirePermissionsSimpleBatchStateCheckerModel(TState state, String[] permissions, boolean requiresAll = true)
    {
        Objects.requireNonNull(state, nameof(state));
        Check.NotNullOrEmpty(permissions, nameof(permissions));

        State = state;
        Permissions = permissions;
        RequiresAll = requiresAll;
    }
}
