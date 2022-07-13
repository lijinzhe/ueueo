package com.ueueo.guids;

import com.ueueo.ID;
import com.ueueo.guids.algorithm.LongToStringUtil;

import java.util.UUID;

/**
 * Implements <see cref="IGuidGenerator"/> by using <see cref="UUID.randomUUID()"/>.
 *
 * @author Lee
 * @date 2021-08-20 17:22
 */
public class UuidGenerator implements IGuidGenerator {

    private UuidTypeEnum defaultUuidType = UuidTypeEnum.STANDARD;

    public UuidGenerator() {
    }

    public UuidGenerator(UuidTypeEnum defaultUuidType) {
        setDefaultUuidType(defaultUuidType);
    }

    @Override
    public ID create() {
        return create(defaultUuidType);
    }

    public ID create(UuidTypeEnum uuidType) {
        if (UuidTypeEnum.STANDARD_NO_HYPHEN.equals(uuidType)) {
            UUID uuid = UUID.randomUUID();
            return ID.valueOf(uuid.toString().replace("-", "").toUpperCase());
        } else if (UuidTypeEnum.ENCODE_WITH_36_BIT_ChHAR_SET.equals(uuidType)) {
            UUID uuid = UUID.randomUUID();
            long msb = uuid.getMostSignificantBits();
            long lsb = uuid.getLeastSignificantBits();
            return ID.valueOf(LongToStringUtil.to13String(msb) + LongToStringUtil.to13String(lsb));
        } else if (UuidTypeEnum.ENCODE_WITH_64_BIT_ChHAR_SET.equals(uuidType)) {
            UUID uuid = UUID.randomUUID();
            long msb = uuid.getMostSignificantBits();
            long lsb = uuid.getLeastSignificantBits();
            return ID.valueOf(LongToStringUtil.to11String(msb) + LongToStringUtil.to11String(lsb));
        } else {
            return ID.valueOf(UUID.randomUUID().toString().toUpperCase());
        }
    }

    public UuidTypeEnum getDefaultUuidType() {
        return defaultUuidType;
    }

    public void setDefaultUuidType(UuidTypeEnum defaultUuidType) {
        if (defaultUuidType != null) {
            this.defaultUuidType = defaultUuidType;
        }
    }

}
