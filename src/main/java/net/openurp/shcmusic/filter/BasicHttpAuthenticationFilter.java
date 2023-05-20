package net.openurp.shcmusic.filter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.beangle.ems.app.EmsApp;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Base64;

@Configuration
public class BasicHttpAuthenticationFilter implements Filter {

  private String GRAPHQL_CLIENT;

  private String GRAPHQL_SECRET;

  protected FilterConfig filterConfig;

  private static final String HEADER = "BASIC";

  public final void init(FilterConfig filterConfig) throws ServletException {
    GRAPHQL_CLIENT = EmsApp.Instance.getProperties().get("graphql.client");
    GRAPHQL_SECRET = EmsApp.Instance.getProperties().get("graphql.secret");
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
    String client = "GnwUYDINt1raHKmhE96Q";
    String secret = "7376979802290437E534E5A85D8CF8D38070DA7CA2A4763E";
    String tenant = "shcmusic.edu.cn";
//    String pattern = client + "@" + secret + ":" + tenant;
    String pattern = client + ":" + secret;
    String a = new String(Base64.getEncoder().encode(pattern.getBytes()));
    System.out.println(a);
  }


  protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
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
    Base64.Decoder decoder = Base64.getDecoder();
    String client = new String(decoder.decode(basic));
    if (StringUtils.isNotEmpty(client) && StringUtils.isNotBlank(client)) {
      return client.equals(GRAPHQL_CLIENT + ":" + GRAPHQL_SECRET);
    }
    return false;
  }

  protected String[] getPrincipalsAndCredentials(String scheme, String encoded) {
    String decoded = new String(Base64.getDecoder().decode(encoded));
    return decoded.split(":", 2);
  }
}
