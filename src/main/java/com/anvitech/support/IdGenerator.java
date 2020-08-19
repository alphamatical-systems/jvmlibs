package com.anvitech.support;

import java.nio.ByteBuffer;
import java.util.UUID;

import static java.time.Instant.now;

/**
 * Generates a sequential UUID by combining a timestamp with a randomly generated (type 4) UUID.
 * This implementation is based on the following approach:
 * <a href="http://www.codeproject.com/Articles/388157/GUIDs-as-fast-primary-keys-under-multiple-database">GUIDs as
 * fast primary keys under multiple databases</a>.
 *
 * @author Dinesh Kumar
 * @since Dec 23, 2019
 */
public class IdGenerator {

  /**
   * Generates a sequential UUID.
   *
   * @return the {@code String}-generated UUID
   * +
   */
  public static String uuid() {
    return IdGenerator.generate().toString();
  }

  /**
   * Generates a sequential UUID.
   *
   * @return generated UUID
   */
  public static UUID generate() {
    return toUUID(replace(timestamp(), 2, build()));
  }

  /**
   * Generate (type 4) random UUID as a byte array.
   *
   * @return UUID as byte array
   */
  private static byte[] build() {
    UUID uuid = UUID.randomUUID();
    return ByteBuffer.allocate(16)
      .putLong(uuid.getMostSignificantBits())
      .putLong(uuid.getLeastSignificantBits())
      .array();
  }

  /**
   * Generate a timestamp for this instant as a byte array.
   *
   * @return current timestamp as byte array
   */
  private static byte[] timestamp() {
    return ByteBuffer.allocate(Long.BYTES).putLong(now().toEpochMilli()).array();
  }

  /**
   * Replaces bytes in the destination array by those in the source array, starting from the given
   * source array offset.
   *
   * @param src       source byte array
   * @param srcOffset source array offset
   * @param dest      destination byte array
   * @return updated destination byte array
   */
  private static byte[] replace(byte[] src, int srcOffset, byte[] dest) {
    if (srcOffset < src.length) {
      for (int i = srcOffset; i < src.length; i++) {
        if (i < dest.length) {
          dest[i - srcOffset] = src[i];
        }
      }
    }
    return dest;
  }

  /**
   * Converts the given byte array to a UUID. The byte array must be 16 bytes in length.
   *
   * @param bytes bytes to convert
   * @return resulting UUID
   */
  private static UUID toUUID(byte[] bytes) {
    long mostSignificantBytes = 0;
    long leastSignificantBytes = 0;
    assert bytes.length == 16 : "data must be 16 bytes in length";
    for (int i = 0; i < 8; i++)
      mostSignificantBytes = (mostSignificantBytes << 8) | (bytes[i] & 0xff);
    for (int i = 8; i < 16; i++)
      leastSignificantBytes = (leastSignificantBytes << 8) | (bytes[i] & 0xff);

    return new UUID(mostSignificantBytes, leastSignificantBytes);
  }
}