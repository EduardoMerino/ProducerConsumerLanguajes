
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Merino
 */
public class Operation {
    
    public String symbol;
    public int operator1;
    public int operator2;
    
    /* Constructor
     * No parameters
     * Creates an scheme format operation with numbers between
        the maximum of 100 and a minimum of 1
     */
    public Operation(){
        Random random = new Random();
        this.setRandomSymbol();
        this.operator1 = random.nextInt(100 - 1 + 1) + 1;
        this.operator2 = random.nextInt(100 - 1 + 1) + 1;
    }
    
    /* Constructor
     * Max and Min parameters
     * Creates an scheme format operation with numbers between
        the maximum and minimum determined by the user
     */
    public Operation(int max, int min){
        Random random = new Random();
        this.setRandomSymbol();
        this.operator1 = random.nextInt(max - min + 1) + min;
        this.operator2 = random.nextInt(max - min + 1) + min;
    }
    
    /*
     *
     */
    private void setRandomSymbol(){
        //Random random = new Random(System.currentTimeMillis());
        int my_random = ThreadLocalRandom.current().nextInt(1, 4 + 1);
        switch (my_random){
            case 1:
                this.symbol = "+";
                break;
            case 2:
                this.symbol = "-";
                break;
            case 3:
                this.symbol = "*";
                break;
            case 4:
                this.symbol = "/";
                break;
            default:
                this.symbol = "+";
        }
    }
    
    public double resolve(){
        double result = 0.0;
        switch(this.symbol){
            case "+":
                result = this.operator1 + this.operator2;
                break;
            case "-":
                result = this.operator1 - this.operator2;
                break;
            case "*":
                result = this.operator1 * this.operator2;
                break;
            case "/":
                result = this.operator1 / this.operator2;
                break;
        }
        return result;
    }
    
    @Override
    public String toString(){
        return ("(" + this.symbol + " " + this.operator1 + " " + this.operator2 + ")");
    }
    
    
}
