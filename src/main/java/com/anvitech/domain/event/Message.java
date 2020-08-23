package com.anvitech.domain.event;

import java.time.ZonedDateTime;

/**
 * Message representation.
 *
 * @author Dinesh Kumar
 * @since Aug 17, 2020
 */
public final class Message {
  // Header fields
  private String id;
  private String eventType;
  private String source;
  private EventTrigger trigger;
  private ZonedDateTime generatedAt;

  // Event detail
  private String payload;

  /**
   * No-args constructor.
   */
  protected Message() {
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   * @param eventType event type
   * @param source source
   * @param trigger trigger
   * @param generatedAt generated at timestamp
   * @param payload payload
   */
  public Message(String id, String eventType, String source, EventTrigger trigger,
                 ZonedDateTime generatedAt, String payload) {
    this.id = id;
    this.eventType = eventType;
    this.source = source;
    this.trigger = trigger;
    this.generatedAt = generatedAt;
    this.payload = payload;
  }

  /**
   * Gets identifier.
   *
   * @return ide
   */
  public String getId() {
    return id;
  }

  /**
   * Gets event type.
   *
   * @return eventType
   */
  public String getEventType() {
    return eventType;
  }

  /**
   * Gets source of event.
   *
   * @return source
   */
  public String getSource() {
    return source;
  }

  /**
   * Gets action that triggered the event.
   *
   * @return trigger
   */
  public EventTrigger getTrigger() {
    return trigger;
  }

  /**
   * Gets timestamp when event is generated.
   *
   * @return generated at
   */
  public ZonedDateTime getGeneratedAt() {
    return generatedAt;
  }

  /**
   * Gets event paylaod.
   *
   * @return paylaod
   */
  public String getPayload() {
    return payload;
  }
}
