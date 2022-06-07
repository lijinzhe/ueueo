using JetBrains.Annotations;

namespace Volo.Abp.GlobalFeatures;

public abstract class GlobalFeature
{
    [NotNull]
    public GlobalModuleFeatures Module;//  { get; }

    [NotNull]
    public GlobalFeatureManager FeatureManager;//  { get; }

    [NotNull]
    public String FeatureName;//  { get; }

    public boolean IsEnabled {
        get => FeatureManager.IsEnabled(FeatureName);
        set => SetEnabled(value);
    }

    protected GlobalFeature(@NonNull GlobalModuleFeatures module)
    {
        Module = Objects.requireNonNull(module, nameof(module));
        FeatureManager = Module.FeatureManager;
        FeatureName = GlobalFeatureNameAttribute.GetName(GetType());
    }

    public   void Enable()
    {
        FeatureManager.Enable(FeatureName);
    }

    public   void Disable()
    {
        FeatureManager.Disable(FeatureName);
    }

    public void SetEnabled(boolean isEnabled)
    {
        if (isEnabled)
        {
            Enable();
        }
        else
        {
            Disable();
        }
    }
}
