package org.example;

public class Gamer implements Observer{
    private final String name;

    public Gamer(String name, Observable o) {
        this.name = name;
        o.registerObserver(this);
    }

    @Override
    public void update(News news) {
        System.out.println("Gamer " + name + " plays in " + news.name);
        System.out.println("And can get several achievements: " + news.achievements);
    }
}
