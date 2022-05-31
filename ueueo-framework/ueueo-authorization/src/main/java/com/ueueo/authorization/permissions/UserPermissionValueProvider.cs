using System.Linq;
using System.Threading.Tasks;
using Volo.Abp.Security.Claims;

namespace Volo.Abp.Authorization.Permissions;

public class UserPermissionValueProvider : PermissionValueProvider
{
    public const String ProviderName = "U";

    @Override
    public String Name => ProviderName;

    public UserPermissionValueProvider(IPermissionStore permissionStore)
        : base(permissionStore)
    {

    }

    @Override
    public PermissionGrantResult CheckAsync(PermissionValueCheckContext context)
    {
        var userId = context.Principal?.FindFirst(AbpClaimTypes.UserId)?.Value;

        if (userId == null)
        {
            return PermissionGrantResult.Undefined;
        }

        return PermissionStore.IsGrantedAsync(context.Permission.Name, Name, userId)
            ? PermissionGrantResult.Granted
            : PermissionGrantResult.Undefined;
    }

    @Override
    public MultiplePermissionGrantResult CheckAsync(PermissionValuesCheckContext context)
    {
        var permissionNames = context.Permissions.Select(x => x.Name).Distinct().ToArray();
        Check.NotNullOrEmpty(permissionNames, nameof(permissionNames));

        var userId = context.Principal?.FindFirst(AbpClaimTypes.UserId)?.Value;
        if (userId == null)
        {
            return new MultiplePermissionGrantResult(permissionNames);
        }

        return PermissionStore.IsGrantedAsync(permissionNames, Name, userId);
    }
}
