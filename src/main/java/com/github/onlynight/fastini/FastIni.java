package com.github.onlynight.fastini;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by lion on 2017/4/9.
 * fast .ini file parser.
 */

/**
 * FastIni parser.
 */
public class FastIni {

    /**
     * fromPath *.ini file from path
     *
     * @param path  *.ini file path
     * @param clazz the class model you defined to receive the ini file config key values.
     * @param <T>   the template you pass in class
     * @return the data you pass in class instance.
     */
    public <T> T fromPath(String path, Class<T> clazz) {
        return fromDocument(new IniDocument().fromPath(path), clazz);
    }

    public <T> T fromStream(InputStream inputStream, Class<T> clazz) {
        return fromDocument(new IniDocument().fromStream(inputStream), clazz);
    }

    public List<IniDocument.KeyValue> fromPath(String path) {
        return new IniDocument().fromPath(path).getLines();
    }

    public List<IniDocument.KeyValue> fromStream(InputStream inputStream) {
        return new IniDocument().fromStream(inputStream).getLines();
    }

    public <T> T fromDocument(IniDocument document, Class<T> clazz) {

        if (document == null || document.getLines() == null ||
                document.getLines().size() <= 0) {
            return null;
        }

        try {
            // create template instance
            T instance = (T) Class.forName(clazz.getName()).newInstance();

            // get the class field you declared.
            Field[] fields = instance.getClass().getDeclaredFields();

            // set the value to the template fields from the IniDocument model.
            for (Field field : fields) {

                // filter static fields
                if (!Modifier.isStatic(field.getModifiers())) {

                    // match the ini document lines to template fields
                    for (IniDocument.KeyValue keyValue : document.getLines()) {
                        try {
                            if (keyValue.getKey() != null &&
                                    field.getName().equals(keyValue.getKey()) &&
                                    keyValue.getValue().size() > 0) {

                                /**
                                 * set the template fields accessible even if it is a private field.
                                 */
                                field.setAccessible(true);

                                // set the value to the fields by the types
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

