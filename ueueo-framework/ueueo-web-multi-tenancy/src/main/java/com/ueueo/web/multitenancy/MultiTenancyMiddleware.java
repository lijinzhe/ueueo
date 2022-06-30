package com.ueueo.web.multitenancy;

import com.ueueo.disposable.IDisposable;
import com.ueueo.localization.LocalizationSettingNames;
import com.ueueo.multitenancy.ICurrentTenant;
import com.ueueo.multitenancy.ITenantConfigurationProvider;
import com.ueueo.multitenancy.ITenantResolveResultAccessor;
import com.ueueo.multitenancy.TenantConfiguration;
import com.ueueo.settings.ISettingProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MultiTenancyMiddleware implements HandlerInterceptor
{
    private ITenantConfigurationProvider tenantConfigurationProvider;
    private ICurrentTenant currentTenant;
    private  String tenantField;
    private ITenantResolveResultAccessor tenantResolveResultAccessor;
    private ISettingProvider settingProvider;

    public MultiTenancyMiddleware(
        ITenantConfigurationProvider tenantConfigurationProvider,
        ICurrentTenant currentTenant,
        String tenantField,
        ITenantResolveResultAccessor tenantResolveResultAccessor,
        ISettingProvider settingProvider)
    {
        this.tenantConfigurationProvider = tenantConfigurationProvider;
        this.currentTenant = currentTenant;
        this.tenantResolveResultAccessor = tenantResolveResultAccessor;
        this.tenantField = tenantField;
        this.settingProvider = settingProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TenantConfiguration tenant;
        try
        {
            tenant =  tenantConfigurationProvider.get( true);
        }
        catch (Exception e)
        {
            //TODO by Lee on 2022-06-30 12:01 将服务器异常封装成统一的返回数据格式
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return true;
        }

        if (tenant!= null && !tenant.getId() .equals( currentTenant.getId()))
        {
            IDisposable disposable = currentTenant.change(tenant.getId(),tenant.getName());
            if (tenantResolveResultAccessor.getResult() != null &&
                    tenantResolveResultAccessor.getResult().getAppliedResolvers().contains(QueryStringTenantResolveContributor.CONTRIBUTOR_NAME))
            {
                AbpMultiTenancyCookieHelper.setTenantCookie(response, currentTenant.getId(), tenantField);
            }
            //TODO by Lee on 2022-06-30 11:21 为什么要将语言放入cookie？
            //            Locale requestCulture =  TryGetRequestCultureAsync(request);
            //            if (requestCulture != null)
            //            {
            //                CultureInfo.CurrentCulture = requestCulture.Culture;
            //                CultureInfo.CurrentUICulture = requestCulture.UICulture;
            //                AbpRequestCultureCookieHelper.SetCultureCookie(
            //                        context,
            //                        requestCulture
            //                );
            //                context.Items[AbpRequestLocalizationMiddleware.HttpContextItemName] = true;
            //            }
            disposable.dispose();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    private  Locale TryGetRequestCultureAsync(HttpServletRequest request)
    {

        Locale requestCultureFeature =  request.getLocale();

        /* If requestCultureFeature == null, that means the RequestLocalizationMiddleware was not used
         * and we don't want to set the culture. */
        if (requestCultureFeature == null)
        {
            return null;
        }

//        /* If requestCultureFeature.Provider is not null, that means RequestLocalizationMiddleware
//         * already picked a language, so we don't need to set the default. */
//        if (requestCultureFeature.Provider != null)
//        {
//            return null;
//        }

//        var settingProvider = httpContext.RequestServices.GetRequiredService<ISettingProvider>();
        String defaultLanguage =  settingProvider.getOrNull(LocalizationSettingNames.DEFAULT_LANGUAGE);
        if (StringUtils.isBlank(defaultLanguage))
        {
            return null;
        }

        String language;
        String country;

        if (defaultLanguage.contains(";"))
        {
            String[] splitted = defaultLanguage.split(";");
            language = splitted[0];
            country = splitted[1];
        }
        else
        {
            language = defaultLanguage;
            country = defaultLanguage;
        }
        return new Locale(language, country);
    }
}
