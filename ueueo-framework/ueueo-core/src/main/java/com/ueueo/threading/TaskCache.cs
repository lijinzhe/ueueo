using System.Threading.Tasks;

namespace Volo.Abp.Threading;

public static class TaskCache
{
    public static Boolean>  TrueResult;//  { get; }
    public static Boolean>  FalseResult;//  { get; }

    static TaskCache()
    {
        TrueResult = Task.FromResult(true);
        FalseResult = Task.FromResult(false);
    }
}
