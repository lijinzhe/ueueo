using System.Collections.Generic;
using JetBrains.Annotations;

namespace Volo.Abp.Sms;

public class SmsMessage
{
    public String PhoneNumber;//  { get; }

    public String Text;//  { get; }

    public IDictionary<String, Object> Properties;//  { get; }

    public SmsMessage(@NonNull String phoneNumber, @NonNull String text)
    {
        PhoneNumber = Check.NotNullOrWhiteSpace(phoneNumber, nameof(phoneNumber));
        Text = Check.NotNullOrWhiteSpace(text, nameof(text));

        Properties = new Dictionary<String, Object>();
    }
}
