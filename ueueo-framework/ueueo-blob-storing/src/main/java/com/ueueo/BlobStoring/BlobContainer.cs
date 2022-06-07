using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;
using Volo.Abp.MultiTenancy;
using Volo.Abp.Threading;

namespace Volo.Abp.BlobStoring;

public class BlobContainer<TContainer> : IBlobContainer<TContainer>
    //where TContainer : class
{
    private readonly IBlobContainer _container;

    public BlobContainer(IBlobContainerFactory blobContainerFactory)
    {
        _container = blobContainerFactory.Create<TContainer>();
    }

    public void SaveAsync(
        String name,
        Stream stream,
        boolean overrideExisting = false,
        CancellationToken cancellationToken = default)
    {
        return _container.SaveAsync(
            name,
            stream,
            overrideExisting,
            cancellationToken
        );
    }

    public Boolean  DeleteAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        return _container.DeleteAsync(
            name,
            cancellationToken
        );
    }

    public Boolean  ExistsAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        return _container.ExistsAsync(
            name,
            cancellationToken
        );
    }

    public Stream GetAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        return _container.GetAsync(
            name,
            cancellationToken
        );
    }

    public Stream GetOrNullAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        return _container.GetOrNullAsync(
            name,
            cancellationToken
        );
    }
}

public class BlobContainer : IBlobContainer
{
    protected String ContainerName;//  { get; }

    protected BlobContainerConfiguration Configuration;//  { get; }

    protected IBlobProvider Provider;//  { get; }

    protected ICurrentTenant CurrentTenant;//  { get; }

    protected ICancellationTokenProvider CancellationTokenProvider;//  { get; }

    protected IServiceProvider ServiceProvider;//  { get; }

    protected IBlobNormalizeNamingService BlobNormalizeNamingService;//  { get; }

    public BlobContainer(
        String containerName,
        BlobContainerConfiguration configuration,
        IBlobProvider provider,
        ICurrentTenant currentTenant,
        ICancellationTokenProvider cancellationTokenProvider,
        IBlobNormalizeNamingService blobNormalizeNamingService,
        IServiceProvider serviceProvider)
    {
        ContainerName = containerName;
        Configuration = configuration;
        Provider = provider;
        CurrentTenant = currentTenant;
        CancellationTokenProvider = cancellationTokenProvider;
        BlobNormalizeNamingService = blobNormalizeNamingService;
        ServiceProvider = serviceProvider;
    }

    public   void SaveAsync(
        String name,
        Stream stream,
        boolean overrideExisting = false,
        CancellationToken cancellationToken = default)
    {
        using (CurrentTenant.Change(GetTenantIdOrNull()))
        {
            var blobNormalizeNaming = BlobNormalizeNamingService.NormalizeNaming(Configuration, ContainerName, name);

            Provider.SaveAsync(
                new BlobProviderSaveArgs(
                    blobNormalizeNaming.ContainerName,
                    Configuration,
                    blobNormalizeNaming.BlobName,
                    stream,
                    overrideExisting,
                    CancellationTokenProvider.FallbackToProvider(cancellationToken)
                )
            );
        }
    }

    public    Boolean  DeleteAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        using (CurrentTenant.Change(GetTenantIdOrNull()))
        {
            var blobNormalizeNaming =
                BlobNormalizeNamingService.NormalizeNaming(Configuration, ContainerName, name);

            return Provider.DeleteAsync(
                new BlobProviderDeleteArgs(
                    blobNormalizeNaming.ContainerName,
                    Configuration,
                    blobNormalizeNaming.BlobName,
                    CancellationTokenProvider.FallbackToProvider(cancellationToken)
                )
            );
        }
    }

    public    Boolean  ExistsAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        using (CurrentTenant.Change(GetTenantIdOrNull()))
        {
            var blobNormalizeNaming =
                BlobNormalizeNamingService.NormalizeNaming(Configuration, ContainerName, name);

            return Provider.ExistsAsync(
                new BlobProviderExistsArgs(
                    blobNormalizeNaming.ContainerName,
                    Configuration,
                    blobNormalizeNaming.BlobName,
                    CancellationTokenProvider.FallbackToProvider(cancellationToken)
                )
            );
        }
    }

    public    Stream GetAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        var stream = GetOrNullAsync(name, cancellationToken);

        if (stream == null)
        {
            //TODO: Consider to throw some type of "not found" exception and handle on the HTTP status side
            throw new AbpException(
                $"Could not found the requested BLOB '{name}' in the container '{ContainerName}'!");
        }

        return stream;
    }

    public    Stream GetOrNullAsync(
        String name,
        CancellationToken cancellationToken = default)
    {
        using (CurrentTenant.Change(GetTenantIdOrNull()))
        {
            var blobNormalizeNaming =
                BlobNormalizeNamingService.NormalizeNaming(Configuration, ContainerName, name);

            return Provider.GetOrNullAsync(
                new BlobProviderGetArgs(
                    blobNormalizeNaming.ContainerName,
                    Configuration,
                    blobNormalizeNaming.BlobName,
                    CancellationTokenProvider.FallbackToProvider(cancellationToken)
                )
            );
        }
    }

    protected   ID GetTenantIdOrNull()
    {
        if (!Configuration.IsMultiTenant)
        {
            return null;
        }

        return CurrentTenant.Id;
    }
}
