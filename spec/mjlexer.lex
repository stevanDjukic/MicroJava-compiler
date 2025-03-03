package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
	private Symbol new_symbol(int type){
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value){
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" "		{ }
"\b"	{ }
"\t"	{ }
"\r\n"	{ }
"\f"	{ }

"program"	{return new_symbol(sym.PROGRAM, yytext()); }
"break"		{return new_symbol(sym.BREAK, yytext()); }
"class"		{return new_symbol(sym.CLASS, yytext()); }
"else"		{return new_symbol(sym.ELSE, yytext()); }
"const"		{return new_symbol(sym.CONST, yytext()); }
"if"		{return new_symbol(sym.IF, yytext()); }
"new"		{return new_symbol(sym.NEW, yytext()); }
"print"		{return new_symbol(sym.PRINT, yytext()); }
"read"		{return new_symbol(sym.READ, yytext()); }
"return"	{return new_symbol(sym.RETURN, yytext()); }
"this"		{return new_symbol(sym.THIS, yytext()); }
"void"		{return new_symbol(sym.VOID, yytext()); }
"extends"	{return new_symbol(sym.EXTENDS, yytext()); }
"continue"	{return new_symbol(sym.CONTINUE, yytext()); } 
"union"		{return new_symbol(sym.UNION, yytext()); }
"do"		{return new_symbol(sym.DO, yytext()); }
"while"		{return new_symbol(sym.WHILE, yytext()); }
"map"		{return new_symbol(sym.MAP, yytext()); }
"interface"		{return new_symbol(sym.INTERFACE, yytext()); }


"+"			{return new_symbol(sym.PLUS, yytext()); }
"-"			{return new_symbol(sym.MINUS, yytext()); }
"*"			{return new_symbol(sym.MUL, yytext()); }
"/"			{return new_symbol(sym.DIV, yytext()); }
"%"			{return new_symbol(sym.MOD, yytext()); }
"=="		{return new_symbol(sym.EQUALS_TO, yytext()); }
"!="		{return new_symbol(sym.NOT_EQUAL, yytext()); }
">"			{return new_symbol(sym.GREATER, yytext()); }
">="		{return new_symbol(sym.GREATER_EQUAL, yytext()); }
"<"			{return new_symbol(sym.LOWER, yytext()); }
"<="		{return new_symbol(sym.LOWER_EQUAL, yytext()); }
"&&"		{return new_symbol(sym.AND, yytext()); }
"||"		{return new_symbol(sym.OR, yytext()); }
"="			{return new_symbol(sym.EQUAL, yytext()); } 
"++"		{return new_symbol(sym.INC, yytext()); }
"--"		{return new_symbol(sym.DEC, yytext()); }
";"			{return new_symbol(sym.SEMI_COLON, yytext()); }
":"		{return new_symbol(sym.COLON, yytext()); }
","			{return new_symbol(sym.COMA, yytext()); }
"."			{return new_symbol(sym.DOT, yytext()); }
"("			{return new_symbol(sym.LPAREN, yytext()); }
")"			{return new_symbol(sym.RPAREN, yytext()); }
"["			{return new_symbol(sym.LBRACKET, yytext()); }
"]"			{return new_symbol(sym.RBRACKET, yytext()); } 
"{"			{return new_symbol(sym.LBRACE, yytext()); }
"}"			{return new_symbol(sym.RBRACE, yytext()); }

"//"		{yybegin(COMMENT); }
<COMMENT> . { yybegin(COMMENT); }
<COMMENT> "\r\n" {yybegin(YYINITIAL); }

("true" | "false") 				{return new_symbol (sym.BOOL_CONST, Boolean.valueOf(yytext()));}
[a-zA-Z][a-zA-Z0-9_]*			{return new_symbol (sym.IDENT, yytext()); }
[0-9]+							{return new_symbol(sym.NUM_CONST, new Integer (yytext())); }
"'"[\040-\176]"'" 				{return new_symbol (sym.CHAR_CONST, new Character (yytext().charAt(1)));}



. { System.err.println("Leksicka greska ("+yytext()+") u liniji " + (yyline+1)); }