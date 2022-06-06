package Abp.AspNetCore.ExceptionHandling;

import lombok.Data;

@Data
public class AbpExceptionHandlingOptions {
    private boolean sendExceptionsDetailsToClients = false;

    private boolean sendStackTraceToClients = true;
}
