package hashcode2020;

import java.util.ArrayList;
import java.util.List;

public class SoluzioneGiorgio {

    private int[] slices;
    private int maxSlices;
    private List<String> typeOfPizza;

    public SoluzioneGiorgio(int[] slices, int maxSlices){
        this.slices=slices;
        this.maxSlices=maxSlices;
        this.typeOfPizza= new ArrayList();

    }

    public List<String> solution(){
        int index=0;
        somma(slices, maxSlices, index);

        System.out.println(typeOfPizza.toString());
        return typeOfPizza;
    }

    public boolean somma(int[] dataset, int maxSlices, int index) {
        if (index >= dataset.length) {
            return false;
        }
        if (cerca(dataset, maxSlices, index)) {
            return true;
        } else {
            if (dataset.length == 1) {
                return false;
            } else {
                if (somma(dataset, maxSlices - dataset[index], 1 + index)) {
                    //result.add(dataset[index]);
                    return true;
                } else {
                    return somma(dataset, maxSlices, 1 + index);
                }
            }
        }
    }

    private boolean cerca(int[] dataset, int maxSlices, int index) {
        for (int i = index; i < dataset.length; i++) {
            if (dataset[i] <= maxSlices) {
                this.typeOfPizza.add(String.valueOf(dataset[i]));
                return true;
            }
        }
        return false;
    }
}
