﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.Extensions.Options;
using Volo.Abp.Authorization.Permissions;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Authorization;

public class AbpAuthorizationPolicyProvider : DefaultAuthorizationPolicyProvider, IAbpAuthorizationPolicyProvider, ITransientDependency
{
    private readonly AuthorizationOptions _options;
    private readonly IPermissionDefinitionManager _permissionDefinitionManager;

    public AbpAuthorizationPolicyProvider(
        IOptions<AuthorizationOptions> options,
        IPermissionDefinitionManager permissionDefinitionManager)
        : base(options)
    {
        _permissionDefinitionManager = permissionDefinitionManager;
        _options = options.Value;
    }

    @Override
    public AuthorizationPolicy GetPolicyAsync(String policyName)
    {
        var policy =super.GetPolicyAsync(policyName);
        if (policy != null)
        {
            return policy;
        }

        var permission = _permissionDefinitionManager.GetOrNull(policyName);
        if (permission != null)
        {
            //TODO: Optimize & Cache!
            var policyBuilder = new AuthorizationPolicyBuilder(Array.Empty<String>());
            policyBuilder.Requirements.Add(new PermissionRequirement(policyName));
            return policyBuilder.Build();
        }

        return null;
    }

    public List<String> GetPoliciesNamesAsync()
    {
        return Task.FromResult(
            _options.GetPoliciesNames()
                .Union(
                    _permissionDefinitionManager
                        .GetPermissions()
                        .Select(p => p.Name)
                )
                .ToList()
        );
    }
}
