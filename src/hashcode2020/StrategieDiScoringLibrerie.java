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

            if (Utility.isDebug()) {
                System.out.print("La libreria non fa in tempo a tirar fuori neanche un libro");
            }
            return 0f;
        }

        int maxBooksToSend = activityDays * booksPerDay;

        //return oldGetPotentialScore(books, maxBooksToSend);
        return fastestToStartScore();
        //return remainingDaysAfterFinishingBooks(books, maxBooksToSend);
    }

    public int getBooksOfLibraryScore(int numDays, int signupStartDay, Set<Integer> remainingBooks){
        int activityDays = numDays - signupStartDay - numDaysToSignup;
        int maxBooksToSend = activityDays * booksPerDay;
        return (int) oldGetPotentialScore(remainingBooks, maxBooksToSend);
    }


//    private float remainingDaysAfterFinishingBooks(Set<Integer> remainingBooks, int maxBooksToSend) {
//        int num = 0;
//        int sum = 0;
//
//        for(Map.Entry<Integer, Integer> book : books.entrySet()){
//            if(remainingBooks.contains(book.getKey())){ //Se è ancora da mandare
//                num++;
//                sum += book.getValue();
//
//                if(num == maxBooksToSend){ //Se ho raggiunto il num massimo di libri che potrò mandare
//                    break;
//                }
//            }
//        }
//
//        return 0;
//    }


    private float fastestToStartScore(){

        return 1f/(float) this.numDaysToSignup;
    }

    private float oldGetPotentialScore(Set<Integer> remainingBooks, int maxBooksToSend){
        if(maxBooksToSend <= 0f){
            return 0f;
        }

        int num = 0;
        Float sum = 0f;
        for(Map.Entry<Integer, Integer> book : books.entrySet()){
            if(remainingBooks.contains(book.getKey())){ //Se è ancora da mandare
                num++;
                sum += book.getValue();
                if(true||Utility.isDebug()) {
                    Utility.debugLog("cosa sta contando? " + sum + " prova " + book);
                }
                if(num == maxBooksToSend){ //Se ho raggiunto il num massimo di libri che potrò mandare
                    break;
                }
            }else{
                System.out.println("prima " +remainingBooks);
                remainingBooks.remove(book);
                System.out.println("dopo "+ remainingBooks);
            }
        }

        return sum;
    }
}
