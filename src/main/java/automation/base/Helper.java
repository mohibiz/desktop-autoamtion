package automation.base;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public Helper() {
    }

    public static void checkExists(boolean conditions, String error) {
        if (!conditions) {
            error(error);
        }

    }

    public static void error(String error) {
        throw new RuntimeException("Error : " + error);
    }

    public static String convertStacktrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public static String createFolder(String path) {
        File dir = new File(path);
        dir.mkdir();
        return path;
    }

    public static String getCurrentTimestamp(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String time = dateFormat.format(new Date());
        return replace(time);
    }

    private static String replace(String s) {
        char[] chs = s.toCharArray();

        for (int i = 0; i < chs.length; ++i) {
            if (!Character.isLetterOrDigit(chs[i])) {
                chs[i] = '_';
            }
        }

        return new String(chs);
    }
}

