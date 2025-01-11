package client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SequenceC 
{

	// Function f(x) = cx(1-x)
	public static double function(double x, double c)
	{
		return c * x * (1-x);
	}
	
	// Generate the sequence for N iterations
	public static List<Double> generateSequence(double x_0, int N, double c)
	{
		// Print the c value
		System.out.printf("Sequence for c = %.2f%n", c);
		
		// Store the sequence in an ArrayList
		List<Double> seq = new ArrayList<>();
		// Start sequence from x^(0)
		seq.add(x_0);
		
		// Print the first term of the sequence and its value
		System.out.printf("x^(%d) = %.10f%n", 0, x_0);
		
		// Calculate the value of each term and add it into our sequence
		for(int k = 0; k < N; k++)
		{
			// Calculate the value of the current term
			double x_k = function(seq.get(k), c);
			// Add the value of the calculated term into our sequence
			seq.add(x_k);
			
			// Print the current iteration and each term of the sequence along with its value
			System.out.printf("x^(%d) = %.10f%n", k + 1, x_k);
		}
		
		// Return the sequence in terms of N values
		return seq;
	}
	
	// Plot the sequences on a graph
	public static void createGraph(List<List<Double>> sequences, int N, double[] cValues)
	{
		// Create a dataset
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		// Add our sequences into the dataset
		for(int i = 0; i < sequences.size(); i++)
		{
			// Create an empty sequence in the form of (x, y)
			XYSeries series = new XYSeries("c = " + cValues[i]);
			
			// Add our results into the empty sequence created
			for(int k = 0; k <= N; k++)
			{
				series.add(k, sequences.get(i).get(k));
			}
			
			dataset.addSeries(series);
		}
		
		// Create a line graph based on our dataset
		JFreeChart lineGraph = ChartFactory.createXYLineChart("Sequence x^(k) using f(x) = cx(1-x)", "N values", "x^(N) values", dataset);
		
		// To make the dots (results of the sequence for N) appear by default in the graph
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		for(int i = 0; i < sequences.size(); i++)
		{
			renderer.setSeriesLinesVisible(i, true);
			renderer.setSeriesShapesVisible(i, true);
			renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-2, -2, 4, 4));
		}
		
		// Apply the renderer to the graph
		XYPlot plot = lineGraph.getXYPlot();
		plot.setRenderer(renderer);
		
		// Display the line graph and redraw the graph if there are any changes
		ChartPanel sizeGraph = new ChartPanel(lineGraph);
		sizeGraph.setMouseWheelEnabled(true);
		
		// Some functionalities for the window output to work well
		JFrame windowGraph = new JFrame("Sequence Plot using f(x) = cx(1-x)");
		windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowGraph.getContentPane().add(sizeGraph);
		windowGraph.pack();
		windowGraph.setLocationRelativeTo(null);
		windowGraph.setVisible(true);
	}
	
	
	public static void main(String[] args) 
	{
		// First term of the sequence
		double x_0 = 0.1;
		// Number of terms/iterations
		int N = 100;
		// All c values to test
		double[] cValues = {0.95, 1.55, 2.0, 3.6, 3.98};
		
		// Create a list to store the sequences (which are also lists)
		List<List<Double>> sequences = new ArrayList<>();
		
		// Generate the sequence for each c value
		for(int i = 0; i < cValues.length; i++)
		{
			sequences.add(generateSequence(x_0, N, cValues[i]));
		}
		
		// Create and display the graph 
		createGraph(sequences, N, cValues);
	}

}
