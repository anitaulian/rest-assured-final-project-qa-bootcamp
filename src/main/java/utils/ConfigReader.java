package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            // path relatif agar bisa jalan dari root project
            FileInputStream fis = new FileInputStream("src/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Gagal memuat file config.properties di: src/resources/config.properties", e);
        }
    }

    // method untuk ambil nilai property berdasarkan key
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    // method tambahan opsional untuk debug
    public static void printAllProperties() {
        properties.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}
