Java_Compiler by Klaus

This project implements a small Java-like language using a classic multi-phase compiler architecture

Compiler Structure:

LEXER
PARSER
INTERPRETER

Lexer (TOKENIZER:
The lexer works as socalled Tokenizer, scans the source code character by character and groups into useful untis, the tokens (Numbers, Identifiers, Keywords, Operators, ...). The output of the lexer is a structured list of tokens.

Parser:
The parser builds a abstract syntax tree (AST) of the tokenlist, this tree will be executed by the interpreter.

Interpreter:
The intpreter traverses the AST and executes the program.
