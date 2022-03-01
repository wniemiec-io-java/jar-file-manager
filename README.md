![](https://github.com/wniemiec-io-java/jar-file-manager/blob/master/docs/img/logo/logo.jpg)

<h1 align='center'>Jar file manager</h1>
<p align='center'>Utility for managing JAR files.</p>
<p align="center">
    <a href="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/windows.yml"><img src="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/windows.yml/badge.svg" alt=""></a>
    <a href="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/macos.yml"><img src="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/macos.yml/badge.svg" alt=""></a>
    <a href="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/ubuntu.yml"><img src="https://github.com/wniemiec-io-java/jar-file-manager/actions/workflows/ubuntu.yml/badge.svg" alt=""></a>
    <a href="https://codecov.io/gh/wniemiec-io-java/jar-file-manager"><img src="https://codecov.io/gh/wniemiec-io-java/jar-file-manager/branch/master/graph/badge.svg?token=R2SFS4SP86" alt="Coverage status"></a>
    <a href="http://java.oracle.com"><img src="https://img.shields.io/badge/java-11+-D0008F.svg" alt="Java compatibility"></a>
    <a href="https://mvnrepository.com/artifact/io.github.wniemiec-io-java/jar-file-manager"><img src="https://img.shields.io/maven-central/v/io.github.wniemiec-io-java/jar-file-manager" alt="Maven Central release"></a>
    <a href="https://github.com/wniemiec-io-java/jar-file-manager/blob/master/LICENSE"><img src="https://img.shields.io/github/license/wniemiec-io-java/jar-file-manager" alt="License"></a>
</p>
<hr />

## ❇ Introduction
Jar file manager performs operations with [jar](https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jarGuide.html) files simply and easily.

## ❓ How to use

1. Add one of the options below to the pom.xml file: 

#### Using Maven Central:
```
<dependency>
  <groupId>io.github.wniemiec-io-java</groupId>
  <artifactId>jar-file-manager</artifactId>
  <version>LATEST</version>
</dependency>
```

2. Run
```
$ mvn install
```

3. Use it
```
[...]

import wniemiec.io.java.JarFileManager;

[...]

Path jarFile = Path.of("src", "test", "resources", "string-utils.jar");
Path outputDirectory = Path.of(System.getProperty("java.io.tmpdir"));

JarFileManager jarManager = new JarFileManager(jarFile);

Path outputExtraction = jarManager.extractTo(outputDirectory);

System.out.println(Files.exists(outputExtraction.resolve(Path.of("META-INF"))));
System.out.println(Files.exists(outputExtraction.resolve(Path.of("META-INF", "MANIFEST.MF"))));
System.out.println(Files.exists(outputExtraction.resolve(Path.of("wniemiec", "util", "java", "StringUtils.class"))));
```

## 📖 Documentation
|        Property        |Type|Description|Default|
|----------------|-------------------------------|-----------------------------|--------|
|isJarFile |`(path: Path): boolean`|Checks whether a path is a JAR file.| - |
|extractTo |`(output: Path): Path`|Extracts JAR content to some location.| - |


## 🚩 Changelog
Details about each version are documented in the [releases section](https://github.com/williamniemiec/wniemiec-io-java/jar-file-manager/releases).

## 🤝 Contribute!
See the documentation on how you can contribute to the project [here](https://github.com/wniemiec-io-java/jar-file-manager/blob/master/CONTRIBUTING.md).

## 📁 Files

### /
|        Name        |Type|Description|
|----------------|-------------------------------|-----------------------------|
|dist |`Directory`|Released versions|
|docs |`Directory`|Documentation files|
|src     |`Directory`| Source files|
