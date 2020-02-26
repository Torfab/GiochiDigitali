package hashcode2020;

import javafx.util.Pair;

import java.util.*;

public class LibraryProblemSolver {


    private int numLibraries;                                       //Indica il numero di Librerie
    private int numDays;                                            //Indica il numero di giorni
    private Map<Integer, Integer> booksPool;                        //Indica tutti i libri nel sistema <id, score> verrà manipolato
    private Map<Integer, Integer> allBooks;                         //Indica tutti i libri nel sistema <id, score> non verrà manipolato
    private SortedSet<Library> libraries;                           //è il set ordinato di tutte le librerie
    private List<Library> chosenLibraries = new ArrayList<>();      //Indica le librerie scelte (per l'output)
    private Map<Integer, ArrayList<Integer>> sentBooks;             //Indica i libri inviati per ogni libreria (per l'output) <idLibreria, List of books>
    private StrategieDiScoringLibrerie strategieDiScoringLibrerie;

    private int result;

    public LibraryProblemSolver(List<String[]> content) {

        this.numLibraries = Integer.parseInt(content.get(0)[1]);     //Inserisce il numero delle Librerie dal primo elemento della prima riga
        this.numDays = Integer.parseInt(content.get(0)[2]);          //Inserisce il numero di giorni del sistema dal secondo elmento della prima riga
        this.libraries = new TreeSet<>();                            //Inizializza un TreeSet delle librerie, questo ci permette di ordinarle attraverso alla su ainterfaccia di comparazione
        this.sentBooks = new HashMap<>();                            //Inizializza i libri già inviati per evitare NullpointerException
        this.booksPool = new HashMap<>();                            //Inizializza tutti i libri per evitare null pointer Exception

        String[] allBooksId = content.get(1);
        this.booksPool = fillBooksPool(allBooksId);                      //riempie booksPool con tutti i libri, verrà manipolato
        this.allBooks = new HashMap<>(this.booksPool);                //clona booksPool non verrà manipolato

        this.strategieDiScoringLibrerie = new StrategieDiScoringLibrerie(booksPool);

        this.libraries = fillLibraries(content, numLibraries, allBooks);

        this.result = 0;

    }


    public List<String[]> grind() {
        for (int day = 0; day < numDays; ) {
            Library selectedLibrary = null;
            Pair<Float, Integer> selectedScore = new Pair<>(0f, 0);
            Pair<Float, Integer> currentScore;
            for (Library lib : libraries) {
                currentScore = lib.getLibraryScore(numDays, day);


                if (currentScore.getKey() > selectedScore.getKey()) {
                    selectedLibrary = lib;
                    selectedScore = currentScore;
                }

                if (currentScore.getKey().equals(selectedScore.getKey()) && currentScore.getKey() > 0f) {

                    if (selectedScore.getValue() < currentScore.getValue()) {
                        if (Utility.isDebug()) {
                            Utility.debugLog("La nuova libreria considerata è migliore di " + (currentScore.getValue() - selectedScore.getValue()));
                        }

                        selectedLibrary = lib;
                        selectedScore = currentScore;
                    }
                }
            }

            if (selectedLibrary != null) {
                System.out.println("Siamo a giorno " + day + " è stata scelta la libreria " + selectedLibrary.getIdLibrary() + " con lo score di " + selectedScore + " essa resterà in signup per " + selectedLibrary.getNumDaysSignup() + " giorni. Il suo bookscore sarà di " + selectedLibrary.getBooksOfLibraryScore(numDays, day));
                chosenLibraries.add(selectedLibrary);
                libraries.remove(selectedLibrary);
                day = day + selectedLibrary.getNumDaysSignup();
                ArrayList<Integer> booksToSend = selectedLibrary.getBooksToSend(booksPool.keySet(), day, numDays);
                sentBooks.put(selectedLibrary.getIdLibrary(), booksToSend);

                result += selectedScore.getValue();

                for (Integer id : booksToSend) {
                    booksPool.keySet().remove(id);
                }
            } else {
                System.out.println("Siamo a giorno " + day + " su " + numDays + " giorni e non è più possibile registrare le restanti librerie");
                break;
            }
        }
        System.out.println("il risultato libreria per libreria è "+ result);
        return resultConverter();

    }


