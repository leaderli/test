package study.xml;

import li.util.Log;
import org.dom4j.DocumentException;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

import java.util.List;

public class XPathUtil {
  public static void main(String[] args) throws DocumentException {

    SAXReader saxReader = new SAXReader(new DOMDocumentFactory());
    DOMDocument doc = (DOMDocument) saxReader.read(XPathUtil.class.getResourceAsStream("normal.xml"));
    List<DOMElement> age = doc.selectNodes("//root/detail/age");
    for (DOMElement element : age) {
      Log.log(element.asXML());


    }
  }

  @Test
  public void test() {
    Log.log(1);
    Log.log(2);
  }
}
