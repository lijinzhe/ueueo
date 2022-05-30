using JetBrains.Annotations;
using System.Threading.Tasks;

namespace Volo.Abp.Sms;

public static class SmsSenderExtensions
{
    public static void SendAsync(@Nonnull this ISmsSender smsSender, @Nonnull String phoneNumber, @Nonnull String text)
    {
        Objects.requireNonNull(smsSender, nameof(smsSender));
        return smsSender.SendAsync(new SmsMessage(phoneNumber, text));
    }
}
