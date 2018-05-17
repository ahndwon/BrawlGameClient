package Utils;

public class Util {

    public static int getPosXByIndex(int index) {
        return (index % Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE;
    }

    public static int getPosYByIndex(int index) {
        return (index / Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE;
    }

    public static int getPosXByIndex(int index, int lenx) {
        return ((index % Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE) + lenx;
    }

    public static int getPosYByIndex(int index, int leny) {
        return ((index / Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE) + leny;
    }

}
