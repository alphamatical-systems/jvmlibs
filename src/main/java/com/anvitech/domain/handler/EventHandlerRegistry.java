package com.anvitech.domain.handler;

import com.anvitech.domain.event.Message;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;

/**
 * Event handler registry.
 *
 * @author Dinesh Kumar
 * @since Aug 18, 2020
 */
public class EventHandlerRegistry {
  private final Multimap<String, EventHandlerMethod> eventHandlers = ArrayListMultimap.create();

  /**
   * Registers event handler.
   *
   * @param eventHandler event handler
   */
  public void registerEventHandler(Object eventHandler) {
    stream(eventHandler.getClass().getDeclaredMethods())
      .filter(this::isHandlerMethod)
      .forEach(m -> {
        Class<Message> payloadType = (Class<Message>) m.getParameters()[0].getType();
        EventHandlerMethod eventHandlerMethod = createEventHandlerMethod(m, eventHandler, payloadType);
        eventHandlers.put(eventHandlerMethod.getSimpleName(), eventHandlerMethod);
      });
  }

  /**
   * Returns a collection of event handlers that can process the given event type.
   *
   * @param eventType the simple name of the event
   * @return Collection of event handlers or null if none found
   */
  public Collection<EventHandlerMethod> getEventHandlers(String eventType) {
    return eventHandlers.get(eventType) != null ? eventHandlers.get(eventType) : new ArrayList<>();
  }

  /**
   * Creates an event handler method.
   *
   * @param m the actual method to invoke
   * @param eventHandler the object this method belongs to
   * @param payloadType the Message payload type
   * @return a new instance of EventHandlerMethod
   */
  protected EventHandlerMethod createEventHandlerMethod(Method m, Object eventHandler,
                                                        Class<? extends Message> payloadType) {
    return new EventHandlerMethod(m, eventHandler, payloadType);
  }

  /**
   * Checks if given method is a handler method.
   * An event handler method must be annotated with EventHandler and must only have one param
   * and must be accessible.
   *
   * @param m the method to check
   * @return true if method is handler else false
   */
  private boolean isHandlerMethod(Method m) {
    return m.isAnnotationPresent(EventHandler.class) &&
      (Modifier.isPublic(m.getModifiers()) || Modifier.isProtected(m.getModifiers())) &&
      m.getParameters().length == 1;
  }
}
