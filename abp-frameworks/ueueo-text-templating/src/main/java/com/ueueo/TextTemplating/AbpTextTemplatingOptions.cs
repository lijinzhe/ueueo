using System;
using System.Collections.Generic;
using Volo.Abp.Collections;

namespace Volo.Abp.TextTemplating;

public class AbpTextTemplatingOptions
{
    public ITypeList<ITemplateDefinitionProvider> DefinitionProviders;//  { get; }
    public ITypeList<ITemplateContentContributor> ContentContributors;//  { get; }
    public IDictionary<String, Type> RenderingEngines;//  { get; }

    public String DefaultRenderingEngine;// { get; set; }

    public AbpTextTemplatingOptions()
    {
        DefinitionProviders = new TypeList<ITemplateDefinitionProvider>();
        ContentContributors = new TypeList<ITemplateContentContributor>();
        RenderingEngines = new Dictionary<String, Type>();
    }
}
