/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencyconverter;

import java.util.Scanner;

/**
 *
 * @author eivind
 */
public class CurrencyConverter {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        double inputValue = 0.00;
        double outputValue = 0.00;
        String inputCurrency;
        String outputCurrency;
        boolean isDouble;
        String validString = "USD NOK EUR";

        
        //Variabler til konvertering av valuta:

        //Fra USD til EUR , USD til NOK 
        double UtoE = 0.803;
        double UtoN = 7.690;

        //Fra EUR til USD, EUR til NOK 
        double EtoU = 1.245;
        double EtoN = 9.574;

        //Fra NOK til USD, NOK til EUR 
        double NtoU = 0.129;
        double NtoE = 0.104;

        
        
        //Velger verdi på valutaen og sjekker om verdien er et tall.
        do {
            System.out.println("Enter value:" + " ");
            if (s.hasNextDouble()) {
                inputValue = s.nextDouble();
                isDouble = true;
            } else {
                System.out.println("Please enter a valid number.");
                isDouble = false;
                s.next();
            }

        } while (!(isDouble));

        
        
        //Valg av type valuta og sjekker om input er et av alternativene nedenfor.
        s.nextLine();
        System.out.println("Choose between: USD, EUR or NOK: ");
        inputCurrency = s.nextLine();

        while (!(validString.indexOf(inputCurrency) != -1)) {
            System.out.println("Please enter a valid option:");
            inputCurrency = s.nextLine();
        }

        //Valg av output-valuta og sjekker om input er et av alternativene. 
        System.out.println("What currency do you want to convert " + inputCurrency + " to?: USD, EUR or NOK: ");
        outputCurrency = s.nextLine();

        while (!(validString.indexOf(outputCurrency) != -1)) {
            System.out.println("Please enter a valid option:");
            outputCurrency = s.nextLine();
        }

        
        
        //If-setninger som gir riktig output basert på input.
        
        if ((inputCurrency.equals("USD")) && (outputCurrency.equals("EUR"))) {
            outputValue = inputValue * UtoE;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        } else if ((inputCurrency.equals("USD")) && (outputCurrency.equals("NOK"))) {
            outputValue = inputValue * UtoN;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        } else if ((inputCurrency.equals("EUR")) && (outputCurrency.equals("USD"))) {
            outputValue = inputValue * EtoU;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        } else if ((inputCurrency.equals("EUR")) && (outputCurrency.equals("NOK"))) {
            outputValue = inputValue * EtoN;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        } else if ((inputCurrency.equals("NOK")) && (outputCurrency.equals("USD"))) {
            outputValue = inputValue * NtoU;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        } else if ((inputCurrency.equals("NOK")) && (outputCurrency.equals("EUR"))) {
            outputValue = inputValue * NtoE;
            System.out.println(inputValue + " " + inputCurrency + " = " + outputValue + " " + outputCurrency);

        }

        s.close();

    }
}
