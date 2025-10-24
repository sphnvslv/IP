package Lab3;
import java.util.zip.*;
import java.io.*;
import java.util.jar.*;

public class ArchiveProcessor {

    public static void createZipArchive(String sourceFile, String zipFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {

            ZipEntry zipEntry = new ZipEntry(new File(sourceFile).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
        }
    }

    public static void createJarArchive(String sourceFile, String jarFile) throws IOException {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFile));
             FileInputStream fis = new FileInputStream(sourceFile)) {

            JarEntry jarEntry = new JarEntry(new File(sourceFile).getName());
            jos.putNextEntry(jarEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                jos.write(buffer, 0, length);
            }
            jos.closeEntry();
        }
    }
}