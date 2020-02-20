package hashcode2020;

import java.util.*;

public class LibraryScoreSolver {

    private Set<Library> libraries;
    private Set<Integer> books;
    private int numDays;
    private int remainingSignupDays=0;
    private List<Library> chosenLibraries;
    private Map<Integer, List<Integer>> sentBooks;

    public LibraryScoreSolver(Set<Library> libraries, Set<Integer> books, int numDays) {
        this.libraries = libraries;
        this.books = books;
        this.numDays = numDays;
        this.chosenLibraries = new ArrayList<>();
    }

    public List<String> solution() {

        /*
        * fino al giorno d:
        * 1) se non ci sono signup in corso: seleziono la libreria con lo score maggiore e ne attivo il signup
        * 2) per ogni libreria attivata: inserisco i nuovi libri da inviare (i primi booksPerDay della lista ordinata di libri per score [controllo libro già inviato] <-- NOTA: scegliamo bene  da dove mandare i libri duplicati)
        * */

        for(int day = 0; day < numDays; day++){
            if(remainingSignupDays--<=0){
                Library selectedLibrary = null;
                Float maxScore = 0f;
                Float currentScore;
                for(Library lib : libraries){
                    currentScore = lib.getLibraryScore(numDays, day);
                    if(currentScore > maxScore){
                        selectedLibrary = lib;
                        maxScore = currentScore;
                    }
                }

                if(selectedLibrary != null){
                    chosenLibraries.add(selectedLibrary);
                    libraries.remove(selectedLibrary);
                    remainingSignupDays=selectedLibrary.getNumDaysSignup();
                    sentBooks.put(selectedLibrary.getIdLibrary(), selectedLibrary.getBooksToSend(books, day, numDays));

                    for(Integer id : selectedLibrary.getBooksToSend(books,day, numDays)){
                        books.remove(id);
                    }
                }
            }
        }
        return null;
    }
}