package io.mosip.esignet.plugin.peru.service;

import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDException;
import foundation.identity.jsonld.JsonLDObject;
import io.mosip.esignet.api.dto.VCRequestDto;
import io.mosip.esignet.api.dto.VCResult;
import io.mosip.esignet.api.exception.VCIExchangeException;
import io.mosip.esignet.api.spi.VCIssuancePlugin;
import io.mosip.esignet.api.util.ErrorConstants;
import io.mosip.esignet.core.dto.OIDCTransaction;
import io.mosip.esignet.core.dto.vci.ParsedAccessToken;
import io.mosip.esignet.core.exception.EsignetException;
import io.mosip.esignet.core.util.IdentityProviderUtil;
import io.mosip.kernel.core.keymanager.spi.KeyStore;
import io.mosip.kernel.core.util.CryptoUtil;
import io.mosip.kernel.keymanagerservice.constant.KeymanagerConstant;
import io.mosip.kernel.keymanagerservice.entity.KeyAlias;
import io.mosip.kernel.keymanagerservice.helper.KeymanagerDBHelper;
import io.mosip.kernel.signature.dto.JWTSignatureRequestDto;
import io.mosip.kernel.signature.dto.JWTSignatureResponseDto;
import io.mosip.kernel.signature.service.SignatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.stereotype.Component;

import java.util.*;

@ConditionalOnProperty(value = "mosip.esignet.integration.vci-plugin", havingValue = "MockVCIssuancePlugin")
@Component
@Slf4j
public class MockVCIssuancePlugin implements VCIssuancePlugin {

	@Override
	public VCResult<JsonLDObject> getVerifiableCredentialWithLinkedDataProof(VCRequestDto vcRequestDto, String holderId, Map<String, Object> identityDetails) throws VCIExchangeException {
		throw new VCIExchangeException(ErrorConstants.NOT_IMPLEMENTED);
	}

	@Override
	public VCResult<String> getVerifiableCredential(VCRequestDto vcRequestDto, String holderId, Map<String, Object> identityDetails) throws VCIExchangeException {
		throw new VCIExchangeException(ErrorConstants.NOT_IMPLEMENTED);
	}
}
