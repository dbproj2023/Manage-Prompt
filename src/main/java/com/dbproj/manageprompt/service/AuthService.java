package com.dbproj.manageprompt.service;

import com.dbproj.manageprompt.config.NCPProperties;

import com.dbproj.manageprompt.dto.EmailAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

//    @Autowired
//    private final CertificationDao certificationDao;

    @Autowired
    private final NCPProperties ncpProperties;

    private final TemplateEngine templateEngine;

    // 6자리 인증번호 생성
    private String getVerifyCode(int size) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        log.info("인증코드 생성 : " + buffer.toString());
        return buffer.toString();
    }

    // NCP Signature 생성
    private String makeSignature(String url, String timestamp, String ncp_access_key, String ncp_secret_key) throws NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String message = new StringBuilder()
                .append("POST")
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(ncp_access_key)
                .toString();

        SecretKeySpec signingKey;
        String encodeBase64String = null;
        try {
            signingKey = new SecretKeySpec(ncp_secret_key.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
        } catch (UnsupportedEncodingException e) {
            encodeBase64String = e.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encodeBase64String;
    }

    // 인증 메일 발송
    public Boolean sendEmail(EmailAuthDto emailAuthDto) throws JSONException {

        final String content_type = "POST";
        final String timestamp = Long.toString(System.currentTimeMillis());
        boolean process_result = true;
        String targetEmail = emailAuthDto.getEmail();
        String verifyCode = getVerifyCode(6);
        String mailTemplate = setMailContext(targetEmail, verifyCode);
        String body = makeRequestJsonBody(targetEmail, mailTemplate);

        try {
            URL url = new URL(ncpProperties.getNcp_cloud_outbound_mailer_api_url());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("content-type", "application/json");
            con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
            con.setRequestProperty("x-ncp-iam-access-key", ncpProperties.getNcp_access_key());
            // Signature
            con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(ncpProperties.getNcp_cloud_outbound_mailer_api_url_small(), timestamp, ncpProperties.getNcp_access_key(), ncpProperties.getNcp_secret_key()));
            con.setRequestMethod(content_type);
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(body.getBytes());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            log.info("호출 결과" +" " + responseCode);
//            if(responseCode == 201) {
////                certificationDao.createEmailCertification(targetEmail, verifyCode);
//            } else {
//                process_result = false;
//            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return process_result;
    }

    private String makeRequestJsonBody(String targetEmail, String mailTemplate) throws JSONException {
        JSONObject bodyJson = new JSONObject();
        JSONObject toJsonRecipients = new JSONObject();
        JSONArray toArr = new JSONArray();

        log.info("senderAddress  " + ncpProperties.getNcp_sender_email());

        bodyJson.put("senderAddress", ncpProperties.getNcp_sender_email());
        bodyJson.put("senderName", ncpProperties.getNcp_sender_name());
        bodyJson.put("title", ncpProperties.getNcp_email_title());
        bodyJson.put("body", mailTemplate);

        toJsonRecipients.put("address", targetEmail);
        toJsonRecipients.put("name", ncpProperties.getNcp_sender_name());
        // individual 값이 true이면 "R"(수신자),
        // individual 값이 false이면 "R"(수신자), "C"(참조), "B"(숨은참조)를 지정
        toJsonRecipients.put("type", "R");
        toArr.put(toJsonRecipients);
        bodyJson.put("recipients", toArr);
        bodyJson.put("individual", true); // 개인별 발송 여부
//        bodyJson.put("parameters"., "grade"); // 전체 수신자에게 적용할 parameter
        log.info(bodyJson.toString());
        return bodyJson.toString();
    }

    private String setMailContext(String targetEmail, String verifyCode) {
        Context context = new Context();
        context.setVariable("targetEmail", targetEmail);
        context.setVariable("verifyCode", verifyCode);

        return templateEngine.process("inviteMailForm", context);
    }
}
