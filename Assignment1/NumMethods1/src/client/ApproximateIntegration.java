package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ApproximateIntegration 
{

	// Function f(x) = sin(πx)
	public static BigDecimal function(BigDecimal x)
	{
		return BigDecimal.valueOf(Math.sin(Math.PI * x.doubleValue()));
	}
	
	// Midpoint Rule Integration
	public static BigDecimal midpointRule(Function<BigDecimal, BigDecimal> f, int M)
	{
		// h = 1/M
		BigDecimal h = BigDecimal.valueOf(1.0/M);
		BigDecimal sum = BigDecimal.ZERO;
		
		for(int k = 0; k < M; k++)
		{
			// x_k = k*h 
			// Midpoint = x_k + h/2 = k*h + h/2 = h(k+1/2)
			BigDecimal x_k = h.multiply(BigDecimal.valueOf(k).add(BigDecimal.valueOf(0.5)));
			// Add the result to the sum
			sum = sum.add(f.apply(x_k));
		}
		
		// Return the result of the approximate integration of the Midpoint Rule
		return h.multiply(sum);
	}
	
	// Simpson's Rule Integration
	public static BigDecimal simpsonRule(Function<BigDecimal, BigDecimal> f, int M)
	{
		// M needs to be even
		if(M % 2 != 0)
		{
			throw new IllegalArgumentException("M cannot be odd for Simpson's Rule!");
		}
		
		// h = 1/M
		BigDecimal h = BigDecimal.valueOf(1.0/M);
		// Start with first and last terms : f(x_0) + f(x_M)
		BigDecimal sum = f.apply(BigDecimal.ZERO).add(f.apply(BigDecimal.ONE));
		
		// Assign the coefficient for each term
		for(int k = 1; k < M; k++)
		{
			// x_k = k*h
			BigDecimal x_k = h.multiply(BigDecimal.valueOf(k));
			
			// Even indexed terms
			if(k % 2 == 0)
			{
				// sum += 2 * f.apply(x_k)
				sum = sum.add(f.apply(x_k).multiply(BigDecimal.valueOf(2)));
			}
			// Odd indexed terms
			else
			{
				// sum += 4 * f.apply(x_k)
				sum = sum.add(f.apply(x_k).multiply(BigDecimal.valueOf(4)));
			}
		}
		
		// Return the result of the approximate integration of the Simpson's Rule 
		//(h/3) * sum;
		return sum.multiply(h.divide(BigDecimal.valueOf(3), MathContext.DECIMAL128));
	}
	
	// Create the graph for Midpoint Rule
	public static void createGraphMidpoint(BigDecimal integralValue, int n)
	{		
		// Create series for the graph
		XYSeries midPointData = new XYSeries("Midpoint Error");
		
		// Print the headers for the results
		System.out.printf("%-10s %-20s%n", "M", "Midpoint Error");
		
		// Calculate the results for each M values for Midpoint Rule
		for(int i = 1; i <= n; i++)
		{
			// Calculate the errors with Midpoint Rule
			BigDecimal midpointResult = midpointRule(ApproximateIntegration::function, i); 
			// midpointError = Math.abs(midpointResult-integralValue)
			BigDecimal midpointError = midpointResult.subtract(integralValue).abs();
			midPointData.add(i, midpointError);
			
			// Print the results
			System.out.printf("%-10d %-20s%n", i, midpointError.toPlainString());
			
			// To check the smallest value of M for which the error is less than 10^(-7)
			if(midpointError.compareTo(BigDecimal.valueOf(1e-7)) < 0)
			{
				// Print the results
				System.out.printf("Midpoint error < 10^(-7) at M = %d: %s%n", i, midpointError.toPlainString());
				break;
			}
		}
				
		// Create a dataset and add our results for errors into it
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(midPointData);
		
		// Create a line graph based on our dataset
		JFreeChart lineGraph = ChartFactory.createXYLineChart("Approximate Integration Errors", "M subintervals", "Error (Log Scale)", dataset);
		
		// Get the graph
		XYPlot plot = lineGraph.getXYPlot();

	    // Set the y-axis to a logarithmic scale
	    LogarithmicAxis logAxis = new LogarithmicAxis("Error (Log Scale)");
	    plot.setRangeAxis(logAxis);
	    
        // Setting dimensions of the window and enable mouse wheel for Zoom-in and Zoom-out
		ChartPanel sizeGraph = new ChartPanel(lineGraph);
		sizeGraph.setMouseWheelEnabled(true);
		
		// Some functionalities for the window output to work well
		JFrame windowGraph = new JFrame("Midpoint Rule Errors");
		windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowGraph.getContentPane().add(sizeGraph);
		windowGraph.pack();
		windowGraph.setVisible(true);
	}
	
	// Create the graph for Simpson's Rule
	public static void createGraphSimpson(BigDecimal integralValue, int n)
	{
		// Create series for the graph
		XYSeries simpsonData = new XYSeries("Simpson Error");
		
		// Print the headers for the results
		System.out.printf("%-10s %-20s%n", "M", "Simpson error");
		
		// Calculate the results for each M values for Simpson's Rule
		for(int i = 1; i <= n; i++)
		{
			int m = i*2;
			
			// Calculate the errors with Simpson's Rule
			BigDecimal simpsonResult = simpsonRule(ApproximateIntegration::function, m); 
			BigDecimal simpsonError = simpsonResult.subtract(integralValue).abs();
			simpsonData.add(m, simpsonError);
			
			// Print the results
			System.out.printf("%-10d %-20s%n", m, simpsonError.toPlainString());
			
			// To check the smallest value of M for which the error is less than 10^(-7)
			if(simpsonError.compareTo(BigDecimal.valueOf(1e-7)) < 0)
			{
				// Print the results
				System.out.printf("Simpson error < 10^(-7) at M = %d: %s%n", m, simpsonError.toPlainString());
				break;
			}
		}
		
		// Create a dataset and add our results for errors into it
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(simpsonData);
		
		// Create a line graph based on our dataset
		JFreeChart lineGraph = ChartFactory.createXYLineChart("Approximate Integration Errors", "M subintervals", "Error (Log Scale)", dataset);
		
		// To make the dots (results for M) appear by default in the graph
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-2, -2, 4, 4));
		
		// Apply the renderer to the graph
		XYPlot plot = lineGraph.getXYPlot();
	    plot.setRenderer(renderer);

	    // Set the y-axis to a logarithmic scale
	    LogarithmicAxis logAxis = new LogarithmicAxis("Error (Log Scale)");
	    plot.setRangeAxis(logAxis);
		
		// Display the line graph and redraw the graph if there are any changes
		ChartPanel sizeGraph = new ChartPanel(lineGraph);
		sizeGraph.setMouseWheelEnabled(true);
		
		// Some functionalities for the window output to work well
		JFrame windowGraph = new JFrame("Simpson's Rule Errors");
		windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowGraph.getContentPane().add(sizeGraph);
		windowGraph.pack();
		// Get the screen size
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    // Get the window size
	    Dimension windowSize = windowGraph.getSize();
	    // Calculate the position for bottom-right corner
	    int xPosition = screenSize.width - windowSize.width;
	    int yPosition = 0;
	    // Set the location of the window to bottom-right corner
	    windowGraph.setLocation(xPosition, yPosition);
		windowGraph.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		// Analytically obtained value of the integral of f(x) = sin(πx)
		BigDecimal exactIntegralValue = BigDecimal.valueOf(2.0 / Math.PI);
		
		// Create and display the graphs
		createGraphMidpoint(exactIntegralValue, 2000);
		createGraphSimpson(exactIntegralValue, 50);		
	}

}
