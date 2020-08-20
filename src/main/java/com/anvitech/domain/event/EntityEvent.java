package com.anvitech.domain.event;

/**
 * Represents an event related to a specific domain entity
 *
 * @author Dinesh Kumar
 * @since Aug 16, 2020
 */
public interface EntityEvent {

  /**
   * Returns the type of entity that triggered the event.
   *
   * @return the entity type
   */
  String getEntityType();

  /**
   * Returns the identifier of the entity that triggered the event.
   *
   * @return the entity identifier
   */
  String getEntityId();

  /**
   * Returns the entity property that triggered the event.
   *
   * @return the entity property
   */
  String getEntityProperty();

  /**
   * Returns the value of the entity property that triggered the event.
   *
   * @return the entity property value
   */
  String getEntityPropertyValue();

  /**
   * Returns the action that triggered the event.
   *
   * @return the entity trigger
   */
  EventTrigger getTrigger();
}
