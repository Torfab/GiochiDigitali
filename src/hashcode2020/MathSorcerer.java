package hashcode2020;

import java.util.ArrayList;
import java.util.List;

public class MathSorcerer {


    private int maxSlices;
    private int numberOfTypesOfPizza;
    private List<String> typeOfPizza;
    int[] slices;
    List<String> result;

    private SoluzioneStupida soluzioneStupida;
    private SoluzioneStackOverflow soluzioneStackOverflow;
    private SoluzioneGiorgio soluzioneGiorgio;

    public MathSorcerer(List<String[]> content) {
        maxSlices = Integer.parseInt(content.get(0)[0]);
        numberOfTypesOfPizza = Integer.parseInt(content.get(0)[1]);
        typeOfPizza = new ArrayList();
        slices = new int[content.get(1).length];
        for (int j = 0; j < content.get(1).length; j++) {
            slices[j] = Integer.parseInt(content.get(1)[j]);
        }
        result = new ArrayList<>();
        soluzioneStupida = new SoluzioneStupida(slices, maxSlices);
        soluzioneStackOverflow = new SoluzioneStackOverflow(slices, maxSlices);
        soluzioneGiorgio =  new SoluzioneGiorgio(slices,maxSlices);
    }


    public List<String[]> grind() {

        //result = soluzioneStupida.solution();
        //result = soluzioneStackOverflow.solution();
        result = soluzioneGiorgio.solution();

        return resultConverter(result);

    }



    public List<String[]> resultConverter(List<String> typeOfPizza) {
        List<String[]> result = new ArrayList<>();

        String[] head = new String[1];
        String[] tail = new String[typeOfPizza.size()];
        head[0] = String.valueOf(typeOfPizza.size());

        int i = 0;
        for (
                String pizza : typeOfPizza) {
            tail[i] = pizza;
            i++;
        }
        result.add(head);
        result.add(tail);
        return result;
    }

}
