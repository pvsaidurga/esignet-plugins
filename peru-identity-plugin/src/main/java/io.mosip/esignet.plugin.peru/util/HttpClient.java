package io.mosip.esignet.plugin.peru.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.mosip.esignet.plugin.peru.dto.peru.Envelope;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {

    private static String endpointUrl = "http://65.1.93.129/consultadnie/ConsultaDniService";
    private static String soapRequestPreFix="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:end=\"http://endpoint.wsconsultadni.reniec.gob.pe/\">\n" +
            "                       <soapenv:Header/>\n" +
            "                       <soapenv:Body>\n" +
            "                           <end:consultar>\n" +
            "                               <arg0>\n" +
            "                                   <!--Optional:-->\n" +
            "                                   <nuDniConsulta>";
    private static String soapRequestPostFix="</nuDniConsulta>\\n\" +\n" +
            "            \"                                   <!--Optional:-->\\n\" +\n" +
            "            \"                                   <nuDniUsuario>06794000</nuDniUsuario>\\n\" +\n" +
            "            \"                                   <!--Optional:-->\\n\" +\n" +
            "            \"                                   <nuRucUsuario>20295613620</nuRucUsuario>\\n\" +\n" +
            "            \"                                   <!--Optional:-->\\n\" +\n" +
            "            \"                                   <password>06794000</password>\\n\" +\n" +
            "            \"                               </arg0>\\n\" +\n" +
            "            \"                           </end:consultar>\\n\" +\n" +
            "            \"                       </soapenv:Body>\\n\" +\n" +
            "            \"                   </soapenv:Envelope>";


    public static String sendSOAPRequest(String dni) throws Exception {

        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setDoOutput(true);
        // Send SOAP request
        String soapRequest=soapRequestPreFix+dni+soapRequestPostFix;
        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(soapRequest.getBytes());
            outputStream.flush();
        }
        // Read the response
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } else {
            throw new RuntimeException("HTTP error code: " + connection.getResponseCode());
        }
    }

    public static Envelope getResponse(String soapResponse) throws Exception {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(soapResponse, Envelope.class);
    }
}
