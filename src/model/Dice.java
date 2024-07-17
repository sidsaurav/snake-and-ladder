package model;

public class Dice {
    int maxVal;
    public Dice(int maxVal){
        this.maxVal = maxVal;
    }
    public static int roll(int maxVal) {
        return (int)(Math.random()*maxVal) + 1;
    }
}
