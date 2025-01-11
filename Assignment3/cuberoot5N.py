"""Import necessary libraries"""
# Import NumPy for numerical operations, such as linspace (for generating ranges of values)
import numpy as np
# Import Matplotlib for plotting graphs
import matplotlib.pyplot as plt

# Define the Newton iteration function for cube root of 5
def newton_iteration(x):
    # This is the Newton's method iteration formula for the cube root of 5
    # f(x) = (2 * x^3 + 5) / (3 * x^2)
    # Compute the next value based on the current value of x
    return (2 * x**3 + 5) / (3 * x**2)
    
"""Initial guess and number of iterations"""
# Set the initial guess for x_0 (starting point for Newton's method)
x0 = 1.0
# Define how many iterations of Newton's method you want to perform
iterations = 10

"""List to store x_k and x_(k+1) values for plotting"""
# Initialize the list with the first value x_0
x_vals = [x0]
# Compute the first iteration x_1 and store it in y_vals
y_vals = [newton_iteration(x0)]

# Print the starting point
# Print the initial guess x_0 with 10 decimal places for precision
print(f"x(0) = {x0:.10f}")

"""Perform Newton's method iterations and print each step starting from x(1)"""
# Loop over the number of iterations (from 1 to 10)
for k in range(1, iterations+1):
    # Apply Newton's method to the last value in x_vals to get x_(k+1)
    x_next = newton_iteration(x_vals[-1])
    # Append the new value x_(k+1) to the list x_vals
    x_vals.append(x_next)
    # Compute the next y value using Newton's method and append it to y_vals
    y_vals.append(newton_iteration(x_next))
    
    # Print each iteration's result starting from x(1)
    # Print the current value x_k with 10 decimal places for precision
    print(f"x({k}) = {x_vals[k]:.10f}")

"""Generate points for plotting x_(k+1) vs x_k curve"""
# Extend x range a little beyond the data range
x_min = min(x_vals) - 5.0
x_max = max(x_vals) + 5.0

# Generate 400 points between the minimum and maximum x values, with a buffer of 0.5
x_line = np.linspace(x_min, x_max, 400)
# Apply Newton's method to all values in x_line to get corresponding y values
y_line = newton_iteration(x_line)

# Create the plot
# Create a figure with an 8x8 inch size
plt.figure(figsize=(8, 8))

"""Plot the Newton iteration function (x_(k+1) vs x_k) as a blue line"""
# Plot the function g(x) = x_{k+1} as a blue line with a label
plt.plot(x_line, y_line, 'b', label='$x_{k+1} = x_k - [g(x_k)/g\'(x_k)]$')

"""Plot the reference line y = x (diagonal line) in black solid style"""
# Plot the line y = x (black line) as a reference for convergence
plt.plot(x_line, x_line, 'k-', label='$y = x$')

# Set axis limits to make sure we see the lines completely
plt.xlim(x_min, x_max)  # Extend the x-axis from x_min to x_max
plt.ylim(0, max(y_vals) + 1.0)  # Set y-axis starting at 0 and a bit above the max y value

"""Plot the iteration steps (red curves) showing convergence"""
# Loop through all x_k and y_k values except the last one
for i in range(len(x_vals) - 1):
    if i == 0:
        # Start from x_0, but y=0 for the first iteration (initial guess)
        plt.plot([x_vals[i], x_vals[i]], [0, y_vals[i]], 'r')
        plt.plot([x_vals[i], y_vals[i]], [y_vals[i], y_vals[i]], 'r')
    else:
        # Vertical line from (x_k, x_k) to (x_k, x_(k+1)) to show the iteration step from x_k to x_(k+1)
        # Vertical red line showing the step from x_k to x_(k+1)
        plt.plot([x_vals[i], x_vals[i]], [x_vals[i], y_vals[i]], 'r')
        
        # Horizontal line from (x_k, x_(k+1)) to (x_(k+1), x_(k+1)) to show the horizontal step from x_k to x_(k+1)
        # Horizontal red line showing the step from x_k to x_(k+1)
        plt.plot([x_vals[i], y_vals[i]], [y_vals[i], y_vals[i]], 'r')

"""Set limits for better visibility and add a buffer around the points for clarity"""
# Set the x-axis limits to the minimum and maximum values of x_vals, with a 3.0 buffer
plt.xlim(0, max(x_vals) + 3.0)
# Set the y-axis limits to the minimum and maximum values of y_vals, with a 3.5 buffer
plt.ylim(0, max(x_vals) + 3.5)

"""Labels and title for the plot"""
# Label the x-axis as 'x' with font size 12
plt.xlabel('$x_k$', fontsize=12)
# Label the y-axis as 'y' with font size 12
plt.ylabel('$x_{k+1}$', fontsize=12)
# Title of the plot, showing the method for cube root of 5
plt.title('Newton’s Method Iterations for $\\sqrt[3]{5}$', fontsize=14)

"""Add a legend to the plot to explain the different lines"""
# Display the legend for the plot
plt.legend()

"""Add a grid to the plot for easier visualization of the steps"""
# Enable the grid on the plot for better readability
plt.grid(True)

"""Show the plot on the screen"""
# Display the plot
plt.show()