package org.example;

public class Journalist implements Observer{
    private final String name;

    public Journalist(String name, Observable o) {
        this.name = name;
        o.registerObserver(this);
    }

    @Override
    public void update(News news) {
        System.out.println("Journalist " + name + " wrote a post about " + news.title);
    }
}
