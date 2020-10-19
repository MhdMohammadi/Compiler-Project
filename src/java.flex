/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* Java 1.2 language lexer specification */

/* Use together with unicode.flex for Unicode preprocesssing */
/* and java12.cup for a Java 1.2 parser                      */

/* Note that this lexer specification is not tuned for speed.
   It is in fact quite slow on integer and floating point literals, 
   because the input is read twice and the methods used to parse
   the numbers are not very fast. 
   For a production quality application (e.g. a Java compiler) 
   this could be optimized */


import java_cup.runtime.*;

%%

%public
%class Scanner
%implements sym

%unicode

%line
%column

%cup
%cupdebug

%{
  StringBuilder string = new StringBuilder();
  
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  /** 
   * assumes correct representation of a long value for 
   * specified radix in scanner buffer from <code>start</code> 
   * to <code>end</code> 
   */
  private long parseLong(int start, int end, int radix) {
    long result = 0;
    long digit;

    for (int i = start; i < end; i++) {
      digit  = Character.digit(yycharat(i),radix);
      result*= radix;
      result+= digit;
    }

    return result;
  }
%}

/* main character classes */

Identifier = [a-z|A-Z][a-z|A-Z|0-9|_]*

LineTerminator = \r|\n|\r\n
//todo
WhiteSpace     = {LineTerminator} | [ \t\f]

TrueLiteral = "true"
FalseLiteral = "false"

DecIntegerLiteral = [0-9]+
HexIntegerLiteral = 0[xX][0-9|a-f|A-F]+

DoubleLiteral = [0-9]+\.[0-9]*
DoubleScientificLiteral = [0-9]+\.[0-9]*[eE][-+]?[0-9]+

InputCharacter = [^\r\n]
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
Comment = {TraditionalComment} | {EndOfLineComment}


%state STRING, CHARLITERAL

%%

<YYINITIAL> {

  "void"        { return symbol(VOID); }
  "int"         { return symbol(INT); }
  "double"      { return symbol(DOUBLE); }
  "bool"        { return symbol(BOOL); }
  "string"      { return symbol(STRING); }
  "class"       { return symbol(CLASS); }
  "interface"   { return symbol(INTERFACE); }
  "null"        { return symbol(NULL); }
  "this"        { return symbol(THIS); }
  "extends"     { return symbol(EXTENDS); }
  "implements"  { return symbol(IMPLEMENTS); }
  "for"         { return symbol(FOR); }
  "while"       { return symbol(WHILE); }
  "if"          { return symbol(IF); }
  "else"        { return symbol(ELSE); }
  "return"
  "break"
  "continue"
  "new"
  "NewArray"
  "print"
  "ReadInteger"
  "ReadLine"
  "dtoi"
  "itod"
  "btoi"
  "itob"
  "private"
  "protected"
  "public"

  /* boolean literals */
  "true"                         { return symbol(BOOLEAN_LITERAL, true); }
  "false"                        { return symbol(BOOLEAN_LITERAL, false); }
  
  /* null literal */
  "null"                         { return symbol(NULL_LITERAL); }
  
  /* separators */
  "("                            { return symbol(LPAREN); }
  ")"                            { return symbol(RPAREN); }
  "["                            { return symbol(LBRACK); }
  "]"                            { return symbol(RBRACK); }
  ";"                            { return symbol(SEMICOLON); }
  ","                            { return symbol(COMMA); }
  "."                            { return symbol(DOT); }
  
  /* operators */
  "="                            { return symbol(EQ); }
  ">"                            { return symbol(GT); }
  "<"                            { return symbol(LT); }
  "!"                            { return symbol(NOT); }
  "=="                           { return symbol(EQEQ); }
  "<="                           { return symbol(LTEQ); }
  ">="                           { return symbol(GTEQ); }
  "!="                           { return symbol(NOTEQ); }
  "&&"                           { return symbol(ANDAND); }
  "||"                           { return symbol(OROR); }
  "+"                            { return symbol(PLUS); }
  "-"                            { return symbol(MINUS); }
  "*"                            { return symbol(MULT); }
  "/"                            { return symbol(DIV); }
  "&"                            { return symbol(AND); }
  "|"                            { return symbol(OR); }
  "%"                            { return symbol(MOD); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */

  {DecIntegerLiteral}            { return symbol(INTEGER_LITERAL, Integer.valueOf(yytext())); }

  {HexIntegerLiteral}            { return symbol(INTEGER_LITERAL, Integer.valueOf((int) parseLong(2, yylength(), 16))); }

  {DoubleLiteral}                { return symbol(FLOATING_POINT_LITERAL, new Double(yytext())); }
  //todo

  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */ 
  {Identifier}                   { return symbol(IDENTIFIER, yytext()); }  
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol(STRING_LITERAL, string.toString()); }

  //todo in ham bayad dobare neveshte sheY
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  //todo in ja ye chize koofti i bood pak kardim(backspace)
  "\\t"                          { string.append( '\t' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\b"                          { string.append( '\b' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }

  /* error cases */
  {LineTerminator}               { throw new RuntimeException("Unterminated string at end of line"); }
}

/* error fallback */
//todo undifined token
[^]                              { throw new RuntimeException("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); }
<<EOF>>                          { return symbol(EOF); }