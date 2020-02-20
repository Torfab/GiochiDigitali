package hashcode2020;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

public class Library {
    private Float numBooks;
    private int numDaysSignup;
    private int booksPerDay;
    private Map<Integer,Integer> books;

    public Library(Float numBooks, int numDaysSignup, int booksPerDay) {
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
        Float mediumScore = getMediumScore(); //TODO: si può fare di meglio

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

}
