using System;

namespace Volo.Abp.Guids;

/**
 * Used to generate Ids.
*/
public interface IGuidGenerator
{
    /**
     * Creates a new <see cref="Guid"/>.
    */
    ID Create();
}
