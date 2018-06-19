FastIni
=======

中文说明文档 [README_CN.md][1]

# Update

The latest version is 1.0.0, fixed all bugs and IniDocument support #get(String key) method to get the sigle key values.

[download latest 1.0.0 version jar lib file](./bin/fastini-1.0.0.jar)

# Introduction

This is a *.ini file parser java tools. It will help you parse .ini file quickly such as google's Gson.

I hava extention the .ini file, becourse the .ini file is the single key value types, so i change the value to the list, if you want some key with a list of value you can write like this:

```ini
key = value1,value2,value3
```

# Release & Import

The release fastini-x.x.x.jar file are in the root of **/bin** directory.
You can download the fast-ini-x.x.x.jar lib, and add it in your project.

# Use

It is simple to use:

```java
FastIni fastIni = new FastIni();
Demo demo = fastIni.fromPath("the file path", Demo.class);
```

or you dont want to get all property by template, you can use like this:

```java
niDocument document = new IniDocument("the file path").parse();
List<String> values = document.get("key");
```

# Sample

## Sample ini file

filename translate_config.ini

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

## Sample parse code

### 1. use template class to parse the document.

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

### 2. use key to load ini key values.

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

# License

```
Copyright 2017 onlynight

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[1]: ./doc/cn/README.md
