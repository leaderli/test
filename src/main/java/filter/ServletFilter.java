package filter;

import util.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Created by li on 6/1/17.
 */
//@WebFilter(urlPatterns = "/*")
public class ServletFilter implements javax.servlet.Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        if ("/".equals(uri)) {
            request.getRequestDispatcher(uri).forward(req, resp);
            return;
        } else if (uri.contains(".")) {
            request.getRequestDispatcher(uri).forward(req, resp);
            return;
        }
        System.out.println("filter " + uri);
        request.getRequestDispatcher(Constant.BaseServletURL + uri).forward(req, resp);
        if (!resp.isCommitted()) {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {

    }

}
