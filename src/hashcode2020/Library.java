package hashcode2020;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class Library {
    private int idLibrary;
    private Float numBooks;
    private int numDaysSignup;
    private int booksPerDay;
    private Map<Integer,Integer> books;
    private StrategieDiScoringLibrerie strategieDiScoringLibrerie;

    public Library(int idLibrary, Float numBooks, int numDaysSignup, int booksPerDay) {
        this.idLibrary = idLibrary;
        this.numBooks = numBooks;
        this.numDaysSignup = numDaysSignup;
        this.booksPerDay = booksPerDay;
        books = new HashMap<>();
        strategieDiScoringLibrerie=new StrategieDiScoringLibrerie(numDaysSignup, booksPerDay, books);
    }

    public Float getNumBooks() {
        return numBooks;
    }

    public void setNumBooks(Float numBooks) {
        this.numBooks = numBooks;
    }

    public int getNumDaysSignup() {
        return numDaysSignup;
    }

    public void setNumDaysSignup(int numDaysSignup) {
        this.numDaysSignup = numDaysSignup;
    }

    public int getBooksPerDay() {
        return booksPerDay;
    }

    public void setBooksPerDay(int booksPerDay) {
        this.booksPerDay = booksPerDay;
    }

    public void addBook(int id, int score){
        this.books.put(id,score);
    }

    @Override
    public String toString() {
        return "Library{" +
                "numBooks=" + numBooks +
                ", numDaysSignup=" + numDaysSignup +
                ", booksPerDay=" + booksPerDay +
                ", books=" + books +
                '}';
    }

    public float getLibraryScore(int numDays, int signupStartDay, Set<Integer> books){
        float libraryScore=strategieDiScoringLibrerie.getLibraryScore(numDays, signupStartDay, books);
        if(Utility.getDebug()){
            System.out.println("sto valutando la libreria "+idLibrary+" il suo score è "+ libraryScore);
        }
        return libraryScore;
    }



    public int getIdLibrary() {
        return idLibrary;
    }

    public void setIdLibrary(int idLibrary) {
        this.idLibrary = idLibrary;
    }

    public Map<Integer, Integer> getBooks() {
        return books;
    }

    public void setBooks(Map<Integer, Integer> books) {
        this.books = books;
    }

    public ArrayList<Integer> getBooksToSend(Set<Integer> remainingBooks, int activationDay, int totalDay){
        int activityDay = totalDay - activationDay- numDaysSignup;
        int numSent = 0;
        int totalBooksToSend = activityDay * booksPerDay;
        ArrayList<Integer> ret =  new ArrayList<Integer>();

        Map<Integer, Integer> sorted = this.books.entrySet().stream().sorted((integerIntegerEntry, t1) -> t1.getValue().compareTo(integerIntegerEntry.getValue())).collect(
                toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                        LinkedHashMap::new));
        //BUG CONTROLLA IL VETTORE SBAGLIATO E QUI I BOOK NON VENGONO MAI RIMOSSI
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