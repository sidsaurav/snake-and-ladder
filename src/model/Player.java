package model;

public class Player {
    String name;
    String id;

    public Player(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getPlayerID() {
        return id;
    }

    public String getPlayerName() {
        return name;
    }
}
