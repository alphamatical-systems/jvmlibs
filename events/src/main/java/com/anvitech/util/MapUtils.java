package com.anvitech.util;

import java.util.Collections;
import java.util.Map;

/**
 * @author Dinesh Kumar
 * @since Aug 23, 2020
 */
public final class MapUtils {

  /**
   * Null-safe check if the specified map is empty.
   * <p>
   * {@code null} returns {@code true}.
   *
   * @param map the map to check, may be {@code null}
   *
   * @return {@code true} if empty or {@code null}
   */
  public static boolean isEmpty(final Map<?, ?> map) {
    return (null == map) || map.isEmpty();
  }

  /**
   * Returns an empty {@code Map} if the argument is the {@code null} value; otherwise the same
   * argument's reference.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param map the map, possibly {@code null}
   *
   * @return an empty {@code Map} if the argument is the {@code null} value; otherwise the same
   *   argument's reference
   */
  public static <K, V> Map<K, V> nullToEmpty(final Map<K, V> map) {
    return isEmpty(map) ? Collections.emptyMap() : map;
  }
}
