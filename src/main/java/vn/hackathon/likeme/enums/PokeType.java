package vn.hackathon.likeme.enums;

/**
 * Created by bangnl on 3/9/16.
 */
public enum PokeType {
    POKE(1), ACCEPT(2), CANCEL(3);
    PokeType(int num){
        this.num = num;
    }
    private int num;

    public int getNum() {
        return num;
    }
}
