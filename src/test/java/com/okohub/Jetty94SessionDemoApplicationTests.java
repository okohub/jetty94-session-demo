package com.okohub;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Jetty94SessionDemoApplicationTests {

  @Autowired
  private TestRestTemplate testRestTemplate;

  /**
   * SessionRepositoryFilter dispathcer types = FORWARD, INCLUDE, REQUEST, ASYNC
   *
   * @see JettyEmbeddedServletContainerFactory.Jetty94SessionConfigurer
   */
  @Test
  public void checkJSESSIONIDExists() {
    ResponseEntity<String> notFound = testRestTemplate.getForEntity("/asdsad", String.class);
    HttpHeaders headers = notFound.getHeaders();
    List<String> setCookies = headers.get("Set-Cookie");
    assertThat(setCookies).isNotEmpty();
    String cookie = setCookies.get(0);
    assertThat(cookie).contains("JSESSIONID");
  }
}
