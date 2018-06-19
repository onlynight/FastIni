package com.github.onlynight.fastini;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class IniDocumentTest {

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

}