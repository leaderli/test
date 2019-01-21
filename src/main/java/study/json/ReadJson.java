package study.json;

import com.alibaba.fastjson.JSONObject;
import li.util.Log;
import li.util.StringUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ReadJson {
  @Test
  public void test1() throws IOException {
    Log.log("***********************");
    String json = StringUtil.inputStream2String(ReadJson.class.getResourceAsStream("config.json"));
    JsonBean jsonObject = JSONObject.parseObject(json, JsonBean.class);
    Log.log(jsonObject);
  }
}
