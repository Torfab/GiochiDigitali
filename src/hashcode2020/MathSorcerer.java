package hashcode2020;

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
            library = new Library(Float.parseFloat(content.get(rowIndex)[0]),Integer.parseInt(content.get(rowIndex)[1]),Integer.parseInt(content.get(rowIndex)[2]));
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
        /*List<String[]> result = new ArrayList<>();

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
        return result;*/
        return null;
    }

}
