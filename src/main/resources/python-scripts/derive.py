import sys
from sympy import symbols, diff, sympify, latex

if len(sys.argv) < 3:
    sys.exit(1)

expr = sys.argv[1]
var = sys.argv[2]
is_latex = bool(sys.argv[3])
x = symbols(var)

result = diff(sympify(expr), x)
if is_latex:
    print(latex(result))
else:
    print(result)