package wniemiec.io.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class JarFileManagerTest {

    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private static final Path MOCK_JAR;
    private static final Path TEMP_DIR;
    private static JarFileManager jarManager;


    //-------------------------------------------------------------------------
    //		Initialization block
    //-------------------------------------------------------------------------
    static {
        Path resources = Path.of("src", "test", "resources").toAbsolutePath();
        
        MOCK_JAR = resources.resolve("string-utils.jar");
        TEMP_DIR = Path.of(System.getProperty("java.io.tmpdir"));
    }


    //-------------------------------------------------------------------------
    //		Test hooks
    //-------------------------------------------------------------------------
    @BeforeAll
    static void setUpJarManager() {
        jarManager = new JarFileManager(MOCK_JAR);
    }


    //-------------------------------------------------------------------------
    //		Tests
    //-------------------------------------------------------------------------
    @Test
    void testIsJarFilePositive() {
        Assertions.assertTrue(JarFileManager.isJarFile(MOCK_JAR));
    }

    @Test
    void testIsJarFileNegative() {
        Path currentDirectory = Path.of(".");

        Assertions.assertFalse(JarFileManager.isJarFile(currentDirectory));
    }

    @Test
    void testIsJarFileNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            JarFileManager.isJarFile(null);
        });
    }

    @Test
    void testExtractToNull() throws IOException {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            jarManager.extractTo(null);
        });
    }

    @Test
    void testExtractToSomeDirectory() throws IOException {
        Path output = jarManager.extractTo(TEMP_DIR);

        assertPathExists(output, "META-INF");
        assertPathExists(output, "META-INF", "MANIFEST.MF");
        assertPathExists(output, "wniemiec", "util", "java", "StringUtils.class");
    }

    private void assertPathExists(Path base, String... paths) {
        Path finalPath = base;

        for (int i = 0; i < paths.length; i++) {
            finalPath = finalPath.resolve(paths[i]);
        }

        Assertions.assertTrue(Files.exists(finalPath));
    }
}
