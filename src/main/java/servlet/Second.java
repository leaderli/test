package servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by li on 6/1/17.
 */
public class Second extends HttpServlet {
    public String home(HttpServletRequest request, HttpServletResponse response){
        System.out.println("second");
        return "@nav.html";
    }
}
