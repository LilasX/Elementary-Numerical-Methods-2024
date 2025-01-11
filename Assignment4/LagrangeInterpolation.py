# Import numpy for numerical operations
import numpy as np
# Import matplotlib for plotting graphs
import matplotlib.pyplot as plt

# Define the functions
"""Function: sin(πx)"""
def f1(x):
    # Return sin(πx) for each element in x
    return np.sin(np.pi * x)

"""Function: 1 / (1 + x^2)"""
def f2(x):
    # Return the value of 1/(1 + x^2) for each element in x
    return 1 / (1 + x**2)

# Generate Lagrange basis functions
"""Calculate the Lagrange basis polynomial for a given x and i."""
def lagrange_basis(x_points, i, x):
    # Initialize the basis polynomial to 1
    basis = 1.0
    # Loop over all x_points and compute the basis polynomial L_i(x)
    for j, xj in enumerate(x_points):
        # Skip the current point i
        if j != i:
            # Compute the Lagrange basis term
            basis *= (x - xj) / (x_points[i] - xj)
    # Return the computed Lagrange basis polynomial L_i(x)
    return basis

# Compute the Lagrange interpolating polynomial
"""Calculate the Lagrange interpolating polynomial at given evaluation points."""
def lagrange_interpolating_poly(x_points, y_points, x_eval):
    # Number of interpolation points
    n = len(x_points)
    # Initialize the polynomial result (same shape as x_eval)
    pn = np.zeros_like(x_eval)
    # Loop over each point
    for i in range(n):
        # Add the contribution of each Lagrange basis term
        pn += y_points[i] * lagrange_basis(x_points, i, x_eval)
    # Return the final interpolating polynomial p_n(x)
    return pn

# Compute the maximum interpolation error
"""Calculate the maximum interpolation error."""
def max_interpolation_error(f, pn, y_eval):
    # Return the maximum absolute difference between true values and interpolated values
    return np.max(np.abs(f(y_eval) - pn))

# Plot the function and its Lagrange interpolants
"""Plot the function f(x) and its Lagrange interpolants for different n values."""
def plot_function_and_interpolants(f, x_eval, n_values, M, interval, function_name):
    # Create a new figure with a specific size for each function plot
    plt.figure(figsize=(8, 6))
    # Plot the true function f(x) in blue
    plt.plot(x_eval, f(x_eval), label="f(x)", color="blue")
    
    # Plot the interpolating polynomials for different n values
    for n in n_values:
        # Extract the start and end of the interval
        a, b = interval
        # Generate n+1 equally spaced interpolation points in the interval
        x_points = np.linspace(a, b, n + 1)
        # Evaluate the function at these interpolation points
        y_points = f(x_points)
        
        # Compute interpolating polynomial at M+1 points for better resolution
        # Generate M+1 sampling points for polynomial evaluation
        y_eval = np.linspace(a, b, M + 1)
        # Compute the interpolated polynomial at the sampling points
        pn = lagrange_interpolating_poly(x_points, y_points, y_eval)
        
        # Plot the interpolant for this value of n with dashed lines
        plt.plot(y_eval, pn, label=f"p_n(x), n={n}", linestyle="--")
    
    # Modify the title to include the interval and function
    # Convert the interval to a string for the title
    interval_str = f"[{interval[0]}, {interval[1]}]"
    # LaTeX formatting for function names for better presentation in the plot title
    if function_name == "sin(πx)":
        # For sin(πx), use LaTeX format for display
        func_title = r"$\sin(\pi x)$"
    elif function_name == "1/(1+x^2)":
        # For 1/(1+x^2), use LaTeX format for display
        func_title = r"$\frac{1}{1+x^2}$"
    else:
        # Default: use the function name as it is
        func_title = function_name
    
    # Set the title with the formatted function name and interval
    plt.title(f"Lagrange Interpolants on {interval_str} for {func_title}")
    # Add a legend to the plot
    plt.legend()
    # Display the grid for easier visualization of the plot
    plt.grid()
    # Show the plot
    plt.show()

# Main computation for plotting all cases
"""Compute the Lagrange interpolants for different n values and plot them along with the true function."""
"""Compute and print the maximum interpolation error for each n."""
def compute_results_and_plot(f, interval, n_values, M, function_name):
    # Extract the interval endpoints
    a, b = interval
    # Generate 1000 points for high-resolution plot of the true function
    x_eval = np.linspace(a, b, 1000)
    # Generate M+1 sampling points for the interpolated polynomial
    y_eval = np.linspace(a, b, M + 1)
    
    # Compute and print the maximum interpolation error for each n
    for n in n_values:
        # Generate n+1 interpolation points in the interval
        x_points = np.linspace(a, b, n + 1)
        # Evaluate the function at the interpolation points
        y_points = f(x_points)
        
        # Compute interpolating polynomial at the sampling points
        pn = lagrange_interpolating_poly(x_points, y_points, y_eval)
        
        # Compute and print the maximum interpolation error between the true function and the interpolant
        error = max_interpolation_error(f, pn, y_eval)
        print(f"Function: {function_name}, Interval [{a}, {b}], n = {n}, Maximum interpolation error: {error:.6f}")
    
    # Plot the function and its Lagrange interpolants for the given function
    plot_function_and_interpolants(f, x_eval, n_values, M, interval, function_name)

# Define intervals and parameters
# List of different numbers of interpolation points to test
n_values = [2, 4, 8, 16]
# Number of sampling points for the interpolant
M = 500

# Case 1: f(x) = sin(πx), interval [-1, 1]
compute_results_and_plot(f1, [-1, 1], n_values, M, "sin(πx)")

# Case 2: f(x) = 1/(1+x^2), interval [-2, 2]
compute_results_and_plot(f2, [-2, 2], n_values, M, "1/(1+x^2)")

# Case 3: f(x) = 1/(1+x^2), interval [-5, 5]
compute_results_and_plot(f2, [-5, 5], n_values, M, "1/(1+x^2)")