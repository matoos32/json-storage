# json-storage
A tiny helper library for data-binding and persisting plain old java objects (POJO) to JSON file.

Accomplishes this by orchestrating the use of [Gson](https://github.com/google/gson) and optionally [SLF4J](https://www.slf4j.org/) for error logging.

## Installing

First get the repo

```
git clone <this repo>
```

Then compile and install the source into your local Maven cache

```
mvn install
```

## Incorporating into your project

Add this dependency to your Maven POM

```
  <dependency>
    <groupId>com.jsonstorage</groupId>
    <artifactId>json-storage</artifactId>
    <version>1.0.0</version>
  </dependency>
```

Or copy the source code into your project (you'll need to adjust the package names).

Import the desired classes afterwards:

```
import com.jsonstorage.JsonStorage;
```

```
// Alternative that adds boilerplate error logging using a provided org.slf4j.Logger
import com.jsonstorage.LoggingJsonStorage;
```

## Writing objects to JSON file

*Simple examples using a `java.awt.Point` object*

```
import com.jsonstorage.JsonStorage;
import java.awt.Point;

...

try {

  boolean formatted = true;

  JsonStorage.write("/tmp/my-object.json", new Point(1776, 294), formatted);

} catch (IOException e) {
  // Oh oh
}
```

This will write the point into the file `/tmp/my-object.json` as such:

```
{
  "x": 1776,
  "y": 294
}
```

If `formatted` was set to `false` the file contents would instead be:

```
{"x":1776,"y":294}
```

## Reading objects from JSON file

```
import com.jsonstorage.JsonStorage;
import java.awt.Point;

...

try {

  Point myObject = JsonStorage.read("/tmp/my-object.json", Point.class);

} catch (Exception e) {
  // Oh oh
  // See the read() method for all the different exception types
}
```

## Helper Logging

For convenience you can use the class `LoggingJsonStorage` which extends `JsonStorage` adding some boilerplate SLF4J logging of errors.

```
import com.jsonstorage.LoggingJsonStorage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Point;

...

private static final Logger log = LoggerFactory.getLogger(MyClass.class);


private void save() {

  boolean formatted = true;

  LoggingJsonStorage.write("/tmp/my-object.json", new Point(1776, 294), formatted, log);
}


private void load() {

  Point point = LoggingJsonStorage.read("/tmp/my-object.json", Point.class, log);

  if (point == null) {
    config = new Point();
  }
}
```