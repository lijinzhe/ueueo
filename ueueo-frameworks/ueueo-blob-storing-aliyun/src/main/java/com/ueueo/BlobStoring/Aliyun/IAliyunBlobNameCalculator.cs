namespace Volo.Abp.BlobStoring.Aliyun;

public interface IAliyunBlobNameCalculator
{
    String Calculate(BlobProviderArgs args);
}
