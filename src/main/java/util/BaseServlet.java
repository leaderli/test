package util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(urlPatterns = Constant.BaseServletURL+"/*")
public class BaseServlet extends HttpServlet {
    private final static Map<String, String> servlets = new HashMap<>();
    private final static String dirPath = "servlet";
    static {
        fillServlets();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        uri = StringUtils.substringAfterLast(uri, "/").toLowerCase();
        try {
            Object o = Class.forName(servlets.get(uri)).newInstance();
            System.out.println(o);
            Method m = o.getClass().getMethod("home", HttpServletRequest.class, HttpServletResponse.class);
            String redirect = m.invoke(o, req, resp).toString();
            if (redirect.startsWith("@")) {
                uri = "/" + redirect.substring(1);
                req.getRequestDispatcher(uri).forward(req,resp);
            } else {
                req.getRequestDispatcher(redirect).forward(req, resp);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void fillServlets() {
        List<String> servletsName = FileUtils.listFilesUnderExactDir(dirPath);
        for(String name:servletsName){
            servlets.put(name.toLowerCase(),dirPath+"."+name);
        }
        servlets.remove(BaseServlet.class.getSimpleName());
    }
}
