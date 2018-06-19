FastIni
=======

中文说明文档 [README_CN.md][1]

# Update

- add gradle build system
- add Intellij idea ini file comments support

# Gradle

To get a Git project into your build:

**Step 1. Add the JitPack repository to your build file**

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2. Add the dependency**

```groovy
dependencies {
    implementation 'com.github.onlynight:FastIni:1.2.1'
}
```

# Maven

To get a Git project into your build:

**Step 1. Add the JitPack repository to your build file**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

**Step 2. Add the dependency**

```xml
<dependency>
    <groupId>com.github.onlynight</groupId>
    <artifactId>FastIni</artifactId>
    <version>1.2.1</version>
</dependency>
```

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
        private String appId;
        private String appKey;
        private String apiUrl;
        private List<String> others;

        private String translatePlatform;
        private String textType;
        private String sourceLanguage;
        private List<String> destinationLanguage;
        private String sourceFilePath;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getApiUrl() {
            return apiUrl;
        }

        public void setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
        }

        public List<String> getOthers() {
            return others;
        }

        public void setOthers(List<String> others) {
            this.others = others;
        }

        public String getTranslatePlatform() {
            return translatePlatform;
        }

        public void setTranslatePlatform(String translatePlatform) {
            this.translatePlatform = translatePlatform;
        }

        public String getTextType() {
            return textType;
        }

        public void setTextType(String textType) {
            this.textType = textType;
        }

        public String getSourceLanguage() {
            return sourceLanguage;
        }

        public void setSourceLanguage(String sourceLanguage) {
            this.sourceLanguage = sourceLanguage;
        }

        public List<String> getDestinationLanguage() {
            return destinationLanguage;
        }

        public void setDestinationLanguage(List<String> destinationLanguage) {
            this.destinationLanguage = destinationLanguage;
        }

        public String getSourceFilePath() {
            return sourceFilePath;
        }

        public void setSourceFilePath(String sourceFilePath) {
            this.sourceFilePath = sourceFilePath;
        }

        @Override
        public String toString() {
            StringBuilder languages = new StringBuilder();
            try {
                for (String lang : destinationLanguage) {
                    languages.append(lang).append(",");
                }
                languages.deleteCharAt(languages.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "appId" + " = " + appId + "\n" +
                    "appKey" + " = " + appKey + "\n" +
                    "apiUrl" + " = " + apiUrl + "\n" +
                    "others" + " = " + list2String(others) + "\n" +
                    "translatePlatform" + " = " + translatePlatform + "\n" +
                    "textType" + " = " + textType + "\n" +
                    "sourceLanguage" + " = " + sourceLanguage + "\n" +
                    "destinationLanguage" + " = " + list2String(destinationLanguage) + "\n" +
                    "sourceFilePath" + " = " + sourceFilePath + "\n";
        }

        private static String list2String(List<String> list) {
            StringBuilder languages = new StringBuilder();

            if (list != null) {
                try {
                    for (String data : list) {
                        languages.append(data).append(",");
                    }
                    languages.deleteCharAt(languages.length() - 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return languages.toString();
        }
    }
}
```

### 2. use key to load ini key values.

```java
@Test
public void testParseDoc() {
    String filePath = new File(new File("").getAbsoluteFile(),
            "translate_config.ini").getAbsolutePath();
    IniDocument document = new IniDocument().fromPath(filePath);
    List<String> languages = document.get("destinationLanguage");
    if (languages != null) {
        for (String lang : languages) {
            System.out.println(lang);
        }
    }

    System.out.println(document.get("appKey").get(0));
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
