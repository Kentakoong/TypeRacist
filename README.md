# Importing a Maven Project into Eclipse

## Prerequisites

- **Eclipse IDE** (Ensure you have the latest version with Maven integration)
- **Java Development Kit (JDK)** installed
- **Maven** installed (Optional, Eclipse has built-in support for Maven)

## Steps to Import a Maven Project into Eclipse

1. **Open Eclipse**
2. **Go to File > Import...**
3. Select **Maven > Existing Maven Projects**, then click **Next**
4. Click **Browse** and select the root directory of your Maven project
5. Eclipse will automatically detect the `pom.xml` file. Make sure it's selected.
6. Click **Finish**. Eclipse will import the project and resolve dependencies automatically.
7. Wait for the dependencies to download.
   You can check progress internet the **Console** and **Maven Repositories** views.

## Resolving Dependencies (if needed)

If Eclipse does not automatically resolve dependencies, try:

- **Right-click on the project > Maven > Update Project...**,
  then select the project and click **OK**
- Ensure you have an active internet connection (to download dependencies)
- If needed, delete the `target/` folder and run `mvn clean install` from the terminal

## Why Use Maven?

### 1. **Dependency Management**

- Maven handles project dependencies automatically.
- For example, if your project requires **org.json** or **JavaFX**,
  you just need to add them to the `pom.xml` file,
  and Maven will download the required libraries.

### 2. **Standardized Project Structure**

- Follows a standard directory layout (`src/main/java`, `src/test/java`, etc.),
  making it easier for developers to collaborate.

### 3. **Build Automation**

- You can compile, package, and run tests with simple commands like:

  ```sh
  mvn clean install
  mvn package
  ```

### 4. **Central Repository for Dependencies**

- Maven downloads dependencies from repositories like Maven Central,
  ensuring you always use the latest stable versions.
