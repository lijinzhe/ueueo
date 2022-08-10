package com.ueueo.ddd.application.contracts.dtos;

import com.ueueo.data.objectextending.ExtensibleObject;
import com.ueueo.validation.IValidatableObject;
import com.ueueo.validation.ValidationContext;
import com.ueueo.validation.ValidationResult;

import java.util.Collection;
import java.util.Collections;

/**
 * Simply implements <see cref="ILimitedResultRequest"/>.
 *
 * @author Lee
 * @date 2021-08-29 09:52
 */
public class LimitedResultRequestDto extends ExtensibleObject implements ILimitedResultRequest, IValidatableObject {

    /** Default value: 10. */
    public static int DefaultMaxResultCount = 10;

    /**
     * Maximum possible value of the <see cref="MaxResultCount"/>.
     * Default value: 1,000.
     */
    public static int MaxMaxResultCount = 1000;

    /**
     * Maximum result count should be returned.
     * This is generally used to limit result count on paging.
     */
    private int maxResultCount = DefaultMaxResultCount;

    @Override
    public int getMaxResultCount() {
        return maxResultCount;
    }

    @Override
    public void setMaxResultCount(int maxResultCount) {
        this.maxResultCount = maxResultCount;
    }

    @Override
    public Collection<ValidationResult> validate(ValidationContext validationContext) {
        if (this.maxResultCount > MaxMaxResultCount) {
            //TODO 如何获取IStringLocalizer？
//            IStringLocalizer localizer = null;
//            LocalizedString message = localizer.get("MaxResultCountExceededExceptionMessage",
//                    "MaxResultCount", MaxMaxResultCount, "LimitedResultRequestDto", MaxMaxResultCount);
//            return Collections.singleton(new ValidationResult(message));
        }
        return Collections.emptyList();
    }

}
