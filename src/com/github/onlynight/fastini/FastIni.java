package com.github.onlynight.fastini;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/4/9.
 * fast .ini file parser.
 * <p>
 * demo
 * <p>
 * public class Main {
 * <p>
 * public static void main(String[] args) {
 * FastIni ini = new FastIni();
 * File currentPath = new File("");
 * TestBean bean = ini.fromPath(new File(currentPath.getAbsolutePath(), "translate_config.ini").
 * getAbsolutePath(), TestBean.class);
 * System.out.println(bean);
 * }
 * <p>
 * public static class TestBean {
 * <p>
 * public static final String TAG = TestBean.class.getSimpleName();
 * <p>
 * private String baidu_app_id;
 * private String baidu_app_secret;
 * private String google_api_key;
 * private String youdao_api_key;
 * <p>
 * private String translate_platform;
 * <p>
 * private String phone_platform;
 * <p>
 * private String source_language;
 * private List<String> destination_language;
 * private String source_file_path;
 * <p>
 * public String getBaidu_app_id() {
 * return baidu_app_id;
 * }
 * <p>
 * public void setBaidu_app_id(String baidu_app_id) {
 * this.baidu_app_id = baidu_app_id;
 * }
 * <p>
 * public String getBaidu_app_secret() {
 * return baidu_app_secret;
 * }
 * <p>
 * public void setBaidu_app_secret(String baidu_app_secret) {
 * this.baidu_app_secret = baidu_app_secret;
 * }
 * <p>
 * public String getGoogle_api_key() {
 * return google_api_key;
 * }
 * <p>
 * public void setGoogle_api_key(String google_api_key) {
 * this.google_api_key = google_api_key;
 * }
 * <p>
 * public String getYoudao_api_key() {
 * return youdao_api_key;
 * }
 * <p>
 * public void setYoudao_api_key(String youdao_api_key) {
 * this.youdao_api_key = youdao_api_key;
 * }
 * <p>
 * public String getTranslate_platform() {
 * return translate_platform;
 * }
 * <p>
 * public void setTranslate_platform(String translate_platform) {
 * this.translate_platform = translate_platform;
 * }
 * <p>
 * public String getPhone_platform() {
 * return phone_platform;
 * }
 * <p>
 * public void setPhone_platform(String phone_platform) {
 * this.phone_platform = phone_platform;
 * }
 * <p>
 * public String getSource_language() {
 * return source_language;
 * }
 * <p>
 * public void setSource_language(String source_language) {
 * this.source_language = source_language;
 * }
 * <p>
 * public String getSource_file_path() {
 * return source_file_path;
 * }
 * <p>
 * public void setSource_file_path(String source_file_path) {
 * this.source_file_path = source_file_path;
 * }
 * <p>
 * public List<String> getDestination_language() {
 * return destination_language;
 * }
 * <p>
 * public void setDestination_language(List<String> destination_language) {
 * this.destination_language = destination_language;
 * }
 *
 * @Override public String toString() {
 * StringBuilder languages = new StringBuilder();
 * try {
 * for (String lang : destination_language) {
 * languages.append(lang).append(",");
 * }
 * languages.deleteCharAt(languages.length() - 1);
 * } catch (Exception e) {
 * e.printStackTrace();
 * }
 * return "baidu_app_id" + " = " + baidu_app_id + "\n" +
 * "baidu_app_secret" + " = " + baidu_app_secret + "\n" +
 * "google_api_key" + " = " + google_api_key + "\n" +
 * "youdao_api_key" + " = " + youdao_api_key + "\n" +
 * "translate_platform" + " = " + translate_platform + "\n" +
 * "phone_platform" + " = " + phone_platform + "\n" +
 * "source_file_path" + " = " + source_file_path + "\n" +
 * "destination_language" + " = " + languages.toString() + "\n" +
 * "source_file_path" + " = " + source_file_path + "\n";
 * }
 * }
 * }
 */
public class FastIni {

    private Class clazz;

    public FastIni() {
    }

    public <T> T fromPath(String path, Class<T> clazz) {
        this.clazz = clazz;
        IniDocument document = new IniDocument(path);
        document.parse();
        getNeedParseFieldsName();
        return fromDocument(document);
    }

    private List<String> getNeedParseFieldsName() {
        Field[] fields = this.clazz.getDeclaredFields();
        List<String> fieldsName = new ArrayList<>();
        for (Field field : fields) {
            if ((field.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                fieldsName.add(field.getName());
            }
        }
        return fieldsName;
    }

    public <T> T fromDocument(IniDocument document) {

        if (document == null || document.getLines() == null ||
                document.getLines().size() <= 0) {
            return null;
        }

        try {
            T instance = (T) Class.forName(clazz.getName()).newInstance();
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    for (IniDocument.KeyValue keyValue : document.getLines()) {
                        try {
                            if (keyValue.getKey() != null &&
                                    field.getName().equals(keyValue.getKey()) &&
                                    keyValue.getValue().size() > 0) {
                                field.setAccessible(true);
                                if (field.getType() == List.class) {
                                    field.set(instance, keyValue.getValue());
                                } else {
                                    field.set(instance, keyValue.getValue().get(0));
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }

            return instance;
        } catch (IllegalAccessException |
                InstantiationException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
