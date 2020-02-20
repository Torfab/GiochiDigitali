package hashcode2020;

import java.util.ArrayList;
import java.util.List;

public class SoluzioneStackOverflow {

    private int[] slices;
    private int maxSlices;
    private List<String> typeOfPizza;

    public SoluzioneStackOverflow(int[] slices, int maxSlices){
        this.slices=slices;
        this.maxSlices=maxSlices;
         this.typeOfPizza= new ArrayList();
    }

    public List<String> solution(){

        int lol=0;
        lol=checkAlbero(-1,0, slices, maxSlices);
        System.out.println(lol);


     return typeOfPizza;
    }

        private int checkAlbero(int indexRaggiunto, int valoreRaggiunto, int[] slices, int maxSlices){
        int massimoTraIValoriTrovati=valoreRaggiunto;
        int valoreTrovato=0;
        for (int i=indexRaggiunto+1; i<slices.length;i++){
            if (valoreRaggiunto+slices[i]<maxSlices){

                typeOfPizza.add(String.valueOf(i));

                valoreTrovato=checkAlbero(i,valoreRaggiunto+slices[i], slices, maxSlices);
                System.out.println(valoreTrovato);

                if (valoreTrovato>valoreRaggiunto) {
                    if (valoreTrovato>massimoTraIValoriTrovati){
                        massimoTraIValoriTrovati=valoreTrovato;
                    }
                }
            }else{
                break;
            }
        }return massimoTraIValoriTrovati;
    }
}
