package com.anvitech.domain.event;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Entity update event representation.
 *
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public abstract class EntityUpdated extends EntityEvent {
  private ZonedDateTime updatedAt;

  /**
   * Returns a fully initialized instance of this class.
   */
  protected EntityUpdated() {
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id entity identifier
   * @param updatedAt updated at
   */
  public EntityUpdated(String id, ZonedDateTime updatedAt) {
    super(id);
    this.updatedAt = Objects.nonNull(updatedAt) ? updatedAt : ZonedDateTime.now();
  }

  /**
   * Returns entity updated date time.
   *
   * @return entity created at
   */
  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }
}
