package org.example;

public class Developer implements Observer{
    private final String name;

    public Developer(String name, Observable o) {
        this.name = name;
        o.registerObserver(this);
    }

    @Override
    public void update(News news) {
        System.out.println("Developer " + name + " used " + news.developLanguage);
    }
}
