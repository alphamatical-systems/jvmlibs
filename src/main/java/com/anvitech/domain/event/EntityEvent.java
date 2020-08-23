package com.anvitech.domain.event;

import com.anvitech.support.IdGenerator;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Represents an event related to a specific domain entity
 *
 * @author Dinesh Kumar
 * @since Aug 16, 2020
 */
public abstract class EntityEvent implements Envelope {
  private String id;
  private String eventType;
  private String source;
  private EventTrigger trigger;
  private ZonedDateTime generatedAt;

  /**
   * No-arg constructor.
   */
  protected EntityEvent() {
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   */
  public EntityEvent(String id) {
    this.id = nonNull(id) ? id : IdGenerator.uuid();
    eventType = getClass().getSimpleName();
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   * @param source source
   */
  public EntityEvent(String id, String source) {
    this(id, source, null);
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   * @param source source
   */
  public EntityEvent(String id, String source, EventTrigger trigger) {
    this(id, source, trigger, null);
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id id
   * @param source source
   * @param trigger trigger
   * @param generatedAt generated at
   */
  public EntityEvent(String id, String source, EventTrigger trigger, ZonedDateTime generatedAt) {
    this(id);
    this.source = source;
    this.trigger = trigger;
    this.generatedAt = nonNull(generatedAt) ? generatedAt : ZonedDateTime.now();
  }

  /**
   * Gets identifier.
   *
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * Gets generated at.
   *
   * @return generatedAt
   */
  public ZonedDateTime getGeneratedAt() {
    return generatedAt;
  }

  /**
   * Gets source.
   *
   * @return source
   */
  public String getSource() {
    return source;
  }

  /**
   * Gets trigger.
   *
   * @return trigger
   */
  public EventTrigger getTrigger() {
    return trigger;
  }

  /**
   * Gets event type.
   *
   * @return eventType
   */
  public String getEventType() {
    return eventType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EntityEvent that = (EntityEvent) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(generatedAt, that.generatedAt) &&
      Objects.equals(source, that.source) &&
      Objects.equals(eventType, that.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, generatedAt, source, eventType);
  }
}
