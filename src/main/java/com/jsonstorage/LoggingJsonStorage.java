/* [LoggingJsonStorage.java]
 * 
 * MIT License
 *
 * Copyright (c) 2022 Matt E
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jsonstorage;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.slf4j.Logger;

/**
 * Extension of {@link JsonStorage} to add helper logging using an <a href="https://www.slf4j.org/">SLF4J</a> Logger.
 */
public class LoggingJsonStorage extends JsonStorage {

  /**
   * Writes an object to JSON persistent file storage.
   * 
   * @param file Path of file to save into
   * @param obj The object to converted to JSON
   * @param formatted True to produce formatted JSON, false otherwise
   * @param logger {@link org.slf4j.Logger} instance to use for logging errors. Set as {@code null} to not log errors.
   * @throws IOException If there's a problem creating or writing to the file
   */
  public static void write(String file, Object obj, boolean formatted, Logger logger) {

    try {

      write(file, obj, formatted);

    } catch (Exception e) {

      if (logger != null) {

        logger.error(
          e.getClass().getSimpleName() + " when writing " + obj.getClass().getSimpleName() + " : " + e.getMessage(), e);
      }
    }
  }

  /**
   * Loads a saved object from file.
   * 
   * @param file File path of JSON file to load
   * @param objClass The class of the object to populate from the JSON data
   * @param logger {@link org.slf4j.Logger} instance to use for logging errors. Set as {@code null} to not log errors.
   * @return New configuration instance containing the loaded settings. {@code null} if the file doesn't exist or
   *         loading failed.
   */
  public static <ObjType> ObjType read(String file, Class<ObjType> objClass, Logger logger) {

    try {

      return read(file, objClass);

    } catch (NoSuchFileException e) {

      // Do nothing

    } catch (Exception e) {

      if (logger != null) {
        logger.error(
          e.getClass().getSimpleName() + " when reading " + objClass.getSimpleName() + " : " + e.getMessage(), e);
      }
    }

    return null;
  }
}
