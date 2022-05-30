using System.Collections.Generic;
using System.Threading.Tasks;
using Volo.Abp.MultiTenancy;
using System.Linq;
using Volo.Abp.Security.Claims;

namespace Volo.Abp.Authorization.Permissions;

public class ClientPermissionValueProvider : PermissionValueProvider
{
    public const String ProviderName = "C";

    @Override
    public String Name => ProviderName;

    protected ICurrentTenant CurrentTenant;//  { get; }

    public ClientPermissionValueProvider(IPermissionStore permissionStore, ICurrentTenant currentTenant)
        : base(permissionStore)
    {
        CurrentTenant = currentTenant;
    }

    @Override
    public Task<PermissionGrantResult> CheckAsync(PermissionValueCheckContext context)
    {
        var clientId = context.Principal?.FindFirst(AbpClaimTypes.ClientId)?.Value;

        if (clientId == null)
        {
            return PermissionGrantResult.Undefined;
        }

        using (CurrentTenant.Change(null))
        {
            return PermissionStore.IsGrantedAsync(context.Permission.Name, Name, clientId)
                ? PermissionGrantResult.Granted
                : PermissionGrantResult.Undefined;
        }
    }

    @Override
    public Task<MultiplePermissionGrantResult> CheckAsync(PermissionValuesCheckContext context)
    {
        var permissionNames = context.Permissions.Select(x => x.Name).Distinct().ToArray();
        Check.NotNullOrEmpty(permissionNames, nameof(permissionNames));

        var clientId = context.Principal?.FindFirst(AbpClaimTypes.ClientId)?.Value;
        if (clientId == null)
        {
            return new MultiplePermissionGrantResult(permissionNames); ;
        }

        using (CurrentTenant.Change(null))
        {
            return PermissionStore.IsGrantedAsync(permissionNames, Name, clientId);
        }
    }
}
