package com.abc.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

@Component
//@Validated
@PropertySource("classpath:test.properties")
public class TestBean {

    @Value("${name}")
    private String name;

    /*@Value("${testdata}")
    private String testdata;*/

    @Valid
    private InetAddress remoteAddress;

    @Autowired
    Security sec;

    @Component
//    @PropertySource("classpath:/test.properties")
    public static class Security {

        @Autowired
        Environment env;
//        @NotEmpty
        @Value("${testdata}")
        public String testdata;

        @Autowired
        ResourceLoader rl;

        public String getUsername() {
            Properties p = new Properties();
            try {
                System.out.println(System.getProperty("java.class.path"));
//                Resource rc = rl.getResource("file:test.properties");
//                InputStream in = rc.getInputStream();
                InputStream in = ClassLoader.getSystemResourceAsStream("test.properties");
                if ( in == null) throw new IllegalArgumentException();
                p.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return env.getProperty("testdata");
        }
    }

    public String getName() {
        return name + " - " + sec.getUsername();
    }
}
