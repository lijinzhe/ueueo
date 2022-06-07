namespace Volo.Abp.Emailing;

/**
 * Declares names of the settings defined by <see cref="EmailSettingProvider"/>.
*/
public static class EmailSettingNames
{
    /**
     * Abp.Net.Mail.DefaultFromAddress
    */
    public const String DefaultFromAddress = "Abp.Mailing.DefaultFromAddress";

    /**
     * Abp.Net.Mail.DefaultFromDisplayName
    */
    public const String DefaultFromDisplayName = "Abp.Mailing.DefaultFromDisplayName";

    /**
     * SMTP related email settings.
    */
    public static class Smtp
    {
        /**
         * Abp.Net.Mail.Smtp.Host
        */
        public const String Host = "Abp.Mailing.Smtp.Host";

        /**
         * Abp.Net.Mail.Smtp.Port
        */
        public const String Port = "Abp.Mailing.Smtp.Port";

        /**
         * Abp.Net.Mail.Smtp.UserName
        */
        public const String UserName = "Abp.Mailing.Smtp.UserName";

        /**
         * Abp.Net.Mail.Smtp.Password
        */
        public const String Password = "Abp.Mailing.Smtp.Password";

        /**
         * Abp.Net.Mail.Smtp.Domain
        */
        public const String Domain = "Abp.Mailing.Smtp.Domain";

        /**
         * Abp.Net.Mail.Smtp.EnableSsl
        */
        public const String EnableSsl = "Abp.Mailing.Smtp.EnableSsl";

        /**
         * Abp.Net.Mail.Smtp.UseDefaultCredentials
        */
        public const String UseDefaultCredentials = "Abp.Mailing.Smtp.UseDefaultCredentials";
    }
}
