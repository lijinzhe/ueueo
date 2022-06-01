using JetBrains.Annotations;
using Microsoft.AspNetCore.Authorization;

namespace Volo.Abp.Authorization;

public class PermissionsRequirement : IAuthorizationRequirement
{
    public String[] PermissionNames;//  { get; }

    public boolean RequiresAll;//  { get; }

    public PermissionsRequirement(@NonNull String[] permissionNames, boolean requiresAll)
    {
        Objects.requireNonNull(permissionNames, nameof(permissionNames));

        PermissionNames = permissionNames;
        RequiresAll = requiresAll;
    }

    @Override public String toString()
    {
        return $"PermissionsRequirement: {string.Join(", ", PermissionNames)}";
    }
}
