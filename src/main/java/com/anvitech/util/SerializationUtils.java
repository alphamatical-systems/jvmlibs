package com.anvitech.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public class SerializationUtils {

  /**
   * XXX: Encapsulating the mapper here since we only support the following types:
   *
   * <ul>
   * <li>string</li>
   * <li>integer</li>
   * <li>long</li>
   * <li>double</li>
   * <li>ZonedDateTime "yyyy-MM-dd'T'hh:mm:ssZ"</li>
   * <li>LocalDate "yyyy-MM-dd"</li>
   * </ul>
   * <p>
   * If this changes then this should be extracted so it can be configured per service.
   */
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .registerModule(new JavaTimeModule());

  /**
   * Deserializes a JSON object into a given class.
   *
   * @param json the JSON object
   * @param instanceClass the class
   *
   * @return an instance of the given class
   */
  public static <T> T fromJson(final String json, final Class<T> instanceClass) {
    try {
      return OBJECT_MAPPER.readValue(json, instanceClass);
    } catch (final IOException e) {
      throw new SerializationException("Failed to deserialize object due to:", e);
    }
  }

  /**
   * Serializes an object into JSON.
   *
   * @param obj the object
   *
   * @return the serialized JSON
   */
  public static String toJson(final Object obj) {
    try {
      return OBJECT_MAPPER.writeValueAsString(obj);
    } catch (final JsonProcessingException e) {
      throw new SerializationException("Failed to serialize object due to:", e);
    }
  }
}
