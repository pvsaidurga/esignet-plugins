/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.mosip.esignet.plugin.peru.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mosip.esignet.api.dto.*;
import io.mosip.esignet.api.exception.KycAuthException;
import io.mosip.esignet.api.exception.KycExchangeException;
import io.mosip.esignet.api.exception.SendOtpException;
import io.mosip.esignet.api.spi.Authenticator;
import io.mosip.esignet.plugin.peru.dto.KycExchangeRequestDto;
import io.mosip.kernel.core.util.StringUtils;
import io.mosip.kernel.keymanagerservice.dto.AllCertificatesDataResponseDto;
import io.mosip.kernel.keymanagerservice.dto.CertificateDataResponseDto;
import io.mosip.kernel.keymanagerservice.service.KeymanagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ConditionalOnProperty(value = "mosip.esignet.integration.authenticator", havingValue = "PeruAuthenticationService")
@Component
@Slf4j
public class PeruAuthenticationService implements Authenticator {

    private static final String APPLICATION_ID = "MOCK_AUTHENTICATION_SERVICE";
    public static final String SEND_OTP_FAILED = "send_otp_failed";

    @Value("${mosip.esignet.mock.authenticator.kyc-exchange-url}")
    private String kycExchangeUrl;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KeymanagerService keymanagerService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void initialize() {
        log.info("Started to setup MOCK IDA");
    }

    @Validated
    @Override
    public KycAuthResult doKycAuth(@NotBlank String relyingPartyId, @NotBlank String clientId,
                                   @NotNull @Valid KycAuthDto kycAuthDto) throws KycAuthException {

        log.info("Started to build kyc-auth request with transactionId : {} && clientId : {}",
                kycAuthDto.getTransactionId(), clientId);
        return helperService.doKycAuth(relyingPartyId, clientId, kycAuthDto);
    }

    @Override
    public KycExchangeResult doKycExchange(String relyingPartyId, String clientId, KycExchangeDto kycExchangeDto)
            throws KycExchangeException {
        log.info("Started to build kyc-exchange request with transactionId : {} && clientId : {}",
                kycExchangeDto.getTransactionId(), clientId);
        try {
            KycExchangeRequestDto kycExchangeRequestDto = new KycExchangeRequestDto();
            kycExchangeRequestDto.setRequestDateTime(HelperService.getUTCDateTime());
            kycExchangeRequestDto.setTransactionId(kycExchangeDto.getTransactionId());
            kycExchangeRequestDto.setKycToken(kycExchangeDto.getKycToken());
            kycExchangeRequestDto.setIndividualId(kycExchangeDto.getIndividualId());
            kycExchangeRequestDto.setAcceptedClaims(kycExchangeDto.getAcceptedClaims());
            kycExchangeRequestDto.setClaimLocales(Arrays.asList(kycExchangeDto.getClaimsLocales()));
            return helperService.kycExchange(relyingPartyId, clientId, kycExchangeRequestDto);
        } catch (KycExchangeException e) {
            throw e;
        } catch (Exception e) {
            log.error("IDA Kyc-exchange failed with clientId : {}", clientId, e);
        }
        throw new KycExchangeException("peru-ida-005", "Failed to build kyc data");
    }

    @Override
    public SendOtpResult sendOtp(String relyingPartyId, String clientId, SendOtpDto sendOtpDto)
            throws SendOtpException {
        if (sendOtpDto == null || StringUtils.isEmpty(sendOtpDto.getTransactionId())) {
            throw new SendOtpException("invalid_transaction_id");
        }

        return helperService.sendOtpMock(sendOtpDto.getTransactionId(), sendOtpDto.getIndividualId(), sendOtpDto.getOtpChannels(), relyingPartyId, clientId);
    }

    @Override
    public boolean isSupportedOtpChannel(String channel) {
        return helperService.isSupportedOtpChannel(channel);
    }

    @Override
    public List<KycSigningCertificateData> getAllKycSigningCertificates() {
        List<KycSigningCertificateData> certs = new ArrayList<>();
        AllCertificatesDataResponseDto allCertificatesDataResponseDto = keymanagerService.getAllCertificates(APPLICATION_ID,
                Optional.empty());
        for (CertificateDataResponseDto dto : allCertificatesDataResponseDto.getAllCertificates()) {
            certs.add(new KycSigningCertificateData(dto.getKeyId(), dto.getCertificateData(),
                    dto.getExpiryAt(), dto.getIssuedAt()));
        }
        return certs;
    }
}