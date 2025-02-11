package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;

public class ChordMethod 
{
    public static void main(String[] args) 
    {
        // Initial guess for x_0 (use BigDecimal for high precision)
        BigDecimal x0 = new BigDecimal("0.1");
        // Number of iterations (10 iterations in this case)
        int iterations = 10;

        // Set the precision for BigDecimal (set a reasonably high precision)
        // 100 decimal places of precision
        MathContext mc = new MathContext(100);  
        
        // Create a file to store the output
        File outputFile = new File("chord_method_output.txt");

        try (PrintWriter writer = new PrintWriter(outputFile)) 
        {
            // Print the initial guess to the output file
            writer.printf("x(0) = %.100f%n", x0);

            // Perform Chord's method iterations and print each step starting from k = 1
            BigDecimal x = x0;
            for (int k = 1; k <= iterations; k++) 
            { 	
            	// Change the loop to go from k=1 to k=10
                // Compute the next value using Chord's method
                x = chordIteration(x, x0, mc);
                // Print the result of the current iteration to the file
                // Print iteration number starting from 1
                writer.printf("x(%d) = %.100f%n", k, x); 
            }

            // Notify that the results have been saved
            System.out.println("Results have been saved to chord_method_output.txt");

        } catch (FileNotFoundException e) 
        {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Chord iteration formula to calculate next value
    public static BigDecimal chordIteration(BigDecimal x, BigDecimal x0, MathContext mc) 
    {
        // g(x) = x^3 - 5
        BigDecimal f_x = x.pow(3, mc).subtract(new BigDecimal("5"));
        // g'(x0) = 3 * x0^2
        BigDecimal f_prime_x0 = x0.pow(2, mc).multiply(new BigDecimal("3"));
        
        // Chord iteration formula: x(k+1) = x(k) - (g(x) / g(x0))
        return x.subtract(f_x.divide(f_prime_x0, mc));
    }
}
