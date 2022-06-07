using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Threading;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Primitives;
using Volo.Abp.DependencyInjection;

namespace Volo.Abp.VirtualFileSystem;

//TODO: Work with directory & wildcard watches!
//TODO: Work with dictionaries!

 * <remarks>
 * Current implementation only supports file watch.
 * Does not support directory or wildcard watches.
 * </remarks>
public class DynamicFileProvider : DictionaryBasedFileProvider, IDynamicFileProvider, ISingletonDependency
{
    protected override IDictionary<String, IFileInfo> Files => DynamicFiles;

    protected ConcurrentDictionary<String, IFileInfo> DynamicFiles;//  { get; }

    protected ConcurrentDictionary<String, ChangeTokenInfo> FilePathTokenLookup;//  { get; }

    public DynamicFileProvider()
    {
        FilePathTokenLookup = new ConcurrentDictionary<String, ChangeTokenInfo>(StringComparer.OrdinalIgnoreCase); ;
        DynamicFiles = new ConcurrentDictionary<String, IFileInfo>();
    }

    public void AddOrUpdate(IFileInfo fileInfo)
    {
        var filePath = fileInfo.GetVirtualOrPhysicalPathOrNull();
        DynamicFiles.AddOrUpdate(filePath, fileInfo, (key, value) => fileInfo);
        ReportChange(filePath);
    }

    public boolean Delete(String filePath)
    {
        if (!DynamicFiles.TryRemove(filePath, out _))
        {
            return false;
        }

        ReportChange(filePath);
        return true;
    }

    @Override
    public IChangeToken Watch(String filter)
    {
        return GetOrAddChangeToken(filter);
    }

    private IChangeToken GetOrAddChangeToken(String filePath)
    {
        if (!FilePathTokenLookup.TryGetValue(filePath, out var tokenInfo))
        {
            var cancellationTokenSource = new CancellationTokenSource();
            var cancellationChangeToken = new CancellationChangeToken(cancellationTokenSource.Token);
            tokenInfo = new ChangeTokenInfo(cancellationTokenSource, cancellationChangeToken);
            tokenInfo = FilePathTokenLookup.GetOrAdd(filePath, tokenInfo);
        }

        return tokenInfo.ChangeToken;
    }

    private void ReportChange(String filePath)
    {
        if (FilePathTokenLookup.TryRemove(filePath, out var tokenInfo))
        {
            tokenInfo.TokenSource.Cancel();
        }
    }

    protected struct ChangeTokenInfo
    {
        public ChangeTokenInfo(
            CancellationTokenSource tokenSource,
            CancellationChangeToken changeToken)
        {
            TokenSource = tokenSource;
            ChangeToken = changeToken;
        }

        public CancellationTokenSource TokenSource;//  { get; }

        public CancellationChangeToken ChangeToken;//  { get; }
    }
}
