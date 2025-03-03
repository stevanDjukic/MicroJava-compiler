# Project Overview
This project implements a compiler for the MicroJava programming language, which translates MicroJava source code into MicroJava bytecode that runs on the MicroJava Virtual Machine (MJVM).

# The compiler performs:
  - Lexical Analysis – Tokenizing the input source code.
  - Syntax Analysis – Parsing the code according to MicroJava grammar.
  - Semantic Analysis – Validating contextual correctness and symbol usage.
  - Code Generation – Producing MJVM-compatible bytecode for execution.

This compiler is implemented in Java, utilizing JFlex for lexical analysis, AST-CUP for parsing, and MJVM tools for code execution.

# Features
- Lexical Analysis
  - Tokenizes MicroJava source code using JFlex.
  - Detects and reports lexical errors with line and column numbers.
- Syntax Analysis
  - Implements a LALR(1) parser using AST-CUP.
  - Recovers from syntax errors and reports them with explanations.
- Semantic Analysis
  - Checks symbol declarations and type correctness.
  - Uses a symbol table for variable, function, and class tracking.
- Code Generation
  - Translates correct programs into MicroJava bytecode.
  - Outputs .obj files executable on the MJVM.
