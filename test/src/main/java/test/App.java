package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {



    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Hello World");

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
