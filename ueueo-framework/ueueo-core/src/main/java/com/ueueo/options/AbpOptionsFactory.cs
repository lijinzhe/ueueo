using System.Collections.Generic;
using Microsoft.Extensions.Options;

namespace Volo.Abp.Options;

//TODO: Derive from OptionsFactory when this is released: https://github.com/aspnet/Options/pull/258 (or completely remove this!)
// https://github.com/dotnet/runtime/blob/master/src/libraries/Microsoft.Extensions.Options/src/OptionsFactory.cs
public class AbpOptionsFactory<TOptions> : IOptionsFactory<TOptions> //where TOptions : class, new()
{
    private readonly IEnumerable<IConfigureOptions<TOptions>> _setups;
    private readonly IEnumerable<IPostConfigureOptions<TOptions>> _postConfigures;
    private readonly IEnumerable<IValidateOptions<TOptions>> _validations;

    public AbpOptionsFactory(
        IEnumerable<IConfigureOptions<TOptions>> setups,
        IEnumerable<IPostConfigureOptions<TOptions>> postConfigures)
        : this(setups, postConfigures, validations: null)
    {

    }

    public AbpOptionsFactory(
        IEnumerable<IConfigureOptions<TOptions>> setups,
        IEnumerable<IPostConfigureOptions<TOptions>> postConfigures,
        IEnumerable<IValidateOptions<TOptions>> validations)
    {
        _setups = setups;
        _postConfigures = postConfigures;
        _validations = validations;
    }

    public   TOptions Create(String name)
    {
        var options = new TOptions();

        ConfigureOptions(name, options);
        PostConfigureOptions(name, options);
        ValidateOptions(name, options);

        return options;
    }

    protected   void ConfigureOptions(String name, TOptions options)
    {
        for (var setup in _setups)
        {
            if (setup is IConfigureNamedOptions<TOptions> namedSetup)
            {
                namedSetup.Configure(name, options);
            }
            else if (name == Microsoft.Extensions.Options.Options.DefaultName)
            {
                setup.Configure(options);
            }
        }
    }

    protected   void PostConfigureOptions(String name, TOptions options)
    {
        for (var post in _postConfigures)
        {
            post.PostConfigure(name, options);
        }
    }

    protected   void ValidateOptions(String name, TOptions options)
    {
        if (_validations != null)
        {
            var failures = new List<String>();
            for (var validate in _validations)
            {
                var result = validate.Validate(name, options);
                if (result.Failed)
                {
                    failures.AddRange(result.Failures);
                }
            }
            if (failures.Count > 0)
            {
                throw new OptionsValidationException(name, typeof(TOptions), failures);
            }
        }
    }
}
