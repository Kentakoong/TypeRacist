# TypeRacist

A Java project built with Maven that utilizes JavaFX and other dependencies. This project follows a standard Maven structure and can be easily imported into Eclipse.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Importing the Maven Project into Eclipse](#importing-the-maven-project-into-eclipse)
- [Building the JAR File](#building-the-jar-file)
- [Running the JAR File](#running-the-jar-file)
- [Releases](#releases)
- [Why Use Maven?](#why-use-maven)
- [Troubleshooting](#troubleshooting)

## Prerequisites

- **Java Development Kit (JDK) 21 or later** installed  
- **Eclipse IDE** with Maven integration (preferably the latest version)  
- **Maven** (optional, since Eclipse has built-in support; Maven Wrapper is included)  
- **JavaFX SDK** (to be provided at runtime)

## Importing the Maven Project into Eclipse

1. **Open Eclipse**  
2. Go to **File > Import...**  
3. Select **Maven > Existing Maven Projects**, then click **Next**  
4. Click **Browse** and select the root directory of this project (where the `pom.xml` is located)  
5. Eclipse will automatically detect the `pom.xml` file. Ensure it's selected.  
6. Click **Finish**. Eclipse will import the project and resolve dependencies automatically.  
7. If dependencies are not resolved immediately, right-click the project and choose **Maven > Update Project...** and click **OK**.

## Building the JAR File

There are two ways to build the JAR file: via Eclipse or using the terminal.

### Via Eclipse (GUI)

1. Right-click on the project in the **Project Explorer**.  
2. Select **Run As > Maven build...**  
3. In the **Goals** field, type `clean package`.  
4. Click **Run**.  
5. The JAR file will be generated in the `target/` directory.

### Via Terminal (CLI)

1. Open a terminal in the project directory (where `pom.xml` is located).  
2. Run the following command:

       ./mvnw clean package

   (On Windows, use `mvnw.cmd clean package`.)

3. After the build completes, locate the JAR file in the `target/` directory. It will be named something like:

       TypeRacist-1.0-SNAPSHOT-shaded.jar

## Running the JAR File

If you exclude JavaFX dependencies from the JAR, provide the JavaFX SDK path at runtime. For example:

    java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.media -jar target/TypeRacist-1.0-SNAPSHOT-shaded.jar

Replace `/path/to/javafx-sdk/lib` with the actual path to your JavaFX SDK.

## Releases

We have official releases available in our [Releases Tab](https://github.com/2110215-ProgMeth/cedt-project-2024-2-typeracist/releases/tag/v1.0.0).  
Visit the link to download the latest version of TypeRacist.

## Why Use Maven?

- **Dependency Management:**  
  Maven automatically downloads and manages project dependencies from central repositories. Simply add your required libraries (e.g., JavaFX, JSON, JUnit) to the `pom.xml` file.

- **Standardized Project Structure:**  
  The standard directory layout (`src/main/java`, `src/test/java`, etc.) makes collaboration and project maintenance easier.

- **Build Automation:**  
  Compile, package, test, and deploy your project with simple Maven commands, such as:
  
       mvn clean install  
       mvn package

- **Central Repository Access:**  
  Maven downloads dependencies from repositories like Maven Central, ensuring you have access to the latest stable versions.

## Troubleshooting

- **Dependency Issues:**  
  If Eclipse does not automatically resolve dependencies, right-click the project and choose **Maven > Update Project...**. Make sure you have a stable internet connection.

- **Build Failures:**  
  If you encounter build failures, run:

       ./mvnw dependency:tree

  This will help identify any conflicting dependencies.

- **JavaFX Runtime Issues:**  
  If you see errors regarding missing JavaFX runtime components, verify that you are providing the correct module path to your JavaFX SDK.
