package hashcode2020;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Library implements Comparable<Library>{
    private int idLibrary;
    private int numBooks;
    private int numDaysSignup;
    private int booksPerDay;
    private Map<Integer,Integer> remainingBooks;
    private StrategieDiScoringLibrerie strategieDiScoringLibrerie;

    public Library (int idLibrary, int numBooks, int numDaysSignup, int booksPerDay, StrategieDiScoringLibrerie strategieDiScoringLibrerie) {
        this.idLibrary = idLibrary;
        this.numBooks = numBooks;
        this.numDaysSignup = numDaysSignup;
        this.booksPerDay = booksPerDay;
        remainingBooks = new HashMap<>();
        this.strategieDiScoringLibrerie=strategieDiScoringLibrerie;
    }

    public int getNumDaysSignup() {
        return numDaysSignup;
    }

    public void addBook(int id, int score){
        this.remainingBooks.put(id,score);
    }

    @Override
    public String toString() {
        return "Library{" +
                "numBooks=" + numBooks +
                ", numDaysSignup=" + numDaysSignup +
                ", booksPerDay=" + booksPerDay +
                ", books=" + remainingBooks +
                '}';
    }

    @Override
    public int compareTo(Library other) {
        return Integer.compare(this.idLibrary, other.idLibrary);
    }

    public float getLibraryScore(int numDays, int signupStartDay){
        float libraryScore=strategieDiScoringLibrerie.getLibraryScore(numDays, signupStartDay, this.numDaysSignup, this.booksPerDay, this.remainingBooks.keySet());
        if(Utility.isDebug())
            Utility.debugLog("Sto valutando la libreria "+idLibrary+" il suo score è "+ libraryScore + ". Possiede "+remainingBooks.size()+" libri. I libri sono "+remainingBooks+ " lo score totale è "+getBooksOfLibraryScore(numDays,numDaysSignup));
        return libraryScore;
    }

    public int getBooksOfLibraryScore(int numDays, int signupStartDay){

        int booksOfLibraryScore=strategieDiScoringLibrerie.getBooksOfLibraryScore(numDays, signupStartDay, this.numDaysSignup, this.booksPerDay, remainingBooks.keySet());

        if(Utility.isDebug()){
            Utility.debugLog("sto valutando i libri della libreria "+idLibrary+" il loro score è "+ booksOfLibraryScore);
        }
        return booksOfLibraryScore;
    }



    public int getIdLibrary() {
        return idLibrary;
    }


    public ArrayList<Integer> getBooksToSend(Set<Integer> remainingBooks, int activationDay, int totalDay){
        int activityDay = totalDay - activationDay- numDaysSignup;
        int numSent = 0;
        int totalBooksToSend = activityDay * booksPerDay;
        ArrayList<Integer> ret =  new ArrayList<>();

        Map<Integer, Integer> sorted = this.remainingBooks.entrySet().stream().sorted((integerIntegerEntry, t1) -> t1.getValue().compareTo(integerIntegerEntry.getValue())).collect(
                toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));

        for(Integer i : sorted.keySet()){
            if(remainingBooks.contains(i)){
                ret.add(i);
                numSent++;
                if(numSent >= totalBooksToSend){
                    break;
                }
            }
        }
        return ret;
    }
}

//a pari score, prendere le librerie con più libri e se finisce prima di D prendere quello con meno signup time