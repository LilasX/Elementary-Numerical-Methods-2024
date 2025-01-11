package client;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SequenceReccurenceRelation 
{
	// Function f(x) = (2x^3+5) / (3x^2)
	public static double function(double x)
	{
		return (2*Math.pow(x, 3)+5) / (3*Math.pow(x,2));
	}
	
	// Generate the sequence for N iterations
	public static ArrayList<Double> generateSequence(double x_0, int N)
	{
		// Store the sequence in an ArrayList
		ArrayList<Double> seq = new ArrayList<>();
		// Start sequence from x^(0)
		seq.add(x_0);
		// Print the first term of the sequence and its value
		System.out.printf("x^(%d) = %.10f%n", 0, x_0);
		
		// Calculate the value of each term and add it into our sequence
		for(int k = 0; k < N; k++)
		{
			// Calculate the value of the current term
			double x_k = function(seq.get(k));
			// Add the value of the calculated term into our sequence
			seq.add(x_k);
			
			// Print the current iteration and each term of the sequence along with its value
			System.out.printf("x^(%d) = %.10f%n", k + 1, x_k);
		}
		
		// Return the sequence in terms of N values
		return seq;
	}
	
	// Plot the sequence on a graph
	public static void createGraph(ArrayList<Double> seq, int N)
	{
		// Create an empty sequence in the form of (x, y)
		XYSeries series = new XYSeries("x^(k)");
		
		// Add our results into the empty sequence created
		for(int k = 0; k <= N; k++)
		{
			series.add(k, seq.get(k));
		}
		
		// Create a dataset and add our sequence into it
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		
		// Create a line graph based on our dataset
		JFreeChart lineGraph = ChartFactory.createXYLineChart("Sequence x^(k) using Recurrence Relation", "N values", "x^(N) values", dataset);
		
		// To make the dots (results of the sequence for N) appear by default in the graph
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-2, -2, 4, 4));
		
		// Apply the renderer to the graph
		XYPlot plot = lineGraph.getXYPlot();
	    plot.setRenderer(renderer);
		
		// Display the line graph and redraw the graph if there are any changes
		ChartPanel sizeGraph = new ChartPanel(lineGraph);
		sizeGraph.setMouseWheelEnabled(true);
		
		// Some functionalities for the window output to work well
		JFrame windowGraph = new JFrame("Sequence Plot using Recurrence Relation");
		windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowGraph.getContentPane().add(sizeGraph);
		windowGraph.pack();
		windowGraph.setLocationRelativeTo(null);
		windowGraph.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		// First term of the sequence
		double x_0 = 1.0;
		// Number of terms/iterations
		int N = 20;
		
		// Generate the sequence
		ArrayList<Double> sequence = generateSequence(x_0, N);
		
		// Create and display the graph of the sequence
		createGraph(sequence, N);
	}

}

