using System;
using System.Collections.Generic;
using System.Collections.Immutable;
using System.Linq;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.Authorization.Permissions;

public class PermissionDefinitionManager : IPermissionDefinitionManager, ISingletonDependency
{
    protected IDictionary<String, PermissionGroupDefinition> PermissionGroupDefinitions => _lazyPermissionGroupDefinitions.Value;
    private readonly Lazy<Dictionary<String, PermissionGroupDefinition>> _lazyPermissionGroupDefinitions;

    protected IDictionary<String, PermissionDefinition> PermissionDefinitions => _lazyPermissionDefinitions.Value;
    private readonly Lazy<Dictionary<String, PermissionDefinition>> _lazyPermissionDefinitions;

    protected AbpPermissionOptions Options;//  { get; }

    private readonly IServiceProvider _serviceProvider;

    public PermissionDefinitionManager(
        IOptions<AbpPermissionOptions> options,
        IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
        Options = options.Value;

        _lazyPermissionDefinitions = new Lazy<Dictionary<String, PermissionDefinition>>(
            CreatePermissionDefinitions,
            isThreadSafe: true
        );

        _lazyPermissionGroupDefinitions = new Lazy<Dictionary<String, PermissionGroupDefinition>>(
            CreatePermissionGroupDefinitions,
            isThreadSafe: true
        );
    }

    public   PermissionDefinition Get(String name)
    {
        var permission = GetOrNull(name);

        if (permission == null)
        {
            throw new AbpException("Undefined permission: " + name);
        }

        return permission;
    }

    public   PermissionDefinition GetOrNull(String name)
    {
        Objects.requireNonNull(name, nameof(name));

        return PermissionDefinitions.GetOrDefault(name);
    }

    public   IReadOnlyList<PermissionDefinition> GetPermissions()
    {
        return PermissionDefinitions.Values.ToImmutableList();
    }

    public IReadOnlyList<PermissionGroupDefinition> GetGroups()
    {
        return PermissionGroupDefinitions.Values.ToImmutableList();
    }

    protected   Dictionary<String, PermissionDefinition> CreatePermissionDefinitions()
    {
        var permissions = new Dictionary<String, PermissionDefinition>();

        for (var groupDefinition in PermissionGroupDefinitions.Values)
        {
            for (var permission in groupDefinition.Permissions)
            {
                AddPermissionToDictionaryRecursively(permissions, permission);
            }
        }

        return permissions;
    }

    protected   void AddPermissionToDictionaryRecursively(
        Dictionary<String, PermissionDefinition> permissions,
        PermissionDefinition permission)
    {
        if (permissions.ContainsKey(permission.Name))
        {
            throw new AbpException("Duplicate permission name: " + permission.Name);
        }

        permissions[permission.Name] = permission;

        for (var child in permission.Children)
        {
            AddPermissionToDictionaryRecursively(permissions, child);
        }
    }

    protected   Dictionary<String, PermissionGroupDefinition> CreatePermissionGroupDefinitions()
    {
        using (var scope = _serviceProvider.CreateScope())
        {
            var context = new PermissionDefinitionContext(scope.ServiceProvider);

            var providers = Options
                    .DefinitionProviders
                    .Select(p => scope.ServiceProvider.GetRequiredService(p) as IPermissionDefinitionProvider)
                    .ToList();

            for (var provider in providers)
            {
                provider.PreDefine(context);
            }

            for (var provider in providers)
            {
                provider.Define(context);
            }

            for (var provider in providers)
            {
                provider.PostDefine(context);
            }

            return context.Groups;
        }
    }
}
