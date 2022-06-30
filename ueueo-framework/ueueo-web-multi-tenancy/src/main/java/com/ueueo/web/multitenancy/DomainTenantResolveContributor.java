package com.ueueo.web.multitenancy;

//TODO: Create a better domain format. We can accept regex for example.

import com.ueueo.multitenancy.ITenantResolveContext;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainTenantResolveContributor extends HttpTenantResolveContributorBase {

    public final String ContributorName = "Domain";

    private final Pattern pattern;

    @Override
    public String getName() {
        return ContributorName;
    }

    public DomainTenantResolveContributor(String domainRegex) {
        pattern = Pattern.compile(domainRegex);
    }

    @Override
    protected String getTenantIdOrNameFromHttpContextOrNull(@NonNull ITenantResolveContext context, @NonNull ServletRequestAttributes requestAttributes) {
        String hostName = requestAttributes.getRequest().getServerName();
        Matcher matcher = pattern.matcher(hostName);
        context.setHandled(true);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}
