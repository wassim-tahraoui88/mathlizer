import sys
import os
import numpy as np
import matplotlib.pyplot as plt
from sympy import symbols, lambdify, sympify


expression = sys.argv[1]
var = "x"
start = float(sys.argv[2])
end = float(sys.argv[3])
output_file_name = sys.argv[4]
multimedia_path_temp = sys.argv[5]


x = symbols(var)
function = sympify(expression)
f_lambdified = lambdify(x, function, modules=["numpy"])
x_vals = np.linspace(start, end, 500)
y_vals = f_lambdified(x_vals)
valid_indices = np.isfinite(y_vals)
x_vals = x_vals[valid_indices]
y_vals = y_vals[valid_indices]

if np.any(np.isnan(y_vals)) or np.any(np.isinf(y_vals)):
    raise ValueError("The expression resulted in invalid numerical values.")

plt.figure(figsize=(8, 6))
plt.plot(x_vals, y_vals, label=f"${expression}$")
plt.title("Graph of the Function")
plt.xlabel(var)
plt.ylabel("f(x)")
plt.axhline(0, color='black', linewidth=0.8, linestyle="--")
plt.axvline(0, color='black', linewidth=0.8, linestyle="--")
plt.grid(alpha=0.5)
plt.legend()

output_dir = multimedia_path_temp
os.makedirs(output_dir, exist_ok=True)
output_path = os.path.join(output_dir, f"{output_file_name}.svg")
plt.savefig(output_path, format="svg")
plt.close()