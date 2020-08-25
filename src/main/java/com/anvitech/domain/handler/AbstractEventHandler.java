package com.anvitech.domain.handler;

import com.anvitech.domain.event.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Abstract event handler for handlers of all types.
 *
 * @author Dinesh Kumar
 * @since Aug 18, 2020
 */
public abstract class AbstractEventHandler {
  private static final Logger log = LoggerFactory.getLogger(AbstractEventHandler.class);
  private static final EventHandlerRegistry registry;

  static {
    registry = new EventHandlerRegistry();
  }

  /**
   * Fully initialized instance.
   */
  protected AbstractEventHandler() {
    registerHandlers();
  }

  /**
   * Handles event from kafka stream.
   *
   * @param event event
   */
  public void handle(final Message event) {
    final String eventType = event.getEventType();
    final Collection<EventHandlerMethod> handlers = getRegistry().getEventHandlers(eventType);
    if (handlers.isEmpty()) {
      log.warn("No event handler found for event [{}]", eventType);
    } else {
      handlers.forEach(m -> {
        try {
          log.info("Method calling for [{}]", m.getSimpleName());
          m.invokeMethod(event);
        } catch (EventHandlerMethodInvokeException e) {
          log.error("Error occurred during call to event handler for type [{}]", eventType, e);
        }
      });
    }
  }

  /**
   * Allow registering of handlers to registry.
   * Sub classes can override if they need to register other handlers as well.
   */
  protected void registerHandlers() {
    getRegistry().registerEventHandler(this);
  }

  /**
   * Gets the event handler registry.
   *
   * @return event handler registry
   */
  protected EventHandlerRegistry getRegistry() {
    return registry;
  }
}
