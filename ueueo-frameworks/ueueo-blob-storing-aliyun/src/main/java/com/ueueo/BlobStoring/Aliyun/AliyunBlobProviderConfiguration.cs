using System;

namespace Volo.Abp.BlobStoring.Aliyun;

/**
 * Sub-account access to OSS or STS temporary authorization to access OSS
*/
public class AliyunBlobProviderConfiguration
{
    public String AccessKeyId {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.AccessKeyId);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.AccessKeyId, Check.NotNullOrWhiteSpace(value, nameof(value)));
    }

    public String AccessKeySecret {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.AccessKeySecret);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.AccessKeySecret, Check.NotNullOrWhiteSpace(value, nameof(value)));
    }

    public String Endpoint {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.Endpoint);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.Endpoint, Check.NotNullOrWhiteSpace(value, nameof(value)));
    }

    public boolean UseSecurityTokenService {
        get => _containerConfiguration.GetConfigurationOrDefault(AliyunBlobProviderConfigurationNames.UseSecurityTokenService, false);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.UseSecurityTokenService, value);
    }

    public String RegionId {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.RegionId);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.RegionId, value);
    }

    /**
     * acs:ram::$accountID:role/$roleName
    */
    public String RoleArn {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.RoleArn);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.RoleArn, value);
    }

    /**
     * The name used to identify the temporary access credentials, it is recommended to use different application users to distinguish.
    */
    public String RoleSessionName {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.RoleSessionName);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.RoleSessionName, value);
    }

    /**
     * Set the validity period of the temporary access credential, the unit is s, the minimum is 900, and the maximum is 3600.
    */
    public int DurationSeconds {
        get => _containerConfiguration.GetConfigurationOrDefault(AliyunBlobProviderConfigurationNames.DurationSeconds, 0);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.DurationSeconds, value);
    }

    /**
     * If policy is empty, the user will get all permissions under this role
    */
    public String Policy {
        get => _containerConfiguration.GetConfiguration<String>(AliyunBlobProviderConfigurationNames.Policy);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.Policy, value);
    }

    /**
     * This name may only contain lowercase letters, numbers, and hyphens, and must begin with a letter or a number.
     * Each hyphen must be preceded and followed by a non-hyphen character.
     * The name must also be between 3 and 63 characters long.
     * If this parameter is not specified, the ContainerName of the <see cref="BlobProviderArgs"/> will be used.
    */
    public String ContainerName {
        get => _containerConfiguration.GetConfigurationOrDefault<String>(AliyunBlobProviderConfigurationNames.ContainerName);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.ContainerName, value);
    }

    /**
     * Default value: false.
    */
    public boolean CreateContainerIfNotExists {
        get => _containerConfiguration.GetConfigurationOrDefault(AliyunBlobProviderConfigurationNames.CreateContainerIfNotExists, false);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.CreateContainerIfNotExists, value);
    }

    private readonly String _temporaryCredentialsCacheKey;
    public String TemporaryCredentialsCacheKey {
        get => _containerConfiguration.GetConfigurationOrDefault(AliyunBlobProviderConfigurationNames.TemporaryCredentialsCacheKey, _temporaryCredentialsCacheKey);
        set => _containerConfiguration.SetConfiguration(AliyunBlobProviderConfigurationNames.TemporaryCredentialsCacheKey, value);
    }

    private readonly BlobContainerConfiguration _containerConfiguration;

    public AliyunBlobProviderConfiguration(BlobContainerConfiguration containerConfiguration)
    {
        _containerConfiguration = containerConfiguration;
        _temporaryCredentialsCacheKey = Guid.NewGuid().ToString("N");
    }
}
