using System;
using System.IO;
using JetBrains.Annotations;

namespace Volo.Abp.Modularity.PlugIns;

public static class PlugInSourceListExtensions
{
    public static void AddFolder(
        @NonNull this PlugInSourceList list,
        @NonNull String folder,
        SearchOption searchOption = SearchOption.TopDirectoryOnly)
    {
        Objects.requireNonNull(list, nameof(list));

        list.Add(new FolderPlugInSource(folder, searchOption));
    }

    public static void AddTypes(
        @NonNull this PlugInSourceList list,
        params Type[] moduleTypes)
    {
        Objects.requireNonNull(list, nameof(list));

        list.Add(new TypePlugInSource(moduleTypes));
    }

    public static void AddFiles(
        @NonNull this PlugInSourceList list,
        params String[] filePaths)
    {
        Objects.requireNonNull(list, nameof(list));

        list.Add(new FilePlugInSource(filePaths));
    }
}
