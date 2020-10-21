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

  public String yylex() throws IOException {
    java_cup.runtime.Symbol currentSymbol = next_token();
    switch (currentSymbol.sym){
      case BOOLEAN_LITERAL:
        return "T_BOOLEANLITERAL " + yytext();
      case INTEGER_LITERAL:
        return "T_INTLITERAL " + yytext();
      case STRING_LITERAL:
        return "T_STRINGLITERAL " + yytext();
      case DOUBLE_LITERAL:
        return "T_DOUBLELITERAL " + yytext();
      case IDENTIFIER:
        return "T_ID " + currentSymbol.value.toString();
      case EOF:
          throw new Exception("This is the end");
      default:
            return yytext();
    }
  }
%}

/* main character classes */

Identifier = [a-z|A-Z][a-z|A-Z|0-9|_]*

LineTerminator = \r|\n|\r\n
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

StringCharacter = [^\r\n\"\\]

%state STRING

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
  "return"      { return symbol(RETURN); }
  "break"       { return symbol(BREAK); }
  "continue"    { return symbol(CONTINUE); }
  "new"         { return symbol(NEW); }
  "NewArray"    { return symbol(NEWARRAY); }
  "print"       { return symbol(PRINT); }
  "ReadInteger" { return symbol(READINTEGER); }
  "ReadLine"    { return symbol(READLINE); }
  "dtoi"        { return symbol(DTOI); }
  "itod"        { return symbol(ITOD); }
  "btoi"        { return symbol(BTOI); }
  "itob"        { return symbol(ITOB); }
  "private"     { return symbol(PRIVATE); }
  "protected"   { return symbol(PROTECTED); }
  "public"      { return symbol(PUBLIC); }

  /* boolean literals */
  "true"                         { return symbol(BOOLEAN_LITERAL, true); }
  "false"                        { return symbol(BOOLEAN_LITERAL, false); }
  
  /* null literal */
  //"null"                         { return symbol(NULL_LITERAL); }
  
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
  "%"                            { return symbol(MOD); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }

  /* numeric literals */

  /* This is matched together with the minus, because the number is too big to 
     be represented by a positive integer. */

  {DecIntegerLiteral}            { return symbol(INTEGER_LITERAL, Integer.valueOf(yytext())); }

  {HexIntegerLiteral}            { return symbol(INTEGER_LITERAL, Integer.valueOf((int) parseLong(2, yylength(), 16))); }

  {DoubleLiteral}                { return symbol(DOUBLE_LITERAL, new Double(yytext())); }
  {DoubleScientificLiteral}      { return symbol(DOUBLE_LITERAL, new Double(yytext())); }
  /* comments */
  {Comment}                      { /* ignore */ }

  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }

  /* identifiers */
  {Identifier}                   { return symbol(IDENTIFIER, yytext()); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return symbol(STRING_LITERAL, string.toString()); }

  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\t"                          { string.append( '\t' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\b"                          { string.append( '\b' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }

  /* error cases */
  {LineTerminator}               { return symbol(UNDIFIENED_TOKEN); }
}

/* error fallback */
[^]                              { return symbol(UNDIFIENED_TOKEN); }
<<EOF>>                          { return symbol(EOF); }