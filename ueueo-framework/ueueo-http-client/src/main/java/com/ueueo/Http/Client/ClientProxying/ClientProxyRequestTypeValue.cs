using System;
using System.Collections;
using System.Collections.Generic;

namespace Volo.Abp.Http.Client.ClientProxying;

public class ClientProxyRequestTypeValue : IEnumerable<KeyValuePair<Type, Object>>
{
    public List<KeyValuePair<Type, Object>> Values ;// { get; private set; }

    public ClientProxyRequestTypeValue()
    {
        Values = new List<KeyValuePair<Type, Object>>();
    }

    public void Add(Type type, Object value)
    {
        Values.Add(new KeyValuePair<Type, Object>(type, value));
    }

    public IEnumerator<KeyValuePair<Type, Object>> GetEnumerator()
    {
        return Values.GetEnumerator();
    }

    IEnumerator IEnumerable.GetEnumerator()
    {
        return GetEnumerator();
    }
}
