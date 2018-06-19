package com.github.onlynight.fastini;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lion on 2017/4/9.
 * *.ini file document model.
 */
public class IniDocument {

    /**
     * comment ignore start
     */
    private static final String IGNORE_COMMENTS_START = "#";

    /**
     * compat intellij idea comment start
     */
    private static final String IGNORE_COMMENTS_IDEA_START = ";";

    /**
     * group tag start
     */
    private static final String IGNORE_TAG_START = "[";

    /**
     * the document source line string
     */
    private List<String> srcLines;
    /**
     * the document lines
     */
    private List<KeyValue> lines;

    public IniDocument() {
        srcLines = new ArrayList<>();
        lines = new ArrayList<>();
    }

    /**
     * get all source lines
     *
     * @return source lines
     */
    public List<String> getSrcLines() {
        return srcLines;
    }

    /**
     * get all parsed lines
     *
     * @return all parsed lines
     */
    public List<KeyValue> getLines() {
        return lines;
    }

    /**
     * get keyvalue by key
     *
     * @param key the config key
     * @return value
     */
    public List<String> get(String key) {
        for (KeyValue keyValue : lines) {
            if (keyValue.getKey().equals(key)) {
                return keyValue.getValue();
            }
        }

        return null;
    }

    /**
     * fromPath document.
     * after create {@link IniDocument} you should call {@link this#fromPath(String)} method to fromPath the document.
     */
    public IniDocument fromPath(String path) {
        try {
            fromStream(new FileInputStream(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * fromPath document.
     * after create {@link IniDocument} you should call {@link this#fromPath(String)} method to fromPath the document.
     */
    public IniDocument fromStream(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                srcLines.add(line);
            }
            reader.close();

            for (String srcLine : srcLines) {
                // 过滤多行注释，单行
                if (srcLine.contains(IGNORE_COMMENTS_START)) {
                    continue;
                }

                // 过滤多行注释，单行
                if (srcLine.contains(IGNORE_COMMENTS_IDEA_START)) {
                    continue;
                }

                // 过滤分组标签
                if (srcLine.contains(IGNORE_TAG_START)) {
                    continue;
                }

                // 识别关键行, pattern1
                if (srcLine.contains("=") &&
                        !srcLine.startsWith(IGNORE_COMMENTS_START)) {
                    KeyValue tempLine = new KeyValue();
                    tempLine.setLine(srcLine);
                    String temp = srcLine.replace(" ", "");
                    String[] data = temp.split("=");
                    if (data.length > 0) {
                        tempLine.setKey(data[0]);
                    }

                    if (data.length > 1) {
                        String[] values = data[1].split(",");
                        tempLine.setValue(Arrays.asList(values));
                    }
                    lines.add(tempLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * line key value class to save the parsed document data model.
     */
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
