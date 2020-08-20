package com.anvitech.domain.handler;

import com.anvitech.domain.event.Message;

import java.lang.reflect.Method;

/**
 * Represents a event handler method.
 *
 * @author Dinesh Kumar
 * @since Aug 18, 2020
 */
public class EventHandlerMethod {

  private final Method method;
  private final Object bean;
  private final Class<? extends Message> payloadType;

  /**
   * Creates a new event holder method with the given info.
   *
   * @param bean the spring managed bean containing the method to be invoked
   * @param method the actual method
   * @param payloadType the payload type
   */
  public EventHandlerMethod(Method method, Object bean, Class<? extends Message> payloadType) {
    this.method = method;
    this.bean = bean;
    this.payloadType = payloadType;
  }

  /**
   * Invokes the actual method via reflection.
   *
   * @param message the Message to process
   * @return Object the return of invoked method
   */
  public Object invokeMethod(Message message) {
    try {
      return method.invoke(bean, message);
    } catch (Exception e) {
      throw new EventHandlerMethodInvokeException(this, e.getCause(), message);
    }
  }

  /**
   * The simple name for the payload type.
   *
   * @return String representing the simple class name
   */
  public String getSimpleName() {
    return payloadType.getSimpleName();
  }
}
