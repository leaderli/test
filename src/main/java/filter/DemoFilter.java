package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      System.out.println(((HttpServletRequest) request).getRequestURI());
    }
    MyResponse myResponse = new MyResponse((HttpServletResponse) response);
    chain.doFilter(request, myResponse);
    System.out.println(myResponse.getContent());
    String result = myResponse.getContent();
    result += "\r\nfuck you";
    response.getWriter().println(result);
  }

  @Override
  public void destroy() {

  }
}
