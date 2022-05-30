using System.Threading.Tasks;

namespace Volo.Abp.TextTemplating;

public interface ITemplateContentContributor
{
    Task<String> GetOrNullAsync(TemplateContentContributorContext context);
}
