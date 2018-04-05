package SpringWebApp.Core;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.io.IOException;

//@SpringBootApplication
@EnableJpaAuditing
public class Main {

    private static final int DEFAULT_PORT = 3000;
    private final Tomcat tomcat;

    public static void main(String[] args) throws Exception {
        new Main(3000).run();
    }

    public Main() throws Exception {
        this(getPort());
    }

    public Main(int port) throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
        tomcat.addWebapp("", new File(System.getProperty("java.io.tmpdir")).getAbsolutePath());
    }

    /**
     * Start server and block, waiting for it to stop.
     */
    public void run() throws Exception {
        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * Start server and return when server has started.
     */
    public void start() throws Exception {
        tomcat.start();

    }

    public void stop() throws Exception {
        try {
            tomcat.stop();
        } finally {
            tomcat.destroy();
        }
    }

    private static int getPort() throws IOException {
        return System.getenv("PORT") == null ? DEFAULT_PORT : Integer.parseInt(System.getenv("PORT"));
    }

    /*public static void main(String[] args) throws Exception {
        String webAppDirLocation = "";
        Tomcat tomcat = new Tomcat();

        tomcat.setPort(3000);

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webAppDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
        //SpringApplication.run(Main.class, args);
    }*/
}
