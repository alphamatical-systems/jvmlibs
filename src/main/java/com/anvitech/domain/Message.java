package com.anvitech.domain;

import com.anvitech.support.IdGenerator;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Message representation.
 *
 * @author Dinesh Kumar
 * @since Aug 17, 2020
 */
public class Message implements Serializable {
  private static final long serialVersionUID = 1694869349215109944L;
  private final String id;
  private final ZonedDateTime generatedAt;

  /**
   * No-args constructor.
   */
  protected Message() {
    id = IdGenerator.uuid();
    generatedAt = ZonedDateTime.now();
  }

  /**
   * Creates fully initialized instance.
   *
   * @param id identifier
   * @param generatedAt generated at timestamp
   */
  protected Message(String id, ZonedDateTime generatedAt) {
    this.id = id;
    this.generatedAt = generatedAt;
  }

  /**
   * Gets message identifier.
   *
   * @return id
   */
  public String getId() {
    return id;
  }

  /**
   * Gets generated at timestamp.
   *
   * @return generatedAt
   */
  public ZonedDateTime getGeneratedAt() {
    return generatedAt;
  }

  @Override
  public String toString() {
    return "Message{" +
      "id='" + id + '\'' +
      ", generatedAt=" + generatedAt +
      '}';
  }
}
