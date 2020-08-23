package com.anvitech.util;

import com.anvitech.domain.event.EntityEvent;
import com.anvitech.domain.event.Message;

/**
 * Message utilities.
 *
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public class MessageUtils {

  /**
   * Converts an event to a message.
   *
   * @param event event
   * @return {@link Message}
   */
  private static Message toMessage(EntityEvent event) {
    return new Message(
      event.getId(),
      event.getEventType(),
      event.getSource(),
      event.getTrigger(),
      event.getGeneratedAt(),
      SerializationUtils.toJson(event)
    );
  }
}
