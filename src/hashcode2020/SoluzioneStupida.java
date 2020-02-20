package hashcode2020;

import java.util.ArrayList;
import java.util.List;

public class SoluzioneStupida {
    private int[] slices;
    private int maxSlices;

    public SoluzioneStupida(int[] slices, int maxSlices){
        this.slices=slices;
        this.maxSlices=maxSlices;

    }

    public List<String> solution() {

        List<String> typeOfPizza = new ArrayList();

        int maxTemp = 0;
        if (maxTemp + slices[0] < maxSlices) {
            maxTemp = maxTemp + slices[0];
            typeOfPizza.add("0");
        }

        for (int i = 0, j = 1; j < slices.length; i++, j++) {
            if (maxTemp + slices[j] < maxSlices) {
                maxTemp = maxTemp + slices[j];
                typeOfPizza.add(String.valueOf(j));
            } else {
                System.out.println("il numero che ci si avvicina è " + maxTemp + " doveva arrivare a " + maxSlices);
                break;
            }

        }

        return typeOfPizza;

    }

}
