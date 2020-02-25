package hashcode2020;

public final class Utility {
    private static boolean debug;

    public static String[] convertIntArrayToStringArray (Integer[] integerArray, String[] stringArray ,int size){
        for (int i=0; i<size; i++){
            stringArray[i]=String.valueOf(integerArray[i]);
        }
        return stringArray;
    }

    public static boolean isDebug(){
        return debug;
    }

    public static void setDebug(boolean isDebug){
        debug=isDebug;
    }

    public static void debugLog(String log){
        System.out.println("[DEBUG] "+ log);
    }
}
