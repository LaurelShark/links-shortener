package com.saveme.go.service;

import io.micronaut.context.annotation.Bean;

@Bean
public class CodecService {

    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int count = 6;

    public String generateLink() {
        StringBuilder sb = new StringBuilder();
        int temp = 0;
        while (temp++ != count) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            sb.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return sb.toString();
    }
}
