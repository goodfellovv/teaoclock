package com.dabramov.teaoclock.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Year;

@Configuration
public class ClientConfiguration {
    @Value("${data.server.url}")
    private String dataServerUtl;

    @Bean("dayOffClient")
    public WebClient dayOffClient() {
        return WebClient.create("https://isdayoff.ru/api/getdata?delimeter=,&year=" + Year.now());
    }

    @Bean("dataClient")
    public WebClient dataClient() {
        return WebClient.create(dataServerUtl);
    }
}
