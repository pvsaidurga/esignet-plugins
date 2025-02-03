package io.mosip.esignet.plugin.peru.dto;


import io.mosip.esignet.plugin.peru.dto.peru.DatosPersona;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class KycAuth {
    private String kycToken;
    private String partnerSpecificUserToken;

    private LocalDateTime responseTime;

    private Valid validity;

    private String transactionId;
    private String individualId;

    private DatosPersona datosPersona;
}