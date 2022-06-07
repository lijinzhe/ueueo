using System;
using System.Collections.Generic;
using System.Text;
using Volo.Abp.Caching;

namespace Volo.Abp.BlobStoring.Aliyun;

[Serializable]
public class AliyunTemporaryCredentialsCacheItem
{
    public String AccessKeyId;// { get; set; }

    public String AccessKeySecret;// { get; set; }

    public String SecurityToken;// { get; set; }

    public AliyunTemporaryCredentialsCacheItem()
    {

    }

    public AliyunTemporaryCredentialsCacheItem(String accessKeyId, String accessKeySecret, String securityToken)
    {
        AccessKeyId = accessKeyId;
        AccessKeySecret = accessKeySecret;
        SecurityToken = securityToken;
    }
}
