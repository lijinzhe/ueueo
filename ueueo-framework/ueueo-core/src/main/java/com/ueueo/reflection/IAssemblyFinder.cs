using System.Collections.Generic;
using System.Reflection;

namespace Volo.Abp.Reflection;

/**
 * Used to get assemblies in the application.
 * It may not return all assemblies, but those are related with modules.
*/
public interface IAssemblyFinder
{
    IReadOnlyList<Assembly> Assemblies;//  { get; }
}
