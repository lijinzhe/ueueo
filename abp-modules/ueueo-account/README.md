# Account Module

Account module implements the basic authentication features like **login**, **register**, **forgot password** and **account management**.

This module is based on [Microsoft's Identity library](https://docs.microsoft.com/en-us/aspnet/core/security/authentication/identity) and the [Identity Module](Identity.md). It has [IdentityServer](https://github.com/IdentityServer) integration (based on the [IdentityServer Module](IdentityServer.md)) to provide **single sign-on**, access control and other advanced authentication features.

## How to Install

This module comes as pre-installed (as NuGet/NPM packages) when you [create a new solution](https://abp.io/get-started) with the ABP Framework. You can continue to use it as package and get updates easily, or you can include its source code into your solution (see `get-source` [CLI](../CLI.md) command) to develop your custom module.

### The Source Code

The source code of this module can be accessed [here](https://github.com/abpframework/abp/tree/dev/modules/account). The source code is licensed with [MIT](https://choosealicense.com/licenses/mit/), so you can freely use and customize it.

## User Interface

This section introduces the main pages provided by this module.

### Login

`/Account/Login` page provides the login functionality.

![account-module-login](docs/images/account-module-login.png)

Social/external login buttons becomes visible if you setup it. See the *Social/External Logins* section below. Register and Forgot password and links redirect to the pages explained in the next sections.

### Register

`/Account/Register` page provides the new user registration functionality.

![account-module-register](docs/images/account-module-register.png)

### Forgot Password & Reset Password

`/Account/ForgotPassword` page provides a way of sending password reset link to user's email address. The user then clicks to the link and determines a new password.

![account-module-forgot-password](docs/images/account-module-forgot-password.png)

### Account Management

`/Account/Manage` page is used to change password and personal information of the user.

![account-module-manage-account](docs/images/account-module-manage-account.png)

## IdentityServer Integration

[Volo.Abp.Account.Web.IdentityServer](https://www.nuget.org/packages/Volo.Abp.Account.Web.IdentityServer) package provides integration for the [IdentityServer](https://github.com/IdentityServer). This package comes as installed with the [application startup template](../Startup-Templates/Application.md). See the [IdentityServer Module](IdentityServer.md) documentation.

## Social/External Logins

The Account Module has already configured to handle social or external logins out of the box. You can follow the ASP.NET Core documentation to add a social/external login provider to your application.

### Example: Facebook Authentication

Follow the [ASP.NET Core Facebook integration document](https://docs.microsoft.com/en-us/aspnet/core/security/authentication/social/facebook-logins) to support the Facebook login for your application.

#### Add the NuGet Package

Add the [Microsoft.AspNetCore.Authentication.Facebook](https://www.nuget.org/packages/Microsoft.AspNetCore.Authentication.Facebook) package to your project. Based on your architecture, this can be `.Web`, `.IdentityServer` (for tiered setup) or `.Host` project.

#### Configure the Provider

Use the `.AddFacebook(...)` extension method in the `ConfigureServices` method of your [module](../Module-Development-Basics.md), to configure the client:

````csharp
context.Services.AddAuthentication()
    .AddFacebook(facebook =>
    {
        facebook.AppId = "...";
        facebook.AppSecret = "...";
        facebook.Scope.Add("email");
        facebook.Scope.Add("public_profile");
    });
````

> It would be a better practice to use the `appsettings.json` or the ASP.NET Core User Secrets system to store your credentials, instead of a hard-coded value like that. Follow the [Microsoft's document](https://docs.microsoft.com/en-us/aspnet/core/security/authentication/social/facebook-logins) to learn the user secrets usage.

---

# Account Module (Commercial)

This module implements the Login, Register, Forgot Password, Email Confirmation, Password Reset, sending and confirming Two-Factor Authentication, user lockout, switch between tenants functionalities of an application;

* Built on the [Microsoft's ASP.NET Core Identity](https://docs.microsoft.com/en-us/aspnet/core/security/authentication/identity) library.
* Identity Server Grant and Consent pages.
* Setting page to manage **self registration** and two-factor authentication.

See [the module description page](https://commercial.abp.io/modules/Volo.Identity.Pro) for an overview of the module features.

## How to Install

Account is pre-installed in [the startup templates](../startup-templates/application/index.md). So, no need to manually install it.

## Packages

This module follows the [module development best practices guide](https://docs.abp.io/en/abp/latest/Best-Practices/Index) and consists of several NuGet and NPM packages. See the guide if you want to understand the packages and relations between them.

You can visit [Account module package list page](https://abp.io/packages?moduleName=Volo.Account.Pro) to see list of packages related with this module.

## User Interface

### Menu Items

This module doesn't define any menu items.

### Pages

#### Login Page

Login page is used to log in to the system.

![account-pro-module-login-page](docs/images/account-pro-module-login-page-2.png)

#### Register Page

Register page allows new users to register to your system.

![identity-users-page](docs/images/account-pro-module-register-page-2.png)

#### Two Factor Authentication

Identity module allows two factor authentication pages.

##### Send Security Code

Send security code page allows selecting a two factor authentication provider (Email, Phone etc...) and sends a security code to user via selected provider.

![account-pro-module-two-factor-send-page](docs/images/account-pro-module-two-factor-send-page-2.png)

##### Verify Security Code

Verify security code page verifies the security code sent to user and if the code is verified, user logs in to the system.

![account-pro-module-two-factor-verify-page](docs/images/account-pro-module-two-factor-verify-page-2.png)

## Data Seed

This module doesn't seed any data.

## Options

### AbpIdentityAspNetCoreOptions

`AbpAccountOptions` can be configured in the UI layer, in the `ConfigureServices` method of your [module](https://docs.abp.io/en/abp/latest/Module-Development-Basics). Example:

````csharp
Configure<AbpAccountOptions>(options =>
{
    //Set options here...
});
````

`AbpAccountOptions` properties:

* `WindowsAuthenticationSchemeName` (default: Windows): Name of the Windows authentication scheme.

## Social / External Logins

Account module implements social/external login system. All you need to do is to install & configure the provider you want to use.

The application startup template comes with **Twitter**, **Google** and **Microsoft** logins pre-installed. You can configure the client id and secrets on the Settings page:

![account-pro-external-login-settings](docs/images/account-pro-external-login-settings.png)

Social/External login system is compatible with the multi-tenancy. Each tenant can configure their own provider settings if your application is multi-tenant.

### Install a new External Login

Follow the steps below to install a new external/social login. We will show the Facebook authentication as an example.

> When you follow the steps below, the provider settings (e.g. ClientId and ClientSecret) will be managed on the settings page on the UI and will support multi-tenancy as explained above. If you don't want these features, you can follow [the standard way](https://docs.abp.io/en/abp/latest/Authentication/Social-External-Logins) to install and configure the provider.

#### Add the NuGet Package

Add the [Microsoft.AspNetCore.Authentication.Facebook](https://www.nuget.org/packages/Microsoft.AspNetCore.Authentication.Facebook) package to your project. Based on your architecture, this can be `.Web`, `.IdentityServer` (for tiered setup) or `.Host` project.

#### Configure the Provider

Use the `.AddFacebook(...)` and `WithDynamicOptions()` extension methods in the `ConfigureServices` method of your module:

````csharp
context.Services.AddAuthentication()
    .AddFacebook(facebook =>
    {
        facebook.Scope.Add("email");
        facebook.Scope.Add("public_profile");
    })
    .WithDynamicOptions<FacebookOptions>(
        FacebookDefaults.AuthenticationScheme,
        options =>
        {
            options.WithProperty(x => x.AppId);
            options.WithProperty(x => x.AppSecret, isSecret: true);
        }
    );
````

* `AddFacebook()` is the standard method that you can set hard-coded configuration.
* `WithDynamicOptions<FacebookOptions>` is provided by the Account Module that makes possible to configure the provided properties on the UI.

#### For Tiered / Separate IdentityServer Solutions

If your `.IdentityServer` is separated from the `.Host` project, then the `.Host` project should also be configured.

* Add the [Microsoft.AspNetCore.Authentication.Facebook](https://www.nuget.org/packages/Microsoft.AspNetCore.Authentication.Facebook) package to your `.Host` project.
* Add `WithDynamicOptions<FacebookOptions>()` configuration into the `ConfigureServices` method of your module (just copy the all code above and remove the `.AddFacebook(...)` part since it is only needed in the IdentityServer side).

## Internals

### Settings

See the `IAccountSettingNames` class members for all settings defined for this module.

### Application Layer

#### Application Services

* `AccountAppService` (implements `IAccountAppService`): Implements the use cases of the register and password reset UIs.
* `AccountSettingsAppService` (implements `IAccountSettingsAppService`):  Implements the use case of the account settings UI.

### Permissions

See the `AccountPermissions` class members for all permissions defined for this module.


### Angular UI

#### Installation

In order to configure the application to use the `AccountPublicModule` and the `AccountAdminModule`, you first need to import `AccountPublicConfigModule` from `@volo/abp.ng.account/public/config` and `AccountAdminConfigModule` from `@volo/abp.ng.account/adming/config` to root module. Config modules has a static `forRoot` method which you should call for a proper configuration.

```js
// app.module.ts
import { AccountAdminConfigModule } from '@volo/abp.ng.account/admin/config';
import { AccountPublicConfigModule } from '@volo/abp.ng.account/public/config';

@NgModule({
  imports: [
    // other imports
    AccountPublicConfigModule.forRoot(),
    AccountAdminConfigModule.forRoot(),
    // other imports
  ],
  // ...
})
export class AppModule {}
```

The `AccountPublicModule` should be imported and lazy-loaded in your routing module. It has a static `forLazy` method for configuration. Available options are listed below. It is available for import from `@volo/abp.ng.account/public`.

```js
// app-routing.module.ts
const routes: Routes = [
  // other route definitions
  {
    path: 'account',
    loadChildren: () =>
      import('@volo/abp.ng.account/public').then(m => m.AccountPublicModule.forLazy(/* options here */)),
  },
];

@NgModule(/* AppRoutingModule metadata */)
export class AppRoutingModule {}
```

> If you have generated your project via the startup template, you do not have to do anything, because it already has the modules.

<h4 id="h-account-module-options">Options</h4>

You can modify the look and behavior of the module pages by passing the following options to `AccountModule.forLazy` static method:

- **redirectUrl**: Default redirect URL after logging in.
- **entityActionContributors:** Changes grid actions. Please check [Entity Action Extensions for Angular](https://docs.abp.io/en/abp/latest/UI/Angular/Entity-Action-Extensions) for details.
- **toolbarActionContributors:** Changes page toolbar. Please check [Page Toolbar Extensions for Angular](https://docs.abp.io/en/abp/latest/UI/Angular/Page-Toolbar-Extensions) for details.
- **entityPropContributors:** Changes table columns. Please check [Data Table Column Extensions for Angular](https://docs.abp.io/en/abp/latest/UI/Angular/Data-Table-Column-Extensions) for details.

#### Services / Models

Account module services and models are generated via `generate-proxy` command of the [ABP CLI](https://docs.abp.io/en/abp/latest/CLI). If you need the module's proxies, you can run the following commands in the Angular project directory.

The command below generates `AccountPublicModule` proxies:

```bash
abp generate-proxy --module account
```

The command below generates `AccountPublicModule` proxies:
```bash
abp generate-proxy --module accountAdmin
```

#### Replaceable Components

`eAccountComponents` enum provides all replaceable component keys. It is available for import from `@volo/abp.ng.account/public`.

Please check [Component Replacement document](https://docs.abp.io/en/abp/latest/UI/Angular/Component-Replacement) for details.


#### Remote Endpoint URL

The Account module remote endpoint URLs can be configured in the environment files.

```js
export const environment = {
  // other configurations
  apis: {
    default: {
      url: 'default url here',
    },
    AbpAccountPublic: {
      url: 'AbpAccountPublic remote url here'
    },
    AbpAccountAdmin: {
      url: 'AbpAccountAdmin remote url here'
    },
    // other api configurations
  },
};
```

The Account module remote URL configurations shown above are optional. If you don't set any URLs, the `default.url` will be used as fallback.


## Distributed Events

This module doesn't define any additional distributed event. See the [standard distributed events](https://docs.abp.io/en/abp/latest/Distributed-Event-Bus).
