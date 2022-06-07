using System.Collections.Generic;
using System.Net.Http;
using System.Threading.Tasks;
using Volo.Abp.Http.Modeling;

namespace Volo.Abp.Http.Client.ClientProxying;

public interface IObjectToFormData<in TValue>
{
    List<KeyValuePair<String, HttpContent>>> ConvertAsync(ActionApiDescriptionModel actionApiDescription, ParameterApiDescriptionModel parameterApiDescription, TValue value);
}
