package tobyspring.helloboot;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @SpringBootApplication
public class HellobootApplication {
//    private final JdbcTemplate jdbcTemplate;
//
//    public HellobootApplication(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @PostConstruct
//    void init() {
//        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
//    }

    public static void main(String[] args) {
        // NonSpringBoot
        ServletWebServerFactory serverFactory =  new TomcatServletWebServerFactory();
        // WebServer webServer =  serverFactory.getWebServer(); // Tomcat 이외의 다른 서버도(Jetty.. 등) 구동이 가능하도록 만들어져서 WebServer 추상화

        // 익명 클래스
        WebServer webServer =  serverFactory.getWebServer(servletContext -> {
            servletContext.addServlet("frontController", new HttpServlet() {
                @Override
                protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                    // super.service(req, resp);
                    String name = req.getParameter("name");

                    /**
                     * 웹 응답의 3가지 요소
                     * (1) status Code (상태라인에서)
                     * (2) Header
                     * (3) Body
                     */

                    // resp.setStatus(200);
                    resp.setStatus(HttpStatus.OK.value());

                    // resp.setHeader("Content-Type", "text/plain"); // <- 오타의 위험성이 있음
                    resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);

                    // resp.getWriter().println("Hello Servlet");
                    resp.getWriter().println("Hello " + name);
                }
            }).addMapping("/hello"); // <-- hello 라고 들어오는 요청이 있으면 처리하겠다.
        });

        webServer.start();

        System.out.println("SOSOSOSOSO HEE");
       // SpringApplication.run(HellobootApplication.class, args);
    }

}
