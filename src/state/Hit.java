package state;

public class Hit {

    private String from;
    private String to;
    private int damage;

    public Hit(){

    }

    public Hit(String from, String to, int damage){
        this.from = from;
        this.to = to;
        this.damage = damage;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
