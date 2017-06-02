package util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by li on 6/2/17.
 */
public class FileUtils {
    public static List<String> listFilesUnderExactDir(String dirPath) {
        List<String> list = new ArrayList<>();
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    "servlet");
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String path = null;
                try {
                    path = URLDecoder.decode(url.getFile(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                File file;
                if (path != null) {
                    file = new File(path);
                    File[] files = file.listFiles();
                    if (files == null)
                        return list;
                    for (File f : files) {
                        list.add(StringUtils.substringBeforeLast(f.getName(),"."));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}