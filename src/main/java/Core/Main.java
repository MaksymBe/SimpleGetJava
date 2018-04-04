package Core;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;

//@SpringBootApplication
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) throws Exception {
        String webAppDirLocation = "src/main/";
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(3000);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webAppDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
        //SpringApplication.run(Main.class, args);
    }
}
