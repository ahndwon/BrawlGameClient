package state;

public class Move {

    private String direction;

    public Move(String direction){
        this.direction = direction;
    }

    public Move(){
        this.direction = "";
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
