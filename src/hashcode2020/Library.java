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

    public Library(int idLibrary, Float numBooks, int numDaysSignup, int booksPerDay) {
        this.idLibrary = idLibrary;
        this.numBooks = numBooks;
        this.numDaysSignup = numDaysSignup;
        this.booksPerDay = booksPerDay;
        books = new HashMap<>();
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

    public float getLibraryScore(int numDays, int signupStartDay){
        //NOTE: potremmo passare il set dei libri rimasti per calcolare meglio la media
        int activityDays = numDays - signupStartDay - numDaysSignup;
        Float mediumScore = getMediumScore(); //TODO: si può fare di meglio (ad esempio considera i libri già inviati)

        return activityDays * mediumScore * booksPerDay;
    }

    private Float getMediumScore(){
        if(numBooks == 0f){
            return 0f;
        }

        Float sum = 0f;
        for(int score : books.values()){
            sum += score;
        }

        return sum/numBooks;
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

        Map<Integer, Integer> sorted = this.books
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
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
