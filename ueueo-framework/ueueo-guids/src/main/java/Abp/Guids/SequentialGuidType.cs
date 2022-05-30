namespace Volo.Abp.Guids;

/**
 * Describes the type of a sequential GUID value.
*/
public enum SequentialGuidType
{
    /**
     * The GUID should be sequential when formatted using the <see cref="Guid.ToString()" /> method.
     * Used by MySql and PostgreSql.
    */
    SequentialAsString,

    /**
     * The GUID should be sequential when formatted using the <see cref="Guid.ToByteArray" /> method.
     * Used by Oracle.
    */
    SequentialAsBinary,

    /**
     * The sequential portion of the GUID should be located at the end of the Data4 block.
     * Used by SqlServer.
    */
    SequentialAtEnd
}
