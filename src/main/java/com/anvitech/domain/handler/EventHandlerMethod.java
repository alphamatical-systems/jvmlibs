package com.anvitech.domain.handler;

import com.anvitech.domain.event.Envelope;
import com.anvitech.domain.event.Message;
import com.anvitech.support.ContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Represents a event handler method.
 *
 * @author Dinesh Kumar
 * @since Aug 18, 2020
 */
public class EventHandlerMethod {
  private static final Logger log = LoggerFactory.getLogger(EventHandlerMethod.class);

  private final Method method;
  private final Object bean;
  private final Class<? extends Envelope> payloadType;

  /**
   * Creates a new event holder method with the given info.
   *
   * @param bean the spring managed bean containing the method to be invoked
   * @param method the actual method
   * @param payloadType the payload type
   */
  public EventHandlerMethod(Method method, Object bean, Class<? extends Envelope> payloadType) {
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
      return method.invoke(bean, getEnvelope(message));
    } catch (Exception e) {
      log.error("Error while calling method for [{}]", getSimpleName(), e);
      throw new EventHandlerMethodInvokeException(this, e.getCause(), message);
    }
  }

  private Envelope getEnvelope(Message message) {
    return new ContentFactory<>(getPayloadType(), message.getPayload()).hydrate();
  }

  public Class<? extends Envelope> getPayloadType() {
    return payloadType;
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
