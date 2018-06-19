FastIni
=======

# 概述

相信大家都用过阿里的fastjson，它可以通过模板快速解析json字符串，也可以通过key快速获取json字符串中的值。通过模板类型快速解析json字符串是一个很好的想法，所以本着搞事情的原则这里模仿Gson，fastjson写了一个快速解析ini文件的解析类，他能够通过模板类快速解析ini配置文件，也可以通过key快速获取ini配置文件中的值。（当然你也可以自己手动读取ini配置文件，ini文件结构简单解析也很简单，不过有了这个类你会发现站在巨人的肩膀上。）

# 自定义ini文件格式

使用中发现ini配置文件只能是单键值对，不能够一个键对应多个值，也是就是列表，这里对值的部分进行小幅修改，让它可以支持多个值。如果你想让一个键拥有多个值，那么你可以通过使用```,```将多个值隔开，想这样:

```ini
key = value1,value2,value3
```

# 下载

[在github上下载最新的 1.0.0 版本的jar库](https://github.com/onlynight/FastIni/tree/master/bin/fastini-1.0.0.jar)
[你可以在这里看到源码 https://github.com/onlynight/FastIni](https://github.com/onlynight/FastIni)

# 使用

使用非常简单，首先我们来看一下通过模板class解析的代码:

```java
FastIni fastIni = new FastIni();
Demo demo = fastIni.fromPath("the file path", Demo.class);
```

是不是有一种似曾相识的感觉，对Gson就是这么写的，不一样的是ini是文件，而json传的是字符串。```Demo.class```即为定义的模板class，他会通过字段名自动解析ini配置文件中key值相同的字段。


如果你觉得一次解析所有的配置文件有些浪费，也可以通过key获取其中的某一个键值对：

```java
niDocument document = new IniDocument("the file path").parse();
List<String> values = document.get("key");
```

# Demo

## ini 文件栗子

文件名 translate_config.ini

```ini
[platform key]
# baidu translate api info, you should replace it with your own.
baidu_app_id = 123413312312
baidu_app_secret = sadfwqeersddafasdftryyjghsaddarytre

# google translate api info, you should replace it with your own.
google_api_key = qoiewtusdjifoiasjoteertqwerqwe

# youdao translate api info, you should replace it with your own.
youdao_api_key = 32423512412

[platform]
# now support google, baidu, youdao translate platform api.
# the value you can choose are "google", "baidu", "youdao"
translate_platform = google

[phone platform]
# now support android .xml string res and ios .strings string res translate.
# the value you can choose are "android" or "ios"
phone_platform = android

[translator]
# the source language you provided.
source_language = zh-CN

# if you want translate all language you should use "all" to replace this value.
# use "all" means you want translate the platform support all languages;
# or you should use language code to tell the translate you want to translate.
#
# the platform support language contains in the root of the project xxx_support_laguage.txt
# you can check this file to get the platform support language
destination_language = en,zh_TW

# the translate file must in the same path or sub dir of the translator.jar.
# this value assign the folder name, if you don't want use sub dir,
# just use "/" to replace the value
source_file_path = values
```

## 解析代码

### 通过模板类型解析

```java
public class Main {
    public static void main(String[] args) {
        FastIni ini = new FastIni();
        File currentPath = new File("");
        TestBean bean = ini.fromPath(new File(currentPath.getAbsolutePath(), "translate_config.ini").
                getAbsolutePath(), TestBean.class);
        System.out.println(bean);
    }
    public static class TestBean {
        public static final String TAG = TestBean.class.getSimpleName();
        private String baidu_app_id;
        private String baidu_app_secret;
        private String google_api_key;
        private String youdao_api_key;
        private String translate_platform;
        private String phone_platform;
        private String source_language;
        private List<String> destination_language;
        private String source_file_path;
        public String getBaidu_app_id() {
            return baidu_app_id;
        }
        public void setBaidu_app_id(String baidu_app_id) {
            this.baidu_app_id = baidu_app_id;
        }
        public String getBaidu_app_secret() {
            return baidu_app_secret;
        }
        public void setBaidu_app_secret(String baidu_app_secret) {
            this.baidu_app_secret = baidu_app_secret;
        }
        public String getGoogle_api_key() {
            return google_api_key;
        }
        public void setGoogle_api_key(String google_api_key) {
            this.google_api_key = google_api_key;
        }
        public String getYoudao_api_key() {
            return youdao_api_key;
        }
        public void setYoudao_api_key(String youdao_api_key) {
            this.youdao_api_key = youdao_api_key;
        }
        public String getTranslate_platform() {
            return translate_platform;
        }
        public void setTranslate_platform(String translate_platform) {
            this.translate_platform = translate_platform;
        }
        public String getPhone_platform() {
            return phone_platform;
        }
        public void setPhone_platform(String phone_platform) {
            this.phone_platform = phone_platform;
        }
        public String getSource_language() {
            return source_language;
        }
        public void setSource_language(String source_language) {
            this.source_language = source_language;
        }
        public String getSource_file_path() {
            return source_file_path;
        }
        public void setSource_file_path(String source_file_path) {
            this.source_file_path = source_file_path;
        }
        public List<String> getDestination_language() {
            return destination_language;
        }
        public void setDestination_language(List<String> destination_language) {
            this.destination_language = destination_language;
        }

        @Override public String toString() {
            StringBuilder languages = new StringBuilder();
            try {
                for (String lang : destination_language) {
                    languages.append(lang).append(",");
                }
                languages.deleteCharAt(languages.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "baidu_app_id" + " = " + baidu_app_id + "\n" +
                    "baidu_app_secret" + " = " + baidu_app_secret + "\n" +
                    "google_api_key" + " = " + google_api_key + "\n" +
                    "youdao_api_key" + " = " + youdao_api_key + "\n" +
                    "translate_platform" + " = " + translate_platform + "\n" +
                    "phone_platform" + " = " + phone_platform + "\n" +
                    "source_file_path" + " = " + source_file_path + "\n" +
                    "destination_language" + " = " + languages.toString() + "\n" +
                    "source_file_path" + " = " + source_file_path + "\n";
        }
    }
}
```

### 通过key获取其中某一个键值对

```java
public static void main(String[] args) {
    IniDocument document = new IniDocument(new File(new File("").getAbsoluteFile(),
            "translate_config.ini").getAbsolutePath()).parse();
    List<String> languages = document.get("destination_language");
    if (languages != null) {
        for (String lang : languages) {
            System.out.println(lang);
        }
    }
}
```

# 源码分析

IniDocument.java 这个类主要负责解析和存储ini文件键值对，关键解析代码如下：

```java
/**
 *fromPath
 * after create {@link IniDocument} you should call {@link this#parse()}fromPath
 */
public IniDocument parse() {
    try {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
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
```

主要代码逻辑就是读取每一行，过滤注释，将每一个行键值对分别复制给line的键和值，最后多行line组成document。

下面我们来看看```FastIni#fromPath()```和```FastIni#fromDocument()```方法：

```java
/**
 *fromPath
 *
 * @param path  *.ini file path
 * @param clazz the class model you defined to receive the ini file config key values.
 * @param <T>   the template you pass in class
 * @return the data you pass in class instance.
 */
public <T> T fromPath(String path, Class<T> clazz) {
    return fromDocument(new IniDocument(path).parse(), clazz);
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
```

可以看到两个方法实际上实现是一样的，只是```fromPath```方法内部通过path生成了doc文件，其核心代码即为```fromDocument()```。
该方法中首先实例化了一个模板类型的对象，然后获取了模板类中所有定义的属性，将非静态属性与document中的key进行一一对比，匹配成功后对模板类实例进行复制操作，注意这里由于没有具体的类，所以不能通过get/set方法对属性进行复制，这里使用反射对field进行复制操作，为了保证能够操作类的私有属性，赋值前你需要先将field设置为可访问状态```field.setAccessible(true);```。

关键源码即为以上两段，希望这个工具能够给你带来帮助。
