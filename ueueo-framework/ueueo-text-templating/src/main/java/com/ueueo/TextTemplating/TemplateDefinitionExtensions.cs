using System.Collections.Generic;
using JetBrains.Annotations;
using Volo.Abp.TextTemplating.VirtualFiles;

namespace Volo.Abp.TextTemplating;

public static class TemplateDefinitionExtensions
{
    public static TemplateDefinition WithVirtualFilePath(
        @NonNull this TemplateDefinition templateDefinition,
        @NonNull String virtualPath,
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
        @NonNull this TemplateDefinition templateDefinition)
    {
        Objects.requireNonNull(templateDefinition, nameof(templateDefinition));

        return templateDefinition
            .Properties
            .GetOrDefault(VirtualFileTemplateContentContributor.VirtualPathPropertyName) as String;
    }
}
