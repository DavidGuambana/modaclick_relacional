package com.david.modaclick_relacional.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@Data
@RequiredArgsConstructor
public class ApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
}