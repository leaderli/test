package filter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyResponse extends HttpServletResponseWrapper {
  private CharArrayWriter sb = new CharArrayWriter();

  public MyResponse(HttpServletResponse respons) {
    super((HttpServletResponse) respons);
  }


  public String getContent() {
    return new String(sb.toCharArray());
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    PrintWriter writer = new PrintWriter(sb);
    return writer;
  }


}
