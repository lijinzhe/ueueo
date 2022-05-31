package com.ueueo.data.objectextending;

public enum MappingPropertyDefinitionChecksEnum {
    /** No check. Copy all extra properties from the source to the destination. */
    None(0),

    /** Copy the extra properties defined for the source class. */
    Source(1),

    /** Copy the extra properties defined for the destination class. */
    Destination(2),

    /** Copy extra properties defined for both of the source and destination classes. */
    Both(Source.value | Destination.value);

    private int value;

    MappingPropertyDefinitionChecksEnum(int value) {
        this.value = value;
    }

    public boolean hasFlag(MappingPropertyDefinitionChecksEnum checksEnum) {
        return (this.value & checksEnum.value) > 0;
    }
}
