package hashcode2020;

public final class Utility {
    public static String[] convertIntArrayToStringArray (Integer[] integerArray, String[] stringArray ,int size){
        for (int i=0; i<size; i++){
            stringArray[i]=String.valueOf(integerArray[i]);
        }
        return stringArray;
    }

    public static boolean getDebug(){
        return false;
        //return true;
    }
}
