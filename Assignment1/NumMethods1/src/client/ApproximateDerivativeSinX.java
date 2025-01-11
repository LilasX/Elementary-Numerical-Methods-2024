package client;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ApproximateDerivativeSinX 
{
	// Function f(x) = sin(x)
	public static BigDecimal function(BigDecimal x)
	{
		return BigDecimal.valueOf(Math.sin(x.doubleValue()));
	}
	
	// Derivative of sin(x) = cos(x)
	public static BigDecimal realDerivative(BigDecimal x)
	{
		return BigDecimal.valueOf(Math.cos(x.doubleValue()));
	}
	
	// Approximation of the derivative of function f at point x, equation definition
	public static BigDecimal approximateDerivativeEquation(Function<BigDecimal, BigDecimal> f, BigDecimal x, BigDecimal h)
	{
		// f'(x) = [f(x+h)-f(x)] / h 
		// lim h->0
		return f.apply(x.add(h)).subtract(f.apply(x)).divide(h, MathContext.DECIMAL128);
	}
	
	// Create the graph for the absolute errors of the approximation
	public static void createGraph(BigDecimal x, int n)
	{
		// Create series for the graph
		XYSeries errorData = new XYSeries("Derivative Approximation Error");
		
		// h_0 = 1
		BigDecimal h = BigDecimal.ONE;
		
		// Compute the exact value of the derivative at point x for error 
		BigDecimal realDerivativeValue = realDerivative(x);
		
		// Print the headers for the results
		System.out.printf("%-10s %-40s %-20s%n", "n", "h_n", "Error");
		
		// Calculate the results for each n 
		// h_n = 0.5 * h_(n-1)
		for (int i = 1; i <= n; i++)
		{
			// Calculate the approximate derivative with current h
			BigDecimal approxDerivative = approximateDerivativeEquation(ApproximateDerivativeSinX::function, x, h);
			
			// Calculate the error
			// error = |y_n - f'(x)|
			BigDecimal error = approxDerivative.subtract(realDerivativeValue).abs();
			
			// Compute -log_10(h) and log_10(|error|)
			// X-axis: -log10(h)
			double logH = -Math.log10(h.doubleValue());
			// Y-axis: log10(|error|)
            double logError = Math.log10(error.doubleValue());
            
            // Add the results in the series for the graph
            errorData.add(logH, logError);
            
            // Print the results
            System.out.printf("%-10d %-40s %-20s%n", i, h.toPlainString(), error.toPlainString());
            
            // Update the value of h_n
            // h_n = 0.5 * h_(n-1)
            h = h.multiply(BigDecimal.valueOf(0.5));
		}
		
		// Create a dataset and add our results into it
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(errorData);
		
		// Create a line graph based on our dataset
		JFreeChart lineGraph = ChartFactory.createXYLineChart("Derivative Approximation Error for f(x)=sin(x)", "-log_10(h)", "log_10(|Error|)", dataset);
		
		// To make the dots (results for errors) appear by default in the graph
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesLinesVisible(0, true);
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-1, -1, 2, 2));
		
		// Apply the renderer to the graph
		XYPlot plot = lineGraph.getXYPlot();
		plot.setRenderer(renderer);
		
		// Display the line graph and redraw the graph if there are any changes
		ChartPanel sizeGraph = new ChartPanel(lineGraph);
		sizeGraph.setMouseWheelEnabled(true);
		
		// Some functionalities for the window output to work well
		JFrame windowGraph = new JFrame("Derivative Approximation Errors sin(x)");
		windowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowGraph.getContentPane().add(sizeGraph);
		windowGraph.pack();
		windowGraph.setLocationRelativeTo(null);
    	windowGraph.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		// Approximate the derivative of f(x) = sin(x) at x = 0.5
		BigDecimal x = BigDecimal.valueOf(0.5);
		
		// n = 1, 2, 3, ..., 30
		createGraph(x, 30);
	}

}
