package org.example;

import java.util.ArrayList;
import java.util.List;

public class GameCompany implements Observable {
    private List<Observer> developers;
    private List<Observer> gamers;
    private List<Observer> journalists;
    private News gameNews;

    public GameCompany() {
        developers = new ArrayList<>();
        gamers = new ArrayList<>();
        journalists = new ArrayList<>();
    }

    public void setUpdate(News news) {
        this.gameNews = news;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer o) {

        switch (o.getClass().getSimpleName()) {
            case "Developer":
                developers.add(o);
                break;
            case "Journalist":
                journalists.add(o);
                break;
            case "Gamer":
                gamers.add(o);
                break;
        }
    }

    @Override
    public void removeObserver(Observer o) {

        switch (o.getClass().getSimpleName()) {
            case "Developer":
                developers.remove(o);
                break;
            case "Journalist":
                journalists.remove(o);
                break;
            case "Gamer":
                gamers.remove(o);
                break;
        }
    }

    @Override
    public void notifyObservers() {
        for (Observer o : developers)
            o.update(gameNews);

        for (Observer o : gamers)
            o.update(gameNews);

        for (Observer o : journalists)
            o.update(gameNews);
    }
}
