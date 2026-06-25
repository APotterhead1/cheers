  cheers
  =======
  
  A small custom Java serialization/deserialization library that:
  
  - Produces a compact textual representation containing a version header and a set of named objects/arrays.
  - Reconstructs object graphs including shared references and cycles (uses identity tracking).
  - Supports upgrading older serialized data to a newer in-code structure via a versioning mechanism (`Version` + `Modification`).
  
  ## Table of Contents
  
  - [Installation](#installation)
  - [JVM --add-opens Flags](#why-you-may-need-jvm---add-opens-flags)
  - [Public API Overview](#public-api-overview)
  - [Basic Usage (serialize / deserialize)](#basic-usage-serialize--deserialize)
  - [Versioning](#versioning-version-and-modification)
  - [Error Handling & Debugging](#error-handling--debugging)
  - [Notes, Known Limitations, and Tips](#notes-known-limitations-and-tips)
  - [Contributing](#contributing)
  - [License](#license)
  
  ## Installation
  
  ### Option 1: Maven (GitHub Packages)
  
  Add the GitHub Packages repository to your `pom.xml`:
  
  ```xml
  <repositories>
      <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/APotterhead1/cheers</url>
          <snapshots>
              <enabled>true</enabled>
          </snapshots>
      </repository>
  </repositories>
  ```
  
  Add the dependency:
  
  ```xml
  <dependency>
      <groupId>me.apotterhead</groupId>
      <artifactId>cheers</artifactId>
      <version>1.0.0</version>
  </dependency>
  ```
  
  To authenticate with GitHub Packages, create or update your `~/.m2/settings.xml`:
  
  ```xml
  <server>
      <id>github</id>
      <username>YOUR_GITHUB_USERNAME</username>
      <password>YOUR_GITHUB_TOKEN</password>
  </server>
  ```
  
  Generate a Personal Access Token on GitHub with `read:packages` scope: https://github.com/settings/tokens
  
  ### Option 2: Gradle (GitHub Packages)
  
  Add the GitHub Packages repository to your `build.gradle` or `build.gradle.kts`:
  
  **Gradle (Kotlin DSL):**
  ```kotlin
  repositories {
      maven {
          url = uri("https://maven.pkg.github.com/APotterhead1/cheers")
          credentials {
              username = System.getenv("GITHUB_USER") ?: "YOUR_GITHUB_USERNAME"
              password = System.getenv("GITHUB_TOKEN") ?: "YOUR_GITHUB_TOKEN"
          }
      }
  }
  
  dependencies {
      implementation("me.apotterhead:cheers:1.0.0")
  }
  ```
  
  **Gradle (Groovy DSL):**
  ```groovy
  repositories {
      maven {
          url "https://maven.pkg.github.com/APotterhead1/cheers"
          credentials {
              username = System.getenv("GITHUB_USER") ?: "YOUR_GITHUB_USERNAME"
              password = System.getenv("GITHUB_TOKEN") ?: "YOUR_GITHUB_TOKEN"
          }
      }
  }
  
  dependencies {
      implementation 'me.apotterhead:cheers:1.0.0'
  }
  ```
  
  ### Option 3: Direct JAR Download

  **For Maven:**
  
  Download the JAR file directly from the [GitHub Releases](https://github.com/APotterhead1/cheers/releases) page and add it to your project's classpath or build configuration.

  In pom.xml:
  ```xml
  <dependency>
      <groupId>me.apotterhead</groupId>
      <artifactId>cheers</artifactId>
      <version>1.0.0</version>
      <scope>system</scope>
      <systemPath>${project.basedir}/libs/cheers-1.0.0.jar</systemPath>
  </dependency>
  ```

  **For Gradle:**

Download the JAR file directly from the [GitHub Releases](https://github.com/APotterhead1/cheers/releases) page and add it to your project's classpath or build configuration.

  In build.gradle.kts (Kotlin DSL):
  ```kotlin
  dependencies {
      implementation(files("libs/cheers-1.0.0.jar"))
  }
  ```
or in build.gradle (Groovy DSL):
  ```groovy
  dependencies {
      implementation files('libs/cheers-1.0.0.jar')
  }
  ```

  **For IntelliJ IDEA:**
  1. Download the JAR file from the [GitHub Releases](https://github.com/APotterhead1/cheers/releases) page
  2. In your project, open **File → Project Structure**
  3. Navigate to **Libraries** (left sidebar)
  4. Click the **+** button and select **Java**
  5. Browse and select the `cheers-1.0.0.jar` file
  6. Select the modules where you want to add this library
  7. Click **Apply** and then **OK**

  Alternatively, you can add it to a specific module's dependencies:
  1. Open **File → Project Structure → Modules**
  2. Select your module and go to the **Dependencies** tab
  3. Click the **+** button, select **JARs or directories**
  4. Navigate to and select the `cheers-1.0.0.jar` file
  5. Click **Apply** and then **OK**

  **For Eclipse:**
  1. Download the JAR file from the [GitHub Releases](https://github.com/APotterhead1/cheers/releases) page
  2. In your project, create a `lib` folder if you don't have one (right-click project → **New → Folder**)
  3. Copy the `cheers-1.0.0.jar` into the `lib` folder
  4. Right-click the JAR file and select **Build Path → Add to Build Path**

  Alternatively, if the above doesn't work:
  1. Right-click your project and select **Properties**
  2. Navigate to **Java Build Path**
  3. Go to the **Libraries** tab
  4. Click **Add External JARs** and select the `cheers-1.0.0.jar` file
  5. Click **Apply and Close**

## Why you may need JVM --add-opens flags
  
  The library uses reflection extensively to access private fields and to construct objects. On Java 9+ the module system restricts reflective access to some internal `java.base` packages. To allow full reflective behavior during tests / execution, the following `--add-opens` flags are used in `build.gradle.kts` for the test task.

JVM flags used in `build.gradle.kts` (example)
```kotlin
tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf(
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.io=ALL-UNNAMED",
        "--add-opens=java.base/java.nio=ALL-UNNAMED",
        "--add-opens=java.base/java.util.regex=ALL-UNNAMED",
        "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
        "--add-opens=java.base/java.nio.charset=ALL-UNNAMED"
    )
}
```

Add the same flags in other contexts:

- Running the jar directly (after `./gradlew build`):
```bash
java \
  --add-opens=java.base/java.util=ALL-UNNAMED \
  --add-opens=java.base/java.lang=ALL-UNNAMED \
  --add-opens=java.base/java.io=ALL-UNNAMED \
  --add-opens=java.base/java.nio=ALL-UNNAMED \
  --add-opens=java.base/java.util.regex=ALL-UNNAMED \
  --add-opens=java.base/sun.nio.cs=ALL-UNNAMED \
  --add-opens=java.base/java.nio.charset=ALL-UNNAMED \
  -jar build/libs/<your-artifact>.jar
```

- IntelliJ IDEA:
   - Run → Edit Configurations → Your configuration → VM options
   - Paste the same `--add-opens=...` flags (space-separated).

## Public API Overview

Main classes you will use:
- `Serializer` — static method `serialize(Object object, Version version)` returns a String representation.
- `Deserializer` — static method `deserialize(String input, Version version)` returns `Object` (cast to expected type).
- `Version` — implement to provide a current version string and a sequence of `Modification`s that upgrade older serialized data.
- `Modification` — represents a change to the serialized `List<SerialObject>` before instantiation.

## Basic Usage (serialize / deserialize)

```java
// Serialize
String serialized = Serializer.serialize(myObj, myVersion);

// Deserialize (cast to expected type)
MyType obj = (MyType) Deserializer.deserialize(serialized, myVersion);
```

## Versioning: Version and Modification

Purpose:
- Serialized data starts with a version string (e.g. `"1.1"`). When you deserialize, the `Version` implementation is asked to supply modifications that upgrade older serialized data to the current in-code shape before object construction runs. Modifications operate on the serialized representation (`List<SerialObject>`), so upgrades can rename/add/remove variables, insert new `SerialObject`s, or change values.

`Version` interface:
```java
public interface Version {
    String getCurrentVersion();
    Modification[] getModifications(String originalVersion);

    static RuntimeException versionError(String version) {
        return new InvalidDeserializationInputException("Version \"" + version + "\" is not supported.");
    }
}
```

`Modification` interface (apply changes on serialized graph):
```java
public interface Modification {
    String getPath();
    void apply(List<SerialObject> serialObjects);

    // helper to locate a serial object by dotted path (root. ... )
    static SerialObject getSerialObjectFromPath(List<SerialObject> serialObjects, String path) {
        // provided by the library
    }
}
```

Concrete `Version` example (upgrading 1.0 → 1.1 by adding a boolean `bool` in `root.obj`)
```java
public class MyVersion implements Version {
    public static final MyVersion VERSION = new MyVersion();

    @Override
    public String getCurrentVersion() {
        return "1.1";
    }

    @Override
    public Modification[] getModifications(String version) {
        List<Modification> modifications = new ArrayList<>();
        switch (version) {
            case "1.0":
                // Add modification for 1.0 -> 1.1 upgrade
                modifications.add(new Modification() {
                    @Override
                    public String getPath() {
                        return "root.obj";
                    }
                    @Override
                    public void apply(List<SerialObject> serialObjects) {
                        SerialObject obj = Modification.getSerialObjectFromPath(serialObjects, getPath());
                        // add new boolean variable defaulting to false
                        obj.addVariable(new SerialPrimitive("bool", false));
                    }
                });
                // fall through to return modifications
            case "1.1":
                return modifications.toArray(Modification[]::new);
            default:
                throw Version.versionError(version);
        }
    }
}
```

Points about `Modification.getPath(...)`
- Path format: dot-separated parts. The first element must be `root`. Example: `root.obj.child` or `root.arr.0` (use numeric parts for array indices — arrays are serialized as variables named "0", "1", ...).
- If a path can't be resolved the helper throws `InvalidDeserializationInputException`. Write defensive `Modification` code and test it against real serialized strings.

Best practices when writing `Modification`s
- Use switch statement fall-through to chain modifications across versions. When upgrading from an older version, apply all necessary modifications and then fall through to return the array once you reach the current version.
- Keep modification logic small and limited to structural changes (adding/removing variables, renaming variables, inserting new `SerialObject`s).
- Ensure UUID uniqueness when adding new `SerialObject` instances into the `List<SerialObject>` (use e.g. `UUID.randomUUID().toString()`).
- Test modifications with sample serialized data representing older versions to verify safe upgrade.

## Error Handling & Debugging

- Parsing and structural failures throw `InvalidDeserializationInputException` with a message indicating why parsing or matching failed.
- If `getModifications(...)` does not support the serialized version, throw `Version.versionError(version)` to signal an unsupported version.

## Notes, Known Limitations, and Tips

- Identity tracking: `ObjectMap` uses identity (`==`) to identify already-serialized instances so shared references and cycles are preserved. That is intentional; do not expect `.equals()` merging.
- Records: deserialization supports Java records. The deserializer finds the record constructor and invokes it with deserialized components.
- Null handling:
  - `SerialPrimitive` encodes null as type `NULL`. `Serializer.serialize(null, version)` currently returns an empty string.
- Escapes: `SerialPrimitive` encodes typical escapes (\n, \t, \", \\\r, \b, \f).

## Contributing

- Open an issue for bugs or feature requests.

## License

This project is licensed under the MIT License. See `LICENSE` file for details.

Third-party dependencies are licensed under their respective licenses:
- **Objenesis** (3.5) is licensed under the Apache License 2.0. See `LICENSES/Apache-2.0.txt` for details.

For attribution and comprehensive licensing information, see the `NOTICE` file.

