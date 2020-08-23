package com.anvitech.util;

/**
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public class SerializationException extends RuntimeException {

  public SerializationException(String s, Throwable e) {
    super(s, e);
  }
}
