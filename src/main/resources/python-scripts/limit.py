import sys
from sympy import symbols, limit, sympify, latex

if len(sys.argv) < 3:
    sys.exit(1)

expr = sys.argv[1]
point = sys.argv[2]
is_latex = sys.argv[3] == "1"
x = symbols("x")

result = limit(sympify(expr), x, sympify(point))
if is_latex:
    print(latex(result))
else:
    print(result)