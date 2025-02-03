# peru-identity-plugin

## About
Implementation for all the interfaces defined in esignet-integration-api.

This library should be added as a runtime dependency to [esignet-service](https://github.com/mosip/esignet)

## Configurations required to added in esignet-default.properties

````
mosip.esignet.integration.scan-base-package=io.mosip.esignet.mock.integration
mosip.esignet.integration.authenticator=PeruAuthenticationService
mosip.esignet.integration.key-binder=PeruKeyBindingWrapperService
mosip.esignet.integration.vci-plugin=MockVCIssuancePlugin

#---------------------------------------------------------------------------------Peru_KBI_Configuration----------------------------------------------------------------

mosip.esignet.authenticator.peru-rc.auth-factor.kba.individual-id-field=dni
mosip.esignet.authenticator.peru-rc.auth-factor.kba.field-details={{"id":"dni", "type":"text", "format":"", "maxLength": 10, "regex": "^[^\s]*$"},{"id":"prenombres", "type":"text", "format":"", "maxLength": 50, "regex": "^[^\s]*$"}\
  ,{"id":"primerApellido", "type":"text", "format":"", "maxLength": 50, "regex": "^[^\s]*$"},{"id":"segundoApellido", "type":"text", "format":"", "maxLength": 50, "regex": "^[^\s]*$"},{"id":"fechaNacimiento", "type":"date", "format":"dd/mm/yyyy"}}
mosip.esignet.authenticator.default.auth-factor.kba.field-details=${mosip.esignet.authenticator.peru-rc.auth-factor.kba.field-details}
mosip.esignet.authenticator.default.auth-factor.kba.individual-id-field=${mosip.esignet.authenticator.peru-rc.auth-factor.kba.field-details}

mosip.esignet.auth-challenge.KBI.format=base64url-encoded-json
mosip.esignet.auth-challenge.KBI.min-length=50
mosip.esignet.auth-challenge.KBI.max-length=200


mosip.esignet.cache.size={'clientdetails' : 200, 'preauth': 200, 'authenticated': 200, 'authcodegenerated': 200, 'userinfo': 200, \
   'linkcodegenerated' : 500, 'linked': 200 , 'linkedcode': 200, 'linkedauth' : 200 , 'consented' :200, 'vcissuance':100, \
  'apiRateLimit' : 500, 'blocked': 500,'kycauth': 500 }

mosip.esignet.cache.expire-in-seconds={'clientdetails' : 86400, 'preauth': 180, 'authenticated': ${mosip.esignet.authentication-expire-in-secs}, \
  'authcodegenerated': 60, 'userinfo': ${mosip.esignet.access-token-expire-seconds}, 'linkcodegenerated' : ${mosip.esignet.link-code-expire-in-secs}, \
  'linked': 60 , 'linkedcode': ${mosip.esignet.link-code-expire-in-secs}, 'linkedauth' : ${mosip.esignet.authentication-expire-in-secs}, \
  'consented': 120, 'vcissuance': ${mosip.esignet.access-token-expire-seconds}, 'apiRateLimit' : 180, 'blocked': 300, 'kycauth':1800}

mosip.esignet.openid.peru.scope.mapping={'name':'prenombres','gender':'genero','given_name':'primerapellido', 'birthdate':'fechanacimiento'}

mosip.esignet.cache.names=clientdetails,preauth,authenticated,authcodegenerated,userinfo,linkcodegenerated,linked,linkedcode,\
  linkedauth,consented,vcissuance,apiRateLimit,blocked,kycauth

mosip.esignet.amr-acr-mapping-file-url=https://raw.githubusercontent.com/mosip/mosip-config/refs/heads/camdgc-qa/amr-acr-mapping.json

##-----------------------------VCI related demo configuration---------------------------------------------##

mosip.esignet.vciplugin.sunbird-rc.issue-credential-url=http://164.52.205.87/credentials/issue 
mosip.esignet.vciplugin.sunbird-rc.supported-credential-types=InsuranceCredential,HealthCardCredential
mosip.esignet.vciplugin.sunbird-rc.credential-type.InsuranceCredential.static-value-map.issuerId=did:web:esignet-mock.dev.mosip.net
mosip.esignet.vciplugin.sunbird-rc.credential-type.InsuranceCredential.template-url=requestTemplete.json
mosip.esignet.vciplugin.sunbird-rc.credential-type.InsuranceCredential.registry-get-url=http://10.3.148.107/api/v1/Insurance/
mosip.esignet.vciplugin.sunbird-rc.credential-type.InsuranceCredential.cred-schema-id=did:schema:1e4d93df-4047-4dd7-8515-9ad46796009f
mosip.esignet.vciplugin.sunbird-rc.credential-type.InsuranceCredential.cred-schema-version=1.0.0
mosip.esignet.vciplugin.sunbird-rc.credential-type.HealthCardCredential.static-value-map.issuerId=did:web:esignet-mock.dev.mosip.net
mosip.esignet.vciplugin.sunbird-rc.credential-type.HealthCardCredential.template-url=requestTemplete.json
mosip.esignet.vciplugin.sunbird-rc.credential-type.HealthCardCredential.registry-get-url=http://10.3.148.107/api/v1/Insurance/
mosip.esignet.vciplugin.sunbird-rc.credential-type.HealthCardCredential.cred-schema-id=did:schema:1e4d93df-4047-4dd7-8515-9ad46796009f
mosip.esignet.vciplugin.sunbird-rc.credential-type.HealthCardCredential.cred-schema-version=1.0.0
````


Add "bindingtransaction" cache name in "mosip.esignet.cache.names" property.

## License
This project is licensed under the terms of [Mozilla Public License 2.0](LICENSE).
This integration plugin is compatible with [Sunbird-RC 1.0.0](https://github.com/Sunbird-RC/sunbird-rc-core/tree/v1.0.0)


