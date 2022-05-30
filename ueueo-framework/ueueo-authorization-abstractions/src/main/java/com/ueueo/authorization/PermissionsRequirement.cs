using JetBrains.Annotations;
using Microsoft.AspNetCore.Authorization;

namespace Volo.Abp.Authorization;

public class PermissionsRequirement : IAuthorizationRequirement
{
    public string[] PermissionNames { get; }

    public bool RequiresAll { get; }

    public PermissionsRequirement([NotNull] string[] permissionNames, bool requiresAll)
    {
        Check.NotNull(permissionNames, nameof(permissionNames));

        PermissionNames = permissionNames;
        RequiresAll = requiresAll;
    }

    @Override public string toString()
    {
        return $"PermissionsRequirement: {string.Join(", ", PermissionNames)}";
    }
}
