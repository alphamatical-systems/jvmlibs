package com.anvitech.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Base64.getEncoder;

/**
 * Http Request representation.
 *
 * @author Dinesh Kumar
 * @since Apr 11, 2021
 */
public class HttpRequest {
  private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

  private final HttpClient httpClient;
  private final java.net.http.HttpRequest.Builder request;
  private String apiName;
  private ObjectMapper mapper;

  /**
   * Creates fully initialized instance.
   *
   * @param request request builder
   */
  public HttpRequest(java.net.http.HttpRequest.Builder request) {
    this.mapper = new ObjectMapper();
    this.request = request;
    this.httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .connectTimeout(Duration.ofSeconds(10))
      .build();
  }

  /**
   * Create a http request for a GET.
   *
   * @param url url
   * @return this
   */
  public static HttpRequest get(String url) {
    return new HttpRequest(create().uri(URI.create(url)));
  }

  /**
   * Create a http request for a GET.
   *
   * @param url    url
   * @param params query parameters
   * @return this
   */
  public static HttpRequest get(String url, Map<String, String> params) {
    return get(String.format("%s?%s", url, toQueryParams(params)));
  }

  /**
   * Create a http request for a POST.
   *
   * @param url  url
   * @param body body
   * @return this
   */
  public static HttpRequest post(String url, Object body) {
    String data;
    try {
      data = new ObjectMapper().writeValueAsString(body);
    } catch (JsonProcessingException e) {
      log.debug("Unable to create POST request with body [{}]", body);
      throw new GatewayException("Unable to create POST request.");
    }
    return new HttpRequest(create().POST(java.net.http.HttpRequest.BodyPublishers.ofString(data)).uri(URI.create(url)));
  }

  /**
   * Create a http request for a POST.
   *
   * @param url    url
   * @param params query params
   * @param body   body
   * @return this
   */
  public static HttpRequest post(String url, Map<String, String> params, Object body) {
    return post(String.format("%s?%s", url, toQueryParams(params)), body);
  }

  /**
   * Sets api name.
   *
   * @param apiName api name
   * @return this
   */
  public HttpRequest apiName(String apiName) {
    this.apiName = apiName;
    return this;
  }

  /**
   * Sets mapper.
   *
   * @param mapper mapper
   * @return this
   */
  public HttpRequest withMapper(ObjectMapper mapper) {
    this.mapper = mapper;
    return this;
  }

  /**
   * Sets header.
   *
   * @param headers headers
   * @return this
   */
  public HttpRequest headers(Map<String, String> headers) {
    headers.keySet().forEach(k -> request.header(k, headers.get(k)));
    return this;
  }

  /**
   * Adds basic auth to the request.
   *
   * @param username username
   * @param password password
   * @return this
   */
  public HttpRequest basicAuth(String username, String password) {
    request.header(
      "Authorization",
      String.format("Basic %s", new String(getEncoder().encode((username + ":" + password).getBytes())))
    );
    return this;
  }

  /**
   * Gets response as given response class type.
   *
   * @param responseClass response class type
   * @param <T>           type
   * @return class instance
   */
  public <T> T as(Class<? extends T> responseClass) {
    try {
      return mapper.readValue(asString(), responseClass);
    } catch (JsonProcessingException e) {
      log.debug("Unexpected error while connecting server.", e);
      throw new GatewayException("Unexpected error while connecting server.");
    }
  }

  /**
   * Gets response as string.
   *
   * @return string response
   */
  public String asString() {
    java.net.http.HttpRequest target = request.build();
    try {
      HttpResponse<String> response = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() >= 500) {
        GatewayNotRespondingException ex = new GatewayNotRespondingException(
          format(
            "%s Gateway is not responding. Received HTTP status code [%s] for HTTP [%s]",
            apiName, response.statusCode(), target.method()
          ),
          response.statusCode(),
          response.body()
        );
        ex.setContext(Map.of("responseBody", response.body()));
        log.info(
          "[{}] Gateway is not responding. Received HTTP status code [{}] for HTTP [{}]",
          apiName, response.statusCode(), target.method()
        );
        throw ex;
      } else if (response.statusCode() >= 400) {
        GatewayNotAcceptingRequestException ex = new GatewayNotAcceptingRequestException(
          format(
            "%s Gateway is not accepting request. Received HTTP status code [%s] for HTTP [%s]",
            apiName, response.statusCode(), target.method()
          ),
          response.statusCode(),
          response.body()
        );
        ex.setContext(Map.of("responseBody", response.body()));
        log.info(
          "[{}] Gateway is not accepting request. Received HTTP status code [{}] for HTTP [{}]",
          apiName, response.statusCode(), target.method()
        );
        throw ex;
      }

      return response.body();
    } catch (IOException | InterruptedException e) {
      log.debug("Unexpected error while connecting server.", e);
      throw new GatewayException("Unexpected error while connecting server.");
    }
  }

  /**
   * Creates request builder.
   *
   * @return builder
   */
  private static java.net.http.HttpRequest.Builder create() {
    return java.net.http.HttpRequest.newBuilder()
      .header("User-Agent", "Java 11 Client bot");
  }

  /**
   * Gets query string.
   *
   * @param params params
   * @return query string
   */
  private static String toQueryParams(Map<String, String> params) {
    StringBuilder urlBuilder = new StringBuilder();
    if (null != params && !params.isEmpty()) {
      Iterator<String> iterator = params.keySet().stream().iterator();
      while (iterator.hasNext()) {
        String query = iterator.next();
        urlBuilder.append(query).append("=").append(params.get(query)).append("&");
      }
    }
    return urlBuilder.toString();
  }
}
