package com.anvitech.domain.event;

/**
 * Event representation.
 *
 * @author Dinesh Kumar
 * @since Dec 17, 2019
 */
public abstract class Event extends Message implements EntityEvent {

  private static final long serialVersionUID = -8988004773571748526L;

  private EventTrigger trigger;
  private String entityType;
  private String entityId;
  private String entityProperty;
  private String entityPropertyValue;

  /**
   * Default constructor.
   */
  protected Event() { }

  /**
   * Creates a fully initialized event without an entity identifier.
   *
   * @param trigger event trigger
   * @param entityType triggering entity type
   */
  public Event(EventTrigger trigger, String entityType) {
    this(trigger, entityType, null, null);
  }

  /**
   * Creates a fully initialized event.
   *
   * @param trigger event trigger
   * @param entityType triggering entity type
   * @param entityId triggering entity identifier
   */
  public Event(EventTrigger trigger, String entityType, String entityId) {
    this(trigger, entityType, entityId, null);
  }

  /**
   * Creates a fully initialized event with more specificity on the entity property involved.
   *
   * @param trigger event trigger
   * @param entityType triggering entity type
   * @param entityId triggering entity identifier
   * @param entityProperty triggering entity property
   */
  public Event(EventTrigger trigger, String entityType, String entityId, String entityProperty) {
    this(trigger, entityType, entityId, entityProperty, null);
  }

  /**
   * Creates a fully initialized event with more specificity on the entity property involved.
   *
   * @param trigger event trigger
   * @param entityType triggering entity type
   * @param entityId triggering entity identifier
   * @param entityProperty triggering entity property
   * @param entityPropertyValue triggering entity property value
   */
  public Event(EventTrigger trigger, String entityType, String entityId,
               String entityProperty, String entityPropertyValue) {
    this.entityType = entityType;
    this.entityId = entityId;
    this.entityProperty = entityProperty;
    this.entityPropertyValue = entityPropertyValue;
    this.trigger = trigger;
  }

  @Override
  public EventTrigger getTrigger() {
    return trigger;
  }

  @Override
  public String getEntityType() {
    return entityType;
  }

  @Override
  public String getEntityId() {
    return entityId;
  }

  @Override
  public String getEntityProperty() {
    return entityProperty;
  }

  @Override
  public String getEntityPropertyValue() {
    return entityPropertyValue;
  }

  @Override
  public String toString() {
    return super.toString() + " >> Event{" +
      ", trigger=" + trigger +
      ", entityType='" + entityType + '\'' +
      ", entityId='" + entityId + '\'' +
      ", entityProperty='" + entityProperty + '\'' +
      ", entityPropertyValue='" + entityPropertyValue + '\'' +
      '}';
  }
}
