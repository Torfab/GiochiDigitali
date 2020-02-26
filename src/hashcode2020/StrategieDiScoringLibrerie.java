package hashcode2020;

import javafx.util.Pair;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StrategieDiScoringLibrerie {

    private Map<Integer, Integer> books;


    public StrategieDiScoringLibrerie(Map<Integer, Integer> books) {

        this.books = books;

    }


    public Pair<Float, Float> getLibraryScore(int numDays, int signupStartDay, int numDaysToSignup, int booksPerDay, Set<Integer> remainingBooks) {



        float libraryScore = fastestToStartScore(numDaysToSignup);
        float bookValue = getBooksOfLibraryScore(numDays, signupStartDay, numDaysToSignup, booksPerDay, remainingBooks);

        if(bookValue==0f){
            return new Pair<>(0f,0f);
        }

        return new Pair<>(libraryScore, bookValue);

    }

    private float fastestToStartScore(int numDaysToSignup) {
        return 1f / (float) numDaysToSignup;
    }


    public float getBooksOfLibraryScore(int numDays, int signupStartDay, int numDaysToSignup, int booksPerDay, Set<Integer> remainingBooks) {
        int activityDays = numDays - signupStartDay - numDaysToSignup;
        if (activityDays <= 0) {
            if (Utility.isDebug()) {
                System.out.print("La libreria non fa in tempo a tirar fuori neanche un libro");
            }
            return 0f;
        }
        int maxBooksToSend = activityDays * booksPerDay;
        if (maxBooksToSend <= 0f) {
            return 0f;
        }


        Set<Integer> booksToRemove=new HashSet<>();
        Float sum = sumOfBooksScore(remainingBooks, maxBooksToSend, booksToRemove);
        removeChosenBooks(remainingBooks, booksToRemove);

        return sum;
    }

    private Float sumOfBooksScore(Set<Integer> remainingBooks, int maxBooksToSend, Set<Integer> booksToRemove) {
        int num = 0;
        Float sum = 0f;


        for (Integer remainingBook : remainingBooks) {
            if (books.containsKey(remainingBook)) { //Se è ancora da mandare
                num++;
                sum += books.get(remainingBook);
                if (Utility.isDebug()) {
                    Utility.debugLog("La somma parziale dei libri è " + sum + ". I libri che sto prendendo in considerazione sono: " + remainingBook);
                }
                if (num == maxBooksToSend) { //Se ho raggiunto il num massimo di libri che potrò mandare
                    break;
                }
            } else {
                booksToRemove.add(remainingBook);
            }
        }
        return sum;
    }

    private void removeChosenBooks(Set<Integer> remainingBooks, Set<Integer> booksToRemove) {
        if (booksToRemove!=null) {
            for (Integer bookToRemove : booksToRemove) {
                remainingBooks.remove(bookToRemove);
            }
        }
    }


}

//NOTE: potremmo passare il set dei libri rimasti per calcolare meglio la media
//return remainingDaysAfterFinishingBooks(books, maxBooksToSend);

//    private float oldGetPotentialScore(Set<Integer> remainingBooks, int maxBooksToSend) {
//
//    }

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