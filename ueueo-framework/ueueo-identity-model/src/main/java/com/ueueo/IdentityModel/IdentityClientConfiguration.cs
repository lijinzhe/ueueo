using System;
using System.Collections.Generic;
using System.Globalization;
using IdentityModel;

namespace Volo.Abp.IdentityModel;

public class IdentityClientConfiguration : Dictionary<String, String>
{
    /**
     * Possible values: "client_credentials" or "password".
     * Default value: "client_credentials".
    */
    public String GrantType {
        get => this.GetOrDefault(nameof(GrantType));
        set => this[nameof(GrantType)] = value;
    }

    /**
     * Client Id.
    */
    public String ClientId {
        get => this.GetOrDefault(nameof(ClientId));
        set => this[nameof(ClientId)] = value;
    }

    /**
     * Client secret (as plain text - without hashed).
    */
    public String ClientSecret {
        get => this.GetOrDefault(nameof(ClientSecret));
        set => this[nameof(ClientSecret)] = value;
    }

    /**
     * User name.
     * Valid only if <see cref="GrantType"/> is "password".
    */
    public String UserName {
        get => this.GetOrDefault(nameof(UserName));
        set => this[nameof(UserName)] = value;
    }

    /**
     * Password of the <see cref="UserName"/>.
     * Valid only if <see cref="GrantType"/> is "password".
    */
    public String UserPassword {
        get => this.GetOrDefault(nameof(UserPassword));
        set => this[nameof(UserPassword)] = value;
    }

    /**
     * Authority.
    */
    public String Authority {
        get => this.GetOrDefault(nameof(Authority));
        set => this[nameof(Authority)] = value;
    }

    /**
     * Scope.
    */
    public String Scope {
        get => this.GetOrDefault(nameof(Scope));
        set => this[nameof(Scope)] = value;
    }

    /**
     * RequireHttps.
     * Default: true.
    */
    public boolean RequireHttps {
        get => this.GetOrDefault(nameof(RequireHttps))?.To<Boolean> () ?? true;
        set => this[nameof(RequireHttps)] = value.ToString().ToLowerInvariant();
    }

    /**
     * Absolute expiration duration (as seconds) for the access token cache.
     * Default: 1800 seconds (30 minutes)
    */
    public int CacheAbsoluteExpiration {
        get => this.GetOrDefault(nameof(CacheAbsoluteExpiration))?.To<int>() ?? 60 * 30;
        set => this[nameof(CacheAbsoluteExpiration)] = value.ToString(CultureInfo.InvariantCulture);
    }

    public IdentityClientConfiguration()
    {

    }

    public IdentityClientConfiguration(
        String authority,
        String scope,
        String clientId,
        String clientSecret,
        String grantType = OidcConstants.GrantTypes.ClientCredentials,
        String userName = null,
        String userPassword = null,
        boolean requireHttps = true,
        int cacheAbsoluteExpiration = 60 * 30)
    {
        this[nameof(Authority)] = authority;
        this[nameof(Scope)] = scope;
        this[nameof(ClientId)] = clientId;
        this[nameof(ClientSecret)] = clientSecret;
        this[nameof(GrantType)] = grantType;
        this[nameof(UserName)] = userName;
        this[nameof(UserPassword)] = userPassword;
        this[nameof(RequireHttps)] = requireHttps.ToString().ToLowerInvariant();
        this[nameof(CacheAbsoluteExpiration)] = cacheAbsoluteExpiration.ToString(CultureInfo.InvariantCulture);
    }
}
