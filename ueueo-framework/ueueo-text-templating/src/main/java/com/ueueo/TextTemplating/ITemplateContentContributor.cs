using System.Threading.Tasks;

namespace Volo.Abp.TextTemplating;

public interface ITemplateContentContributor
{
    String> GetOrNullAsync(TemplateContentContributorContext context);
}
