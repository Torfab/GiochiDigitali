package hashcode2020;

import java.util.Map;
import java.util.Set;

public class StrategieDiScoringLibrerie {
    private int numDaysToSignup;
    private int booksPerDay;
    private Map<Integer,Integer> books;


    public StrategieDiScoringLibrerie(int numDaysToSignup, int booksPerDay, Map<Integer,Integer> books){

        this.numDaysToSignup = numDaysToSignup;
        this.booksPerDay=booksPerDay;
        this.books=books;

    }



    public float getLibraryScore(int numDays, int signupStartDay, Set<Integer> books){
        //NOTE: potremmo passare il set dei libri rimasti per calcolare meglio la media
        int activityDays = numDays - signupStartDay - numDaysToSignup;

        if(activityDays <= 0){
            return 0f;
        }

        int maxBooksToSend = activityDays * booksPerDay;

        //return getPotentialScore(books, maxBooksToSend);
        return fastestToStartScore();



    }


    private float fastestToStartScore(){

        return 1f/(float) this.numDaysToSignup;
    }

    private Float getPotentialScore(Set<Integer> remainingBooks, int maxBooksToSend){
        if(maxBooksToSend <= 0f){
            return 0f;
        }

        int num = 0;
        Float sum = 0f;
        for(Map.Entry<Integer, Integer> book : books.entrySet()){
            if(remainingBooks.contains(book.getKey())){ //Se è ancora da mandare
                num++;
                sum += book.getValue();
                if(num == maxBooksToSend){ //Se ho raggiunto il num massimo di libri che potrò mandare
                    break;
                }
            }
        }

        return sum;
    }
}
