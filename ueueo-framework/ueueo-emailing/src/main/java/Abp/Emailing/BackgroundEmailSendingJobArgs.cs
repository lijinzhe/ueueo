using System;

namespace Volo.Abp.Emailing;

[Serializable]
public class BackgroundEmailSendingJobArgs
{
    public String From;// { get; set; }

    public String To;// { get; set; }

    public String Subject;// { get; set; }

    public String Body;// { get; set; }

    /**
     * Default: true.
    */
    public boolean IsBodyHtml;// { get; set; } = true;

    //TODO: Add other properties and attachments
}
