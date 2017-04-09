package com.github.onlynight.fastini;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2017/4/9.
 * fast .ini file parser.
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
                        if (field.getName().equals(keyValue.getKey()) &&
                                keyValue.getValue().size() > 0) {
                            field.setAccessible(true);
                            if (field.getType() == List.class) {
                                field.set(instance, keyValue.getValue());
                            } else {
                                field.set(instance, keyValue.getValue().get(0));
                            }
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
