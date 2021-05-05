package com.anvitech.httpclient;

/**
 * A gateway to the outside world thinks it is receiving a bad request. This likely occurs due to a
 * misalignment in API interfaces or failed authentication/authorization.
 *
 * @author Dinesh Kumar
 * @since Apr 11, 2021
 */
public class GatewayNotAcceptingRequestException extends GatewayException {
  /**
   * Creates a new exception with no message.
   */
  public GatewayNotAcceptingRequestException() {
  }

  /**
   * Creates a new gateway exception with a message.
   *
   * @param message short description of the exception
   * @param errorStatus the HTTP error status
   * @param errorDescription the HTTP error description
   */
  public GatewayNotAcceptingRequestException(String message, Integer errorStatus, String errorDescription) {
    super(message, errorStatus, errorDescription);
  }

  /**
   * Creates a fully initialized instance.
   *
   * @param message short description of the exception
   * @param statusCode the HTTP status code received from the server
   */
  public GatewayNotAcceptingRequestException(String message, Integer statusCode) {
    super(message, statusCode);
  }
}
