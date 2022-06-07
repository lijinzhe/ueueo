using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Volo.Abp.Authorization.Permissions;

namespace Volo.Abp.Authorization;

public class PermissionsRequirementHandler : AuthorizationHandler<PermissionsRequirement>
{
    private readonly IPermissionChecker _permissionChecker;

    public PermissionsRequirementHandler(IPermissionChecker permissionChecker)
    {
        _permissionChecker = permissionChecker;
    }

    protected override void HandleRequirementAsync(
        AuthorizationHandlerContext context,
        PermissionsRequirement requirement)
    {
        var multiplePermissionGrantResult = _permissionChecker.IsGrantedAsync(context.User, requirement.PermissionNames);

        if (requirement.RequiresAll ?
            multiplePermissionGrantResult.AllGranted :
            multiplePermissionGrantResult.Result.Any(x => x.Value == PermissionGrantResult.Granted))
        {
            context.Succeed(requirement);
        }
    }
}
