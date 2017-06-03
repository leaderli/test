package filter;

import util.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Created by li on 6/1/17.
 */
//@WebFilter(filterName = "c", urlPatterns = "/*")
public class TheLastFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        if (uri.contains(".") || uri.equals("/")) {
            request.getRequestDispatcher(uri).forward(req, resp);
            return;
        }
        System.out.println("filter " + uri);
        request.getRequestDispatcher(Constant.BaseServletURL + uri).forward(req, resp);
        if (!resp.isCommitted())
            chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {

    }

}
