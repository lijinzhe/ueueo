using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.FileProviders;
using Volo.Abp.DependencyInjection;
using Volo.Abp.Http.Modeling;
using Volo.Abp.Json;
using Volo.Abp.VirtualFileSystem;

namespace Volo.Abp.Http.Client.ClientProxying;

public class ClientProxyApiDescriptionFinder : IClientProxyApiDescriptionFinder, ISingletonDependency
{
    protected IVirtualFileProvider VirtualFileProvider;//  { get; }
    protected IJsonSerializer JsonSerializer;//  { get; }
    protected Dictionary<String, ActionApiDescriptionModel> ActionApiDescriptionModels;//  { get; }
    protected ApplicationApiDescriptionModel ApplicationApiDescriptionModel;// { get; set; }

    public ClientProxyApiDescriptionFinder(
        IVirtualFileProvider virtualFileProvider,
        IJsonSerializer jsonSerializer)
    {
        VirtualFileProvider = virtualFileProvider;
        JsonSerializer = jsonSerializer;
        ActionApiDescriptionModels = new Dictionary<String, ActionApiDescriptionModel>();

        Initialize();
    }

    public ActionApiDescriptionModel FindAction(String methodName)
    {
        return ActionApiDescriptionModels.ContainsKey(methodName) ? ActionApiDescriptionModels[methodName] : null;
    }

    public ApplicationApiDescriptionModel GetApiDescription()
    {
        return ApplicationApiDescriptionModel;
    }

    private void Initialize()
    {
        ApplicationApiDescriptionModel = GetApplicationApiDescriptionModel();
        var controllers = ApplicationApiDescriptionModel.Modules.Select(x => x.Value).SelectMany(x => x.Controllers.Values).ToList();

        for (var controller in controllers.Where(x => x.Interfaces.Any()))
        {
            var appServiceType = controller.Interfaces.Last().Type;

            for (var actionItem in controller.Actions.Values)
            {
                var actionKey = $"{appServiceType}.{actionItem.Name}.{string.Join("-", actionItem.ParametersOnMethod.Select(x => x.Type))}";

                if (!ActionApiDescriptionModels.ContainsKey(actionKey))
                {
                    ActionApiDescriptionModels.Add(actionKey, actionItem);
                }
            }
        }
    }

    private ApplicationApiDescriptionModel GetApplicationApiDescriptionModel()
    {
        var applicationApiDescription = ApplicationApiDescriptionModel.Create();
        var fileInfoList = new List<IFileInfo>();
        GetGenerateProxyFileInfos(fileInfoList);

        for (var fileInfo in fileInfoList)
        {
            using (var streamReader = new StreamReader(fileInfo.CreateReadStream()))
            {
                var content = streamReader.ReadToEnd();

                var subApplicationApiDescription = JsonSerializer.Deserialize<ApplicationApiDescriptionModel>(content);

                for (var module in subApplicationApiDescription.Modules)
                {
                    if (!applicationApiDescription.Modules.ContainsKey(module.Key))
                    {
                        applicationApiDescription.AddModule(module.Value);
                    }
                }
            }
        }

        return applicationApiDescription;
    }

    private void GetGenerateProxyFileInfos(List<IFileInfo> fileInfoList, String path = "")
    {
        for (var directoryContent in VirtualFileProvider.GetDirectoryContents(path))
        {
            if (directoryContent.IsDirectory)
            {
                GetGenerateProxyFileInfos(fileInfoList, directoryContent.PhysicalPath);
            }
            else
            {
                if (directoryContent.Name.EndsWith("generate-proxy.json"))
                {
                    fileInfoList.Add(VirtualFileProvider.GetFileInfo(directoryContent.GetVirtualOrPhysicalPathOrNull()));
                }
            }
        }
    }
}
