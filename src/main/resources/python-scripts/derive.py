import sys
from sympy import symbols, diff, sympify

expr = sys.argv[1]
var = sys.argv[2]
x = symbols(var)
result = diff(sympify(expr), x)
print(result)