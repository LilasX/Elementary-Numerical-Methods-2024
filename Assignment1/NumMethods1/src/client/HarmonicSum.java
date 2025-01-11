package client;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class HarmonicSum
{

	// Harmonic Sum using Single Precision
    private float harmonicSumSingle(int N) 
    {
    	// the sum starts at zero
        float sum = 0.0f;
        
        // Calculate the accumulated sum for N values
        for (int k = 1; k <= N; k++) 
        {
            sum += 1.0f / k;
        }
        
        // Return the accumulated sum
        return sum;
    }

    // Harmonic Sum using Double Precision
    private double harmonicSumDouble(int N) 
    {
    	// the sum starts at zero
        double sum = 0.0;
        
        // Calculate the accumulated sum for N values
        for (int k = 1; k <= N; k++) 
        {
        	sum += 1.0 / k;
        }
        
        // Return the accumulated sum
        return sum;
    }

    // Create the Dataset for both Harmonic Sum (Single and Double Precision)
    private DefaultCategoryDataset createDataset(int N)
    {
    	// Implement the interface for a dataset to store the values of our Harmonic sums
    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	
    	// Calculate the current sum for Single and Double Precision
    	for(int i = 1; i <= N; i++)
    	{
    		// Calculate the sum for each 10^N
    		int exp = (int) Math.pow(10, i);
    		
    		// Transform the sum into a float and a double value respectively
    		float hSingle = harmonicSumSingle(exp);
    		double hDouble = harmonicSumDouble(exp);
    		
    		// Store the values obtained into the dataset
    		dataset.addValue(hSingle, "Harmonic Sum (Single Precision)", Integer.toString(i));
    		dataset.addValue(hDouble, "Harmonic Sum (Double Precision)", Integer.toString(i));
    		
    		// Print results, and the difference between Single and Double Precision
    		System.out.printf("N: %d, Single: %.10f, Double: %.10f, Difference: %.10f%n", i, hSingle, hDouble, Math.abs(hSingle - hDouble));
    	}
    	
    	// Return the dataset containing our sum results
    	return dataset;
    }
    
    // Generate the Graph from the Dataset in a window output
    private void createGraph(int N)
    {
    	// create a line chart to draw the results of our sums in Single and Double precision respectively
    	JFreeChart lineGraph = ChartFactory.createLineChart("Harmonic Sum Comparison", "10^N values", "Sum", createDataset(N));
    	
    	// To make the dots (results of the Harmonic Sum function for 10^N) appear by default in the graph
    	CategoryPlot plot = lineGraph.getCategoryPlot();
    	// Show lines and shapes (dots)
        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true); 
        plot.setRenderer(renderer);
    	
        // Setting dimensions of the window and enable mouse wheel for Zoom-in and Zoom-out
    	ChartPanel sizeGraph = new ChartPanel(lineGraph);
    	sizeGraph.setPreferredSize(new Dimension(800,600));
    	sizeGraph.setMouseWheelEnabled(true);
    	
    	// Some functionalities for the window output to work well
    	JFrame windowGraph = new JFrame("Harmonic Sum Graph");
    	windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	windowGraph.add(sizeGraph);
    	windowGraph.pack();
    	windowGraph.setLocationRelativeTo(null);
    	windowGraph.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
    	// Generate the Graph from Harmonic Sum methods and draw the Graph
    	SwingUtilities.invokeLater(() -> {int N = 9; new HarmonicSum().createGraph(N);});
    }
    
}

