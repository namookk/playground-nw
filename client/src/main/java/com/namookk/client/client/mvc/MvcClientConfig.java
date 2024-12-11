package com.namookk.client.client.mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Slf4j
@Configuration
public class MvcClientConfig {

  private String cachedToken;

  @Bean
  public MvcRestClient mvcRestClient() {
    RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .requestInterceptor(((request, body, execution) -> {
          logRequestDetails(request, body);
          return execution.execute(request, body);
        }))
        .defaultStatusHandler(HttpStatusCode::is4xxClientError, ((request, response) -> {
          //400번대 에러
          String responseBody = getResponseBodyAsString(response);
          log.error("Error 4xx Request : {}", request);
          log.error("Error 4xx Response : {}", responseBody);
          throw new RuntimeException("Error StatusCode : %d, Message : %s".formatted(response.getStatusCode().value(), responseBody));
        }))
        .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
          //500번대 에러
          log.error("Error 5xx Request : {}", request);
          throw new RuntimeException("Error StatusCode : %d, Message : %s".formatted(response.getStatusCode().value(), getResponseBodyAsString(response)));
        }))
        .build();

    return HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(MvcRestClient.class);
  }


  @Bean
  public UserRestClient userRestClient() {
    RestClient restClient = RestClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .requestInterceptor(((request, body, execution) -> {
          String token = getAuthToken();
          request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
          logRequestDetails(request, body);
          return execution.execute(request, body);
        }))
        .defaultStatusHandler(HttpStatusCode::is4xxClientError, ((request, response) -> {
          //400번대 에러
          String responseBody = getResponseBodyAsString(response);
          log.error("Error 4xx Request : {}", request);
          log.error("Error 4xx Response : {}", responseBody);
          throw new RuntimeException("Error StatusCode : %d, Message : %s".formatted(response.getStatusCode().value(), responseBody));
        }))
        .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
          //500번대 에러
          log.error("Error 5xx Request : {}", request);
          throw new RuntimeException("Error StatusCode : %d, Message : %s".formatted(response.getStatusCode().value(), getResponseBodyAsString(response)));
        }))
        .build();

    return HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(UserRestClient.class);
  }

  private String getAuthToken() {
    if(StringUtils.hasText(cachedToken)) {
      return cachedToken;
    }

    UUID token = mvcRestClient().getToken().getBody();
    this.cachedToken = token.toString();
    return cachedToken;
  }

  private void logRequestDetails(HttpRequest request, byte[] body) {
    try {
      log.info("Request Method: {}", request.getMethod());
      log.info("Request URI: {}", request.getURI());
      log.info("Request Headers: {}", request.getHeaders());
      if (body != null && body.length > 0) {
        log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      log.error("Failed to log request details", e);
    }
  }

  private String getResponseBodyAsString(ClientHttpResponse response) {
    try (InputStream bodyStream = response.getBody()) {
      return new BufferedReader(new InputStreamReader(bodyStream))
          .lines()
          .collect(Collectors.joining("\n"));
    } catch (IOException e) {
      log.error("Failed to read response body", e);
      return "Unable to read response body";
    }
  }
}
