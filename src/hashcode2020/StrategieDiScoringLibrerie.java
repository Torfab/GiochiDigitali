package hashcode2020;

import java.math.BigDecimal;
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



    public BigDecimal getLibraryScore(int numDays, int signupStartDay, Set<Integer> books){
        //NOTE: potremmo passare il set dei libri rimasti per calcolare meglio la media
        int activityDays = numDays - signupStartDay - numDaysToSignup;

        if(activityDays <= 0){

            if (Utility.getDebug()) {
                System.out.print("La libreria non fa in tempo a tirar fuori neanche un libro");
            }
            return BigDecimal.valueOf(0);
        }

        int maxBooksToSend = activityDays * booksPerDay;

        return oldGetPotentialScore(books, maxBooksToSend);
        //return fastestToStartScore();
        //return remainingDaysAfterFinishingBooks(books, maxBooksToSend);



    }

    private BigDecimal remainingDaysAfterFinishingBooks(Set<Integer> remainingBooks, int maxBooksToSend) {
        int num = 0;
        int sum = 0;

        for(Map.Entry<Integer, Integer> book : books.entrySet()){
            if(remainingBooks.contains(book.getKey())){ //Se è ancora da mandare
                num++;
                sum += book.getValue();

                if(num == maxBooksToSend){ //Se ho raggiunto il num massimo di libri che potrò mandare
                    break;
                }
            }
        }

        return BigDecimal.valueOf(0);
    }


    private BigDecimal fastestToStartScore(){
        BigDecimal temp= BigDecimal.valueOf(1);
         return temp.divide(BigDecimal.valueOf(this.numDaysToSignup));
    }

    private BigDecimal oldGetPotentialScore(Set<Integer> remainingBooks, int maxBooksToSend){
        if(maxBooksToSend <= 0f){
            return BigDecimal.valueOf(0);
        }

        int num = 0;
        int sum = 0;
        for(Map.Entry<Integer, Integer> book : books.entrySet()){
            if(remainingBooks.contains(book.getKey())){ //Se è ancora da mandare
                num++;
                sum += book.getValue();
                if(Utility.getDebug()) {
                    System.out.println("cosa sta contando? " + sum + " prova " + book); //BUGGONE!!!!!!! NON CONTROLLA SE IL LIBRO ESISTE
                }
                if(num == maxBooksToSend){ //Se ho raggiunto il num massimo di libri che potrò mandare
                    break;
                }
            }
        }

        return BigDecimal.valueOf(sum);
    }
}
