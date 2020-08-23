package com.anvitech.support;

import com.anvitech.domain.event.Envelope;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.anvitech.util.MapUtils.nullToEmpty;
import static com.anvitech.util.SerializationUtils.fromJson;

/**
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public final class ContentFactory<T extends Envelope> {
  private static final Logger log = LoggerFactory.getLogger(ContentFactory.class);

  private final Class<T> clazz;
  private final String payload;

  public ContentFactory(Class<T> clazz, String payload) {
    this.clazz = clazz;
    this.payload = payload;
  }

  public T hydrate() {
    final Map<String, Object> members = new HashMap<>(nullToEmpty(fromJson(payload, Map.class)));
    try {
      final Constructor<T> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      final T instance = (T) constructor.newInstance();
      FieldUtils.getAllFieldsList(instance.getClass()).forEach(it -> {
        final String fieldName = it.getName();
        if (null != members.get(fieldName)) {
          if (!Modifier.isStatic(it.getModifiers())) {
            bind(instance, fieldName, members.get(fieldName));
          }
        }
        members.remove(fieldName);
      });
      if (!members.isEmpty()) {
        log.info("Provided type was a sub-type, we didn't set everything, but I guess that's OK :");
      }
      return instance;
    } catch (final Exception e) {
      log.error("Cannot build type based on input parameters due to:", e);
      throw new IllegalStateException(e);
    }
  }

  private void bind(final T instance, final String fieldName, final Object fieldValue) {
    try {
      final Field field = FieldUtils.getField(instance.getClass(), fieldName, true);
      Object converted = fieldValue;
      // ...special cases for deserialization
      if (fieldValue instanceof String) {
        if (field.getType().equals(ZonedDateTime.class)) {
          converted = ZonedDateTime.parse((CharSequence) fieldValue);
        } else if (field.getType().equals(LocalDate.class)) {
          converted = LocalDate.parse((CharSequence) fieldValue);
        } else if (field.getType().equals(LocalTime.class)) {
          converted = LocalTime.parse((CharSequence) fieldValue);
        } else if (Enum.class.isAssignableFrom(field.getType())) {
          converted = EnumUtils.getEnumIgnoreCase((Class<Enum>) field.getType(), (String) fieldValue);
        }
      }
      field.set(instance, converted);
    } catch (final Exception e) {
      throw new IllegalArgumentException(String.format("[%s] is either not accessible or not assignable", fieldName));
    }
  }
}
