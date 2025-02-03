package io.mosip.esignet.plugin.peru.dto;

import lombok.Data;

@Data
public class ReCaptchaError {

    private String errorCode;
    private String message;
}
