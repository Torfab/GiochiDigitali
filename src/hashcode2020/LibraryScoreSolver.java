package hashcode2020;

import java.util.*;

public class LibraryScoreSolver {

    private Set<Library> libraries;
    private Set<Integer> books;
    private int numDays;
    private int remainingSignupDays=1;
    private List<Library> chosenLibraries;
    private Map<Integer, ArrayList<Integer>> sentBooks;

    public LibraryScoreSolver(Set<Library> libraries, Set<Integer> books, int numDays) {
        this.libraries = libraries;
        this.books = books;
        this.numDays = numDays;
        this.chosenLibraries = new ArrayList<>();
        this.sentBooks = new HashMap<>();
    }

    public List<Library> getChosenLibraries() {
        return chosenLibraries;
    }

    public Map<Integer, ArrayList<Integer>> getSentBooks() {
        return sentBooks;
    }

    public List<String> solution() {

        /*
        * fino al giorno d:
        * 1) se non ci sono signup in corso: seleziono la libreria con lo score maggiore e ne attivo il signup
        * 2) per ogni libreria attivata: inserisco i nuovi libri da inviare (i primi booksPerDay della lista ordinata di libri per score [controllo libro già inviato] <-- NOTA: scegliamo bene  da dove mandare i libri duplicati)
        * */

        /**
         * NOTA: dopo aver selezionato la libreria con lo score maggiore potremmo valutare il numero di giorni di inattività della libreria prima del termine di numDays, e
         * cercare eventualmente la libreria migliore che abbia un numero di giorni di attivazione minore di questo numero di giorni di inattività, ed eventualmente poi inserire entrambe le librerie.
         */

        for(int day = 0; day < numDays; day++){
            remainingSignupDays--;
            if (Utility.getDebug()) {
                if (remainingSignupDays < 0) {
                    System.out.println("Siamo a giorno " + day + ". Sono già state scelte tutte le librerie possibili");
                } else {
                    System.out.println("Siamo a giorno " + day + ". Bisogna aspettare altri " + remainingSignupDays + " giorni per poter caricare una nuova libreria");
                }
            }
            if(remainingSignupDays==0){
                Library selectedLibrary = null;
                float maxScore = 0f;
                float currentScore;
                for(Library lib : libraries){
                    currentScore = lib.getLibraryScore(numDays, day, books);  //qui conta
                    if(currentScore > maxScore){
                        selectedLibrary = lib;
                        maxScore = currentScore;
                    }
                }

                if(selectedLibrary != null){
                    System.out.println("Siamo a giorno "+day+" è stata scelta la libreria "+selectedLibrary.getIdLibrary()+" con lo score di "+maxScore+" essa resterà in signup per "+selectedLibrary.getNumDaysSignup()+" giorni");
                    chosenLibraries.add(selectedLibrary);
                    libraries.remove(selectedLibrary);
                    remainingSignupDays=selectedLibrary.getNumDaysSignup();
                    ArrayList<Integer> mbareTiPregoOgniCicloSoSordi = selectedLibrary.getBooksToSend(books, day, numDays);
                    sentBooks.put(selectedLibrary.getIdLibrary(), mbareTiPregoOgniCicloSoSordi);

                    for(Integer id : mbareTiPregoOgniCicloSoSordi){
                        books.remove(id);
                    }
                }
            }

        }
        return null;
    }
}
