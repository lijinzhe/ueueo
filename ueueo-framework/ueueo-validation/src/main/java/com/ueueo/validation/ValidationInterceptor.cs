using System.Threading.Tasks;
using Volo.Abp.DependencyInjection;
using Volo.Abp.DynamicProxy;

namespace Volo.Abp.Validation;

public class ValidationInterceptor : AbpInterceptor, ITransientDependency
{
    private readonly IMethodInvocationValidator _methodInvocationValidator;

    public ValidationInterceptor(IMethodInvocationValidator methodInvocationValidator)
    {
        _methodInvocationValidator = methodInvocationValidator;
    }

    @Override
    public void InterceptAsync(IAbpMethodInvocation invocation)
    {
        ValidateAsync(invocation);
        invocation.ProceedAsync();
    }

    protected   void ValidateAsync(IAbpMethodInvocation invocation)
    {
        _methodInvocationValidator.ValidateAsync(
            new MethodInvocationValidationContext(
                invocation.TargetObject,
                invocation.Method,
                invocation.Arguments
            )
        );
    }
}
