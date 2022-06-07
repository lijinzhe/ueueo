using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.Loader;

namespace Volo.Abp.Reflection;

internal static class AssemblyHelper
{
    public static List<Assembly> LoadAssemblies(String folderPath, SearchOption searchOption)
    {
        return GetAssemblyFiles(folderPath, searchOption)
            .Select(AssemblyLoadContext.Default.LoadFromAssemblyPath)
            .ToList();
    }

    public static IEnumerable<String> GetAssemblyFiles(String folderPath, SearchOption searchOption)
    {
        return Directory
            .EnumerateFiles(folderPath, "*.*", searchOption)
            .Where(s => s.EndsWith(".dll") || s.EndsWith(".exe"));
    }

    public static IReadOnlyList<Type> GetAllTypes(Assembly assembly)
    {
        try
        {
            return assembly.GetTypes();
        }
        catch (ReflectionTypeLoadException ex)
        {
            return ex.Types;
        }
    }
}
