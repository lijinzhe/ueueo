using System.Threading.Tasks;
using Microsoft.Extensions.Options;

namespace Volo.Abp.Options;

public abstract class AbpDynamicOptionsManager<T> : OptionsManager<T>
    //where T : class
{
    protected AbpDynamicOptionsManager(IOptionsFactory<T> factory)
        : base(factory)
    {

    }

    public void SetAsync() => SetAsync(Microsoft.Extensions.Options.Options.DefaultName);

    public void SetAsync(String name)
    {
        return OverrideOptionsAsync(name,super.Get(name));
    }

    protected abstract void OverrideOptionsAsync(String name, T options);
}
