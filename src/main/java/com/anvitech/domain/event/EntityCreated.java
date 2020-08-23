package com.anvitech.domain.event;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Entity create event representation.
 *
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public abstract class EntityCreated extends EntityEvent {
  private ZonedDateTime createdAt;

  /**
   * Returns a fully initialized instance of this class.
   */
  protected EntityCreated() {
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   * @param createdAt created at
   */
  public EntityCreated(String id, ZonedDateTime createdAt) {
    super(id);
    this.createdAt = Objects.nonNull(createdAt) ? createdAt : ZonedDateTime.now();
  }

  /**
   * Returns entity createdAt date time.
   *
   * @return entity created at
   */
  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }
}
