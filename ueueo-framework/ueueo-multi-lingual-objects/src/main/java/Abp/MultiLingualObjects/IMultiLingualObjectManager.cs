using System.Threading.Tasks;

namespace Volo.Abp.MultiLingualObjects;

public interface IMultiLingualObjectManager
{
    TTranslation> GetTranslationAsync<TMultiLingual, TTranslation>(
        TMultiLingual multiLingual,
        String culture = null,
        boolean fallbackToParentCultures = true)
        where TMultiLingual : IMultiLingualObject<TTranslation>
        where TTranslation : class, IObjectTranslation;
}
