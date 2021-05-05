package com.anvitech.domain.handler;

import com.anvitech.domain.event.Message;

/**
 * Method invocation exception representation.
 *
 * @author Dinesh Kumar
 * @since Aug 18, 2020
 */
public class EventHandlerMethodInvokeException extends RuntimeException {
  private static final long serialVersionUID = -186794008822618935L;

  private final EventHandlerMethod eventHandlerMethod;
  private final Message originalMessage;

  /**
   * Fully initialized instance.
   *
   * @param eventHandlerMethod the event handler method that threw the exception
   * @param e the original exception
   */
  public EventHandlerMethodInvokeException(EventHandlerMethod eventHandlerMethod, Throwable e) {
    this(eventHandlerMethod, e, null);
  }

  /**
   * Fully initialized instance.
   *
   * @param eventHandlerMethod the event handler method that threw the exception
   * @param e the original exception
   * @param originalMessage the original message if available
   */
  public EventHandlerMethodInvokeException(EventHandlerMethod eventHandlerMethod, Throwable e,
                                           Message originalMessage) {
    super(e);
    this.eventHandlerMethod = eventHandlerMethod;
    this.originalMessage = originalMessage;
  }

  /**
   * Gets the event handler method that caused an exception.
   *
   * @return the event handler method
   */
  public EventHandlerMethod getEventHandlerMethod() {
    return eventHandlerMethod;
  }
}
