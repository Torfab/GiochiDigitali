package hashcode2020;

import java.lang.reflect.Array;
import java.util.*;

public class MathSorcerer {


    private int numBooks;
    private int numLibraries;
    private int numDays;
    private Map<Integer, Integer> books;
    private Set<Library> libararies;
    private List<String> result;
    private int resultScore;


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

        soluzioneScore.solution();
        return resultConverter();

    }


    public List<String[]> resultConverter() {


        List<String[]> resultToWrite = new ArrayList<>();
        String[] head = new String[1];
        head[0]=String.valueOf(soluzioneScore.getChosenLibraries().size());
        resultToWrite.add(head);
        for (Library libreria : soluzioneScore.getChosenLibraries()) {
            int sizeOfLibreria=soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).size();
            String[] ring1=new String[2];
            ring1[0]=String.valueOf(libreria.getIdLibrary());  //primo elemento stringa 1 libreria
            ring1[1]=String.valueOf(sizeOfLibreria); //secondo elemento stringa 1 libreria

            Integer[] ring2asInteger = new Integer[sizeOfLibreria];
            String[] ring2asString = new String[sizeOfLibreria];

            ring2asInteger=soluzioneScore.getSentBooks().get(libreria.getIdLibrary()).toArray(ring2asInteger);

            ring2asString= Utility.convertIntArrayToStringArray(ring2asInteger, ring2asString, sizeOfLibreria);

            resultToWrite.add(ring1);
            resultToWrite.add(ring2asString);
        }
        return resultToWrite;
    }

}
