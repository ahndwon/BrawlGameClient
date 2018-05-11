package Windows;

public class ItemConsume {
    private String userName;
    private int index;

    public ItemConsume() {

    }

    public ItemConsume(String userName, String item) {
        this.userName = userName;
        this.index = index;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
