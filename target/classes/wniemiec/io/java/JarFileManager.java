/**
 * Copyright (c) William Niemiec.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package wniemiec.io.java;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.commons.io.FileUtils;


/**
 * Utility for managing JAR files.
 */
public class JarFileManager {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private final Path jar;
    private final String name;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    /**
     * JAR file manager.
     * 
     * @param       jar JAR file
     * 
     * @throws      IllegalArgumentException If any argument is null or if jar
     * is not a jar file
     */
    public JarFileManager(Path jar) {
        validateConstructorArgs(jar);

        name = extractJarName(jar);
        this.jar = setUpJar(jar, name);
    }
    

    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    private void validateConstructorArgs(Path jar) {
        if (jar == null) {
            throw new IllegalArgumentException("Jar file path cannot be null");
        }

        if (!isJarFile(jar)) {
            throw new IllegalArgumentException("Invalid jar file");
        }
    }

    /**
     * CHecks whether a path is a JAR file.
     * 
     * @param       path Path to be verified
     * 
     * @return      True if it is a JAR file; false otherwise
     * 
     * @throws      IllegalArgumentException If path is null
     */
    public static boolean isJarFile(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Jar file path cannot be null");
        }

        return path
            .getFileName()
            .toString()
            .matches(".+\\.jar(([^A-z0-9]+)|)");
    }

    private static String extractJarName(Path jarFile) {
        String filename = jarFile.getFileName().toString();
        
        return filename.substring(0, filename.lastIndexOf('.'));
    }

    private static Path setUpJar(Path jarFile, String jarName) {
        if (jarFile.getParent() == null) {
            return Path.of(jarName + ".jar");
        }

        return jarFile
            .toAbsolutePath()
            .getParent()
            .resolve(jarName + ".jar");
    }

    /**
     * Extracts JAR content to some location.
     * 
     * @param       output Path where JAR content will be exported
     * 
     * @return      Path where JAR content has exported (output/jar name)
     * 
     * @throws      IOException If JAR content cannot be exported
     * @throws      IllegalArgumentException If output is null
     */
    public Path extractTo(Path output) throws IOException {
        if (output == null) {
            throw new IllegalArgumentException("Output path cannot be null");
        }


        Path outputContentPath = buildOutputContentPath(output);
        
        try (JarFile jarFile = new JarFile(jar.toFile())) {
            setUpOutputContentPath(outputContentPath);
            parseJarEntries(outputContentPath, jarFile);
        }

        return outputContentPath;
    }

    private Path buildOutputContentPath(Path outputLocation) {
        return outputLocation.toAbsolutePath().resolve(name);
    }

    private void setUpOutputContentPath(Path outputContentPath) throws IOException {
        if (Files.exists(outputContentPath)) {
            FileUtils.deleteDirectory(outputContentPath.toFile());
        }

        Files.createDirectories(outputContentPath);
    }

    private void parseJarEntries(Path outputContentPath, JarFile jarFile) 
    throws IOException {
        Enumeration<JarEntry> entries = jarFile.entries();
        
        while (entries.hasMoreElements()) {
            parseJarEntry(outputContentPath, jarFile, entries.nextElement());
        }
    }

    private void parseJarEntry(Path outputContentPath, JarFile jarFile, JarEntry entry)
    throws IOException {
        Path entryOutput = buildEntryOutput(outputContentPath, entry);
   
        setUpEntryOutput(entryOutput);
        
        if (!entry.isDirectory()) {
            exportJarEntry(jarFile, entry, entryOutput);
        }
    }

    private Path buildEntryOutput(Path outputContentPath, JarEntry entry) {
        return outputContentPath.resolve(entry.getName());
    }

    private void setUpEntryOutput(Path entryOutputPath) throws IOException {
        if (!Files.exists(entryOutputPath)) {
            Files.createDirectories(entryOutputPath.getParent());
        }
    }

    private void exportJarEntry(JarFile jarFile, JarEntry entry, Path output)
    throws IOException {
        try (
            InputStream inputStream = jarFile.getInputStream(entry);
            FileOutputStream outputStream = new FileOutputStream(output.toFile())
        ) {
            while (inputStream.available() > 0) {
                outputStream.write(inputStream.read());
            }
        }
    }
}
