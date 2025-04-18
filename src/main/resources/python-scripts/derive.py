import sys
from sympy import symbols, diff, sympify, latex

if len(sys.argv) < 4:
    sys.exit(1)

expr = sys.argv[1]
var = sys.argv[2]
order = sys.argv[3]
is_latex = sys.argv[4] == "1"
x = symbols(var)

result = diff(sympify(expr), x, order)
if is_latex:
    print(latex(result))
else:
    print(result)