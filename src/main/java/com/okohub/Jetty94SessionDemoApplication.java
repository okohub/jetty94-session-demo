package com.okohub;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Jetty94SessionDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(Jetty94SessionDemoApplication.class, args);
  }

  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new ErrorPagesCustomizer();
  }

  private static class ErrorPagesCustomizer implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
      container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
      container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));
    }
  }

  @RestController
  class HelloController {

    @GetMapping("/")
    String get() {
      return "Ok!";
    }

    @GetMapping("/404")
    public String notFound(HttpServletRequest request) {
      HttpSession session = request.getSession();
      return session.getId();
    }
  }
}
