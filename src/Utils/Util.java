package Utils;

public class Util {

    public static int getPosXByIndex(int index) {
        return (index % Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE;
    }

    public static int getPosYByIndex(int index) {
        return (index / Constants.BLOCK_COUNT_X) * Constants.BLOCK_SIZE;
    }

    public static int getPosXByIndexForMiniMap(int index) {
        return (((index % 32) * 5));
    }

    public static int getPosYByIndexForMiniMap(int index) {
        return (index / 32) * 5 ;
    }

    public static int getIndexByPosForMiniMap(float x, float y) {

        return (int) ((x / 5) + (((y) / 5) * 32));

    }

}