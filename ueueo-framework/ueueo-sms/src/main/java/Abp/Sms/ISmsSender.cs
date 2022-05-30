using System.Threading.Tasks;

namespace Volo.Abp.Sms;

public interface ISmsSender
{
    void SendAsync(SmsMessage smsMessage);
}
