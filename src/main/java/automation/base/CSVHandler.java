package automation.base;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class CSVHandler {
    private static final String SEPERATOR = ",";

    public CSVHandler() {
    }

    public static List<List<String>> readCSV(File file) {
        try {
            return (List) Files.lines(file.toPath(), StandardCharsets.UTF_8).map((line) -> {
                return Arrays.asList(line.split(","));
            }).collect(Collectors.toList());
        } catch (IOException var2) {
            throw new UncheckedIOException(var2);
        }
    }

    public static List<Map<String, String>> readCSVToHashMap(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;
            HashMap<String, String> map = new HashMap<String, String>();
//            String str[] = line.split(",");
            while ((line = br.readLine()) != null) {
                String str[] = line.split(",");
                for (int i = 1; i < str.length; i++) {
                    String arr[] = str[i].split(":");
                    map.put(arr[0], arr[1]);
                }

            }
            return Collections.singletonList(map);
        } catch (IOException var2) {
            throw new UncheckedIOException(var2);
        }

    }

    public static File getTheNewestFile(String filePath) {
        File theNewestFile = null;
        File dir = new File(filePath);
        FileFilter fileFilter = new WildcardFileFilter("*.csv");
        File[] files = dir.listFiles(fileFilter);
        if (files.length > 0) {
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            theNewestFile = files[0];
        }

        return theNewestFile;
    }

    public static String createCSVString(List<String> header, List<List<String>> data) {
        List<String> csv = new ArrayList();
        csv.add(header.stream().reduce((t, u) -> {
            return t + "," + u;
        }).get());
        data.forEach((linex) -> {
            csv.add(linex.stream().reduce((t, u) -> {
                return t + "," + u;
            }).get());
        });
        StringBuilder csvString = new StringBuilder();
        Iterator var4 = csv.iterator();

        while (var4.hasNext()) {
            String line = (String) var4.next();
            csvString.append(line);
            csvString.append("\r\n");
        }

        return csvString.toString();
    }
}

