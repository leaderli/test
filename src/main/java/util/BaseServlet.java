package util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 6/1/17.
 */
//@WebServlet(urlPatterns = Constant.BaseServletURL + "/*")
public class BaseServlet extends HttpServlet {
    private final static Map<String, String> servlets = new HashMap<>();
    private final static String dirPath = "servlet";

    static {
        fillServlets();
    }

    private static void fillServlets() {
        List<String> servletsName = FileUtils.listFilesUnderExactDir(dirPath);
        for (String name : servletsName) {
            servlets.put(name.toLowerCase(), dirPath + "." + name);
        }
        servlets.remove(BaseServlet.class.getSimpleName());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        uri = StringUtils.substringAfter(uri, Constant.BaseServletURL + "/");
        String[] paths = uri.split("/");
        String servletName = servlets.get(paths[0].toLowerCase());
	    if (servletName == null) {
		    redirectByCase(req, resp, "");
		    return;
        }
        String methodName = "home";
        try {
            Object o = Class.forName(servletName).newInstance();
            if (paths.length > 1) {
                methodName = getNameIfMethodExist(paths[1], methodName, o);
            }
            Method m = o.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String redirect = m.invoke(o, req, resp).toString();
            redirectByCase(req, resp, redirect);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void redirectByCase(HttpServletRequest req, HttpServletResponse resp, String redirect) throws ServletException, IOException {
	    redirect = "/" + redirect;
        System.out.println(redirect);
        req.getRequestDispatcher(redirect).forward(req, resp);
    }

    private String getNameIfMethodExist(String lowerCase, String methodName, Object o) {
        Method[] methods = o.getClass().getMethods();
        String temp;
        for (Method m : methods) {
            temp = m.getName();
            if (temp.toLowerCase().equals(lowerCase)) {
                return temp;
            }
        }
        return methodName;
    }
}
