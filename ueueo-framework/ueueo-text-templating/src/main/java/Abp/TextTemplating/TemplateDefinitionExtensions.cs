using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.TextTemplating.VirtualFiles;

namespace Volo.Abp.TextTemplating;

public static class TemplateDefinitionExtensions
{
    public static TemplateDefinition WithVirtualFilePath(
        @Nonnull this TemplateDefinition templateDefinition,
        @Nonnull String virtualPath,
        boolean isInlineLocalized)
    {
        Objects.requireNonNull(templateDefinition, nameof(templateDefinition));

        templateDefinition.IsInlineLocalized = isInlineLocalized;

        return templateDefinition.WithProperty(
            VirtualFileTemplateContentContributor.VirtualPathPropertyName,
            virtualPath
        );
    }

    public static String GetVirtualFilePathOrNull(
        @Nonnull this TemplateDefinition templateDefinition)
    {
        Objects.requireNonNull(templateDefinition, nameof(templateDefinition));

        return templateDefinition
            .Properties
            .GetOrDefault(VirtualFileTemplateContentContributor.VirtualPathPropertyName) as String;
    }
}
