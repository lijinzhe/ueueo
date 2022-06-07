using JetBrains.Annotations;
using System.Threading.Tasks;

namespace Volo.Abp.Sms;

public static class SmsSenderExtensions
{
    public static void SendAsync(@NonNull this ISmsSender smsSender, @NonNull String phoneNumber, @NonNull String text)
    {
        Objects.requireNonNull(smsSender, nameof(smsSender));
        return smsSender.SendAsync(new SmsMessage(phoneNumber, text));
    }
}
