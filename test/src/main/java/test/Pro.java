package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Pro {
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            System.out.println(System.getProperty("java.class.path"));
            InputStream in = ClassLoader.getSystemResourceAsStream("test.properties");
            if (in == null) throw new IllegalArgumentException();
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