    /**
     * il formato di ritorno è così formato:
     * prima riga del file -> un solo elemento, numero delle librerie scelte
     * poi iterativamente si ripetono due righe:
     * prima riga dell'iterazoine -> due elmenti: Id della libreria, numero di libri mandati dalla liibreria
     * seconda riga dell'iterazione -> array di id di books
     *
     * @return
     */
    public List<String[]> resultConverter() {

        int resultScore = 0;
        List<String[]> resultToWrite = new ArrayList<>();

        String[] firstLineOfFile = new String[1];
        firstLineOfFile[0] = String.valueOf(chosenLibraries.size());
        resultToWrite.add(firstLineOfFile);

        for (Library libreria : chosenLibraries) {
            int sizeOfLibreria = sentBooks.get(libreria.getIdLibrary()).size();
            String[] firsLineInIteration = new String[2];
            firsLineInIteration[0] = String.valueOf(libreria.getIdLibrary());         //id libreria
            firsLineInIteration[1] = String.valueOf(sizeOfLibreria);                  //size of libreria

            Integer[] secondLineInIterationAsInteger = new Integer[sizeOfLibreria];
            String[] secondLineInInterationAsString = new String[sizeOfLibreria];

            secondLineInIterationAsInteger = sentBooks.get(libreria.getIdLibrary()).toArray(secondLineInIterationAsInteger);

            for (Integer book : secondLineInIterationAsInteger) {
                resultScore += this.allBooks.get(book);
            }

            secondLineInInterationAsString = Utility.convertIntArrayToStringArray(secondLineInIterationAsInteger, secondLineInInterationAsString, sizeOfLibreria);

            resultToWrite.add(firsLineInIteration);
            resultToWrite.add(secondLineInInterationAsString);
        }
        System.out.println("Lo score del file è " + resultScore);
        return resultToWrite;
    }


    /**
     * Generate Libraries, add them their books and add them into a SortedSet by id.
     *
     * @param content      it expects 2*n rows, first with 3 elements "numBooks, numDaysSignup, booksPerDay", second with all books' id.
     * @param numLibraries it will stop looking for libraries when the loop reach numLibraries.
     * @param booksPool    it is needed to map book id with its score.
     * @return
     */
    private SortedSet<Library> fillLibraries(List<String[]> content, int numLibraries, Map<Integer, Integer> booksPool) {
        SortedSet<Library> libraries = new TreeSet<>();
        int rowIndex = 2;
        Library library;
        for (int index = 0; index < numLibraries; index++) {
            int numBooks = Integer.parseInt(content.get(rowIndex)[0]);
            int numDaysSignup = Integer.parseInt(content.get(rowIndex)[1]);
            int booksPerDay = Integer.parseInt(content.get(rowIndex)[2]);

            library = new Library(index, numBooks, numDaysSignup, booksPerDay, this.strategieDiScoringLibrerie);
            libraries.add(library);

            rowIndex++;

            String[] lineOfBooks = content.get(rowIndex);

            fillBooksInALibrary(library, lineOfBooks, booksPool);

            rowIndex++;

        }
        return libraries;
    }

    /**
     * Fill the map of a library with its books.
     *
     * @param library     is the library
     * @param lineOfBooks is the array of books id
     * @param booksPool   is the element to map id with its score
     */
    private void fillBooksInALibrary(Library library, String[] lineOfBooks, Map<Integer, Integer> booksPool) {
        for (String s : lineOfBooks) {
            int id = Integer.parseInt(s);
            library.addBook(id, booksPool.get(id));
        }
    }


    /**
     * ill the map of all the books
     *
     * @param allBooksId it expects an array of books id
     * @return
     */
    private Map<Integer, Integer> fillBooksPool(String[] allBooksId) {
        Map<Integer, Integer> allBooks = new HashMap<>();
        int index = 0;
        for (String s : allBooksId) {
            int score = Integer.parseInt(s);
            allBooks.put(index, score);
            index++;
        }
        return allBooks;
    }
}
