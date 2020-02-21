package hashcode2020;

import java.lang.reflect.Array;
import java.util.*;

public class MathSorcerer {


    private int numBooks;
    private int numLibraries;
    private int numDays;
    Map<Integer, Integer> books;
    Set<Library> libararies;
    List<String> result;


    private LibraryScoreSolver soluzioneScore;

    public MathSorcerer(List<String[]> content) {

        this.numBooks = Integer.parseInt(content.get(0)[0]);
        this.numLibraries = Integer.parseInt(content.get(0)[1]);
        this.numDays = Integer.parseInt(content.get(0)[2]);
        this.libararies = new HashSet<>();

        this.books = new HashMap<>();
        int index = 0;
        for (String s : content.get(1)) {
            int score = Integer.parseInt(s);
            books.put(index, score);
            index++;
        }
        int rowIndex = 2;
        Library library;
        for(int i = 0; i < numLibraries; i++){
            library = new Library(i, Float.parseFloat(content.get(rowIndex)[0]),Integer.parseInt(content.get(rowIndex)[1]),Integer.parseInt(content.get(rowIndex)[2]));
            rowIndex++;
            for(String s : content.get(rowIndex)){
                int id = Integer.parseInt(s);
                library.addBook(id, books.get(id));
            }
            rowIndex++;

            libararies.add(library);
        }

        soluzioneScore = new LibraryScoreSolver(libararies, books.keySet(), numDays);
        result = new ArrayList<>();
    }


    public List<String[]> grind() {

        result = soluzioneScore.solution();

        return resultConverter(result);

    }


    public List<String[]> resultConverter(List<String> typeOfPizza) {
        List<String[]> result = new ArrayList<>();

        String[] head = new String[1];
        head[0]=String.valueOf(soluzioneScore.getChosenLibraries().size());
        result.add(head);
        for (Library libreria : soluzioneScore.getChosenLibraries()) {
            String[] ring1=new String[2];
            ring1[0]=String.valueOf(libreria.getIdLibrary());
            ring1[1]=String.valueOf(soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).size());
            Integer[] ring2 = new Integer[soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).size()];
            String[] ring2vero = new String[soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).size()];
            ring2=soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).toArray(ring2);
            for (int i=0; i<soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).size(); i++){
                ring2vero[i]=String.valueOf(ring2[i]);
            }
            result.add(ring1);
            result.add(ring2vero);
        }
        return result;
    }

}
