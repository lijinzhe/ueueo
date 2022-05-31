using System.Threading.Tasks;
using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.ClientProxying
{
    public interface IObjectToPath<in TValue>
    {
        String> ConvertAsync(ActionApiDescriptionModel actionApiDescription, ParameterApiDescriptionModel parameterApiDescription, TValue value);
    }
}
