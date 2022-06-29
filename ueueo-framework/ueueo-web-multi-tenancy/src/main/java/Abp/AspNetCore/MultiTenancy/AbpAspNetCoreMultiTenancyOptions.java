package Abp.AspNetCore.MultiTenancy;

import com.ueueo.multitenancy.TenantResolverConsts;

public class AbpAspNetCoreMultiTenancyOptions {

    /**
     * Default DefaultTenantKey
     */
    private String tenantKey;

    //    public Func<ServletRequestAttributes, Exception, Task> MultiTenancyMiddlewareErrorPageBuilder { get; set; }

    public AbpAspNetCoreMultiTenancyOptions() {
        tenantKey = TenantResolverConsts.DefaultTenantKey;
        //        MultiTenancyMiddlewareErrorPageBuilder = async (context, exception) =>
        //        {
        //            context.Response.StatusCode = (int)HttpStatusCode.InternalServerError; ;
        //            context.Response.ContentType = "text/html";
        //
        //            var message = exception.Message;
        //            var details = exception is BusinessException businessException ? businessException.Details : string.Empty;
        //
        //            await context.Response.WriteAsync($"<html lang=\"{CultureInfo.CurrentCulture.Name}\"><body>\r\n");
        //            await context.Response.WriteAsync($"<h3>{message}</h3>{details}<br>\r\n");
        //            await context.Response.WriteAsync("</body></html>\r\n");
        //
        //                // Note the 500 spaces are to work around an IE 'feature'
        //                await context.Response.WriteAsync(new string(' ', 500));
        //        };
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
    }
}
