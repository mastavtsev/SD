package org.example;

import java.util.List;

public class News {
    String title;
    String name;
    List<String> achievements;
    String  developLanguage;

    public News(String title, String name, List<String> achievements, String developLanguage) {
        this.title = title;
        this.name = name;
        this.achievements = achievements;
        this.developLanguage = developLanguage;
    }

}
