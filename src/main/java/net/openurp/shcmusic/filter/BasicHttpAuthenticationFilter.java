package net.openurp.shcmusic.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;

public class BasicHttpAuthenticationFilter implements Filter {
  protected FilterConfig filterConfig;

  private static final String HEADER = "BASIC";

  public final void init(FilterConfig filterConfig) throws ServletException {
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
    String pattern = client + "@" + client + ":" + tenant;
    String a = new String(Base64.getEncoder().encode(pattern.getBytes()));
    System.out.println(a);
  }

  protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) {
    String authorizationHeader = request.getHeader("authorization");
    if (null == authorizationHeader) return false;
    String[] authTokens = authorizationHeader.split(" ");
    if (authTokens.length != 2) return false;
    return (authTokens.length > 1 && "R253VVlESU50MXJhSEttaEU5NlFAc2hjbXVzaWMuZWR1LmNuOjczNzY5Nzk4MDIyOTA0MzdFNTM0RTVBODVEOENGOEQzODA3MERBN0NBMkE0NzYzRQ==".equals(authTokens[1]));
  }

  protected String[] getPrincipalsAndCredentials(String scheme, String encoded) {
    String decoded = new String(Base64.getDecoder().decode(encoded));
    return decoded.split(":", 2);
  }
}
