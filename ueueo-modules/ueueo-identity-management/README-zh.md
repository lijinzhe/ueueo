# 身份管理模块

身份模块基于Microsoft Identity库用于管理[组织单元](Organization-Units.md), 角色, 用户和他们的权限.

> 参阅 [源码](https://github.com/abpframework/abp/tree/dev/modules/identity). 文档很快会被完善.

## Identity安全日志

安全日志可以记录账户的一些重要的操作或者改动， 你可以在在一些功能中保存安全日志.

你可以注入和使用 `IdentitySecurityLogManager` 或 `ISecurityLogManager` 来保存安全日志. 默认它会创建一个安全日志对象并填充常用的值. 如 `CreationTime`, `ClientIpAddress`, `BrowserInfo`, `current user/tenant`等等. 当然你可以自定义这些值.

```cs
await IdentitySecurityLogManager.SaveAsync(new IdentitySecurityLogContext()
{
	Identity = "IdentityServer";
	Action = "ChangePassword";
});
```

通过配置 `AbpSecurityLogOptions` 来提供应用程序的名称或者禁用安全日志功能. 默认是**启用**状态.

```cs
Configure<AbpSecurityLogOptions>(options =>
{
	options.ApplicationName = "AbpSecurityTest";
});
```

# 组织单元管理

组织单元(OU)是"身份模块"的一部分,用于**对用户和实体进行分层分组**.

### OrganizationUnit 实体

OU由 **OrganizationUnit** 实体表示. 实体有以下基本属性:

- **TenantId**: OU租户的ID,为null代表是宿主OU.
- **ParentId**: OU的父亲Id,为null为根OU.
- **Code**: 租户唯一的分层字符串代码.
- **DisplayName**: OU的显示名称.

OrganizationUnit实体的主键(Id)是 **Guid** 类型,派生自[**FullAuditedAggregateRoot**](../Entities.md)类.

#### Organization 树

因为OU可以有父亲,租户所有的OU是一个**树**结构. 树有一些规则:

- 可以有多个根(`ParentId` 为 `null`).
- OU的第一级子级数有限制(面说明的固定OU代码单位长度).

#### OU Code

OU代码由OrganizationUnit Manager自动生成和维护. 看起来像这样的字符串:

"**00001.00042.00005**"

此代码可用于轻松查询数据库中OU的所有子级(递归). 代码有一些规则:

- 必须[租户](../Multi-Tenancy.md)**唯一**的.
- 同一OU的所有子代均以**父OU的代码开头**.
- 它是**固定长度**的,并且基于树中OU的级别,如示例中所示.
- 虽然OU代码是唯一的,但是如果移动OU,它是**可更改的**.
- 你必须通过Id而不是代码引用OU.

### OrganizationUnit Manager

可以注入 **OrganizationUnitManager** 管理OU. 常见的用例有:

- 创建,更改或删除OU.
- 在OU树中移动OU.
- 获取有关OU树及其项的信息

#### 多租户

`OrganizationUnitManager` 设置为一次性为 **单个租户** 工作,默认是 **当前租户**.
