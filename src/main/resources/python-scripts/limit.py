import sys
from sympy import symbols, limit, sympify

expr = sys.argv[1]
point = sys.argv[2]
x = symbols("x")
result = limit(sympify(expr), x, sympify(point))
print(result)