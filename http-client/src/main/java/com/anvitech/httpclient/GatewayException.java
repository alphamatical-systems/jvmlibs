package com.anvitech.httpclient;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Parent of all gateway exceptions.
 *
 * @author Dinesh Kumar
 * @since Apr 11, 2021
 */
public class GatewayException extends RuntimeException {
  private Map<String, Object> context = Collections.emptyMap();

  private Integer statusCode;

  private String errorDescription;

  /**
   * Creates a new gateway exception with no message.
   */
  public GatewayException() {
  }

  /**
   * Creates a fully initialized instance.
   *
   * @param message short description of the exception
   */
  public GatewayException(String message) {
    super(message);
  }

  /**
   * Creates a fully initialized instance.
   *
   * @param message short description of the exception
   * @param statusCode the HTTP error code status
   */
  public GatewayException(String message, Integer statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  /**
   * Creates a fully initialized instance.
   *
   * @param message short description of the exception
   * @param statusCode the HTTP error code status
   * @param errorDescription the HTTP error description
   */
  public GatewayException(String message, Integer statusCode, String errorDescription) {
    super(message);
    this.statusCode = statusCode;
    this.errorDescription = errorDescription;
  }

  /**
   * Returns the context
   *
   * @return context
   */
  public Map<String, Object> getContext() {
    return context;
  }

  /**
   * Sets the context
   *
   * @param context context
   */
  public void setContext(Map<String, Object> context) {
    this.context = context;
  }

  /**
   * Returns the error code status.
   *
   * @return the error code status
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * Returns the error description.
   *
   * @return the error description
   */
  public String getErrorDescription() {
    return errorDescription;
  }

  /**
   * Returns the error code status.
   *
   * @return the error code status
   */
  public String statusCode() {
    return (statusCode != null) ? String.valueOf(statusCode) : "";
  }

  /**
   * Returns the error description.
   *
   * @return the error description
   */
  public String errorDescription() {
    return Objects.nonNull(errorDescription) ? errorDescription.trim() : "";
  }
}
