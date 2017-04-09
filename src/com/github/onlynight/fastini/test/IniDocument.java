package com.github.onlynight.fastini.test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lion on 2017/4/9.
 * *.ini file document model.
 */
public class IniDocument {

    private static final String IGNORE_COMMENTS_START = "#";
    private static final String IGNORE_TAG_START = "[";

    private String path;

    private List<String> srcLines;
    private List<KeyValue> lines;

    public IniDocument(String path) {
        this.path = path;
        srcLines = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public List<String> getSrcLines() {
        return srcLines;
    }

    public List<KeyValue> getLines() {
        return lines;
    }

    public void parse() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                srcLines.add(line);
            }
            reader.close();

            for (String srcLine : srcLines) {
                KeyValue tempLine = new KeyValue();
                tempLine.setLine(srcLine);

                // 过滤多行注释，单行
                if (srcLine.contains(IGNORE_COMMENTS_START)) {
                    continue;
                }

                // 过滤分组标签
                if (srcLine.contains(IGNORE_TAG_START)) {
                    continue;
                }

                // 识别关键行, pattern1
                if (srcLine.contains("=") &&
                        !srcLine.startsWith(IGNORE_COMMENTS_START)) {
                    String temp = srcLine.replace(" ", "");
                    String[] data = temp.split("=");
                    if (data.length > 0) {
                        tempLine.setKey(data[0]);
                    }

                    if (data.length > 1) {
                        String[] values = data[1].split(",");
                        tempLine.setValue(Arrays.asList(values));
                    }
                }
                lines.add(tempLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class KeyValue {
        private String key;
        private List<String> value;
        private String line;

        public String getLine() {
            return line;
        }

        public void setLine(String line) {
            this.line = line;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
