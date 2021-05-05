package com.anvitech.httpclient;

/**
 * A gateway to the outside world is down or experiencing technical issues that prevent it from
 * functioning normally.
 *
 * @author Dinesh Kumar
 * @since Apr 11, 2021
 */
public class GatewayNotRespondingException extends GatewayException {

  /**
   * Creates a new exception with no message.
   */
  public GatewayNotRespondingException() {
  }

  /**
   * Creates a new exception with a message.
   *
   * @param message short description of the exception
   */
  public GatewayNotRespondingException(String message) {
    super(message);
  }

  /**
   * Creates a new gateway exception with a message.
   *
   * @param message short description of the exception
   * @param errorStatus the HTTP error status
   * @param errorDescription the HTTP error description
   */
  public GatewayNotRespondingException(String message, Integer errorStatus, String errorDescription) {
    super(message, errorStatus, errorDescription);
  }
}
