package com.dbproj.manageprompt.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-ncp.yml")
@Configuration
@Getter
public class NCPProperties {
    @Value("${ncp_access_key}")
    private String ncp_access_key;
    @Value("${ncp_secret_key}")
    private String ncp_secret_key;
    @Value("${ncp_sender_email}")
    private String ncp_sender_email;
    @Value("${ncp_sender_name}")
    private String ncp_sender_name;
    @Value("${ncp_email_title}")
    private String ncp_email_title;
    @Value("${ncp_cloud_outbound_mailer_api_url}")
    private String ncp_cloud_outbound_mailer_api_url;
    @Value("${ncp_cloud_outbound_mailer_api_url_small}")
    private String ncp_cloud_outbound_mailer_api_url_small;
}
