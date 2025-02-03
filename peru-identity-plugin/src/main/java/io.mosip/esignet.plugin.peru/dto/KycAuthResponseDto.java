package io.mosip.esignet.plugin.peru.dto;


import lombok.Data;

@Data
public class KycAuthResponseDto {

    private boolean authStatus;
    private String kycToken;
    private String partnerSpecificUserToken;
}