package state;

public class Kill {

    private String from;
    private String to;

    public Kill(String from, String to){
        this.from = from;
        this.to = to;
    }

    public Kill(){
        this.from = "";
        this.to = "";

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
}
