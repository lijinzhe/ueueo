using System;
using System.Linq;
using System.Threading.Tasks;
using Volo.Abp.SimpleStateChecking;

namespace Volo.Abp.GlobalFeatures;

public class RequireGlobalFeaturesSimpleStateChecker<TState> : ISimpleStateChecker<TState>
    where TState : IHasSimpleStateCheckers<TState>
{
    private readonly String[] _globalFeatureNames;
    private readonly boolean _requiresAll;

    public RequireGlobalFeaturesSimpleStateChecker(params String[] globalFeatureNames)
        : this(true, globalFeatureNames)
    {
    }

    public RequireGlobalFeaturesSimpleStateChecker(boolean requiresAll, params String[] globalFeatureNames)
    {
        Check.NotNullOrEmpty(globalFeatureNames, nameof(globalFeatureNames));

        _requiresAll = requiresAll;
        _globalFeatureNames = globalFeatureNames;
    }

    public RequireGlobalFeaturesSimpleStateChecker(boolean requiresAll, params Type[] globalFeatureNames)
    {
        Check.NotNullOrEmpty(globalFeatureNames, nameof(globalFeatureNames));

        _requiresAll = requiresAll;
        _globalFeatureNames = globalFeatureNames.Select(GlobalFeatureNameAttribute.GetName).ToArray();
    }

    public Task<bool> IsEnabledAsync(SimpleStateCheckerContext<TState> context)
    {
        var isEnabled = _requiresAll
            ? _globalFeatureNames.All(x => GlobalFeatureManager.Instance.IsEnabled(x))
            : _globalFeatureNames.Any(x => GlobalFeatureManager.Instance.IsEnabled(x));

        return Task.FromResult(isEnabled);
    }
}
