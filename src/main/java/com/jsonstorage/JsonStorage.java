/* [JsonStorage.java]
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Utility class for persisting plain old Java objects (POJO) to UTF-8 text file in JSON format and subsequently loading
 * the files into runtime objects.
 * <p>
 * Uses the <a href="https://github.com/google/gson">Gson</a> library for JSON serialization and deserialization.
 */
public class JsonStorage {

  /**
   * Saves an object to JSON file.
   * 
   * @param file Path of file to save into
   * @param obj The object to converted to JSON
   * @param formatted True to produce formatted JSON, false otherwise
   * @throws IOException If there's a problem creating or writing to the file
   */
  public static void write(String file, Object obj, boolean formatted) throws IOException {

    Gson gson = formatted ? new GsonBuilder().setPrettyPrinting().create() : new Gson();

    final String json = gson.toJson(obj);

    Files.write(Path.of(file), json.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Loads a saved object from file.
   * 
   * @param file File path of JSON file to load
   * @param objClass The class of the object to populate from the JSON data
   * @return New configuration instance containing the loaded settings
   * @throws IOException If there's a problem opening or reading the file
   * @throws SecurityException If an applicable problem occurs during object creation
   * @throws NoSuchMethodException If an applicable problem occurs during object creation
   * @throws InvocationTargetException If an applicable problem occurs during object creation
   * @throws IllegalArgumentException If an applicable problem occurs during object creation
   * @throws IllegalAccessException If an applicable problem occurs during object creation
   * @throws InstantiationException If an applicable problem occurs during object creation
   */
  public static <ObjType> ObjType read(String file, Class<ObjType> objClass)
    throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, NoSuchMethodException, SecurityException {

    final String json = new String(Files.readAllBytes(Path.of(file)), StandardCharsets.UTF_8);

    Gson gson = new Gson();

    ObjType obj = gson.fromJson(json, (Type) objClass);

    return Optional.ofNullable(obj).orElse(objClass.getDeclaredConstructor().newInstance());
  }
}
