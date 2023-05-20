package net.openurp.shcmusic.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.beangle.ems.app.EmsApp;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Base64;

@Configuration
public class BasicHttpAuthenticationFilter implements Filter {

  private String key;

  protected FilterConfig filterConfig;

  private static final String HEADER = "BASIC";

  public final void init(FilterConfig filterConfig) throws ServletException {
    var client = EmsApp.Instance.getProperties().get("graphql.client");
    var secret = EmsApp.Instance.getProperties().get("graphql.secret");
    String pattern = client + ":" + secret;
    key = new String(Base64.getEncoder().encode(pattern.getBytes()));
    setFilterConfig(filterConfig);
  }

  public void setFilterConfig(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

  public final void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (isAccessAllowed(httpRequest, httpResponse)) {
      filterChain.doFilter(request, response);
    } else {
      httpResponse.setStatus(401);
      String authcHeader = "BASIC realm=\"GraphQL\"";
      httpResponse.setHeader("BASIC", authcHeader);
    }
  }

  public static void main(String[] args) {
    String client = "client";
    String secret = "secret";
    String pattern = client + ":" + secret;
    String a = new String(Base64.getEncoder().encode(pattern.getBytes()));
    System.out.println(a);
  }


  protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
    String uri = request.getRequestURI();
    if (uri.equals("/graphiql")) return true;
    if (request.getRemoteAddr().equals("127.0.0.1") || request.getRemoteAddr().equals("localhost")) return true;
    String authorizationHeader = request.getHeader("Authorization");
    if (null == authorizationHeader) {
      return false;
    }
    String[] authorizations = authorizationHeader.split(",");
    String basic = "";
    for (String tempString : authorizations) {
      if (tempString.contains("Basic")) {
        basic = tempString.substring(tempString.indexOf("Basic") + 5).trim();
      }
    }
    return basic.equals(this.key);
  }

  protected String[] getPrincipalsAndCredentials(String scheme, String encoded) {
    String decoded = new String(Base64.getDecoder().decode(encoded));
    return decoded.split(":", 2);
  }
}
