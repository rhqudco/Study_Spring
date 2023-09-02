package com.security.session.controller.responseMsg;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;


public class GenerateHttpHeader {
  public static HttpHeaders returnHttpHeader(String type, String subType) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(new MediaType(type, subType, StandardCharsets.UTF_8));

    return headers;
  }

}
