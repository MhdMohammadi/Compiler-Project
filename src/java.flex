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

  public String yylex() throws Exception {
    java_cup.runtime.Symbol currentSymbol = next_token();
    switch (currentSymbol.sym){
      case BOOLEAN_LITERAL:
        return "T_BOOLEANLITERAL " + yytext();
      case INTEGER_LITERAL:
        return "T_INTLITERAL " + yytext();
      case STRING_LITERAL:
        return "T_STRINGLITERAL \"" + currentSymbol.value.toString() + "\"";
      case DOUBLE_LITERAL:
        return "T_DOUBLELITERAL " + yytext();
      case IDENTIFIER:
        return "T_ID " + currentSymbol.value.toString();
      case EOF:
          throw new Exception("EOF");
      case UNDEFINED_TOKEN:
          throw new Exception("UNDEFINED_TOKEN");
      default:
            return yytext();
    }
  }

%}

%type Symbol
%state STRING

alphabet = [A-Za-z]
digit = [0-9]
underLine = "_"
whiteSpace = {endLine} | [ \t\f]
id = {alphabet}({alphabet} | {digit} | {underLine})*
endLine = \n|\r|\r\n
decimal = ({digit})+
hexadigit = {digit} | [a-fA-F]
hexadecimal = 0[xX]({hexadigit})+
floatingPoint = ({digit}+\.{digit}*)
scientificFloat = {floatingPoint}[Ee][+-]?{decimal}
floatingPointAll = {floatingPoint} | {scientificFloat}
inputCharacter = [^\r\n]
singleLineComment = \/\/{inputCharacter}*
multiLineComment = \/\*~\*\/
%%

<YYINITIAL> {

	"void"          {return token(sym.VOID, yytext()); }
	"int"           {return token(sym.INT, yytext()); }
	"double"        {return token(sym.DOUBLE, yytext()); }
	"bool"          {return token(sym.BOOL, yytext()); }
	"string"        {return token(sym.STRING, yytext()); }
	"class"         {return token(sym.CLASS, yytext()); }
	"interface"     {return token(sym.INTERFACE, yytext()); }
	"null"          {return token(sym.NULL, yytext()); }
	"this"          {return token(sym.THIS, yytext()); }
	"extends"       {return token(sym.EXTENDS, yytext()); }
	"implements"    {return token(sym.IMPLEMENTS, yytext()); }
	"for"           {return token(sym.FOR, yytext()); }
	"while"         {return token(sym.WHILE, yytext()); }
	"if"            {return token(sym.IF, yytext()); }
	"else"          {return token(sym.ELSE, yytext()); }
	"return"        {return token(sym.RETURN, yytext()); }
	"break"         {return token(sym.BREAK, yytext()); }
	"continue"      {return token(sym.CONTINUE, yytext()); }
	"new"           {return token(sym.NEW, yytext()); }
	"NewArray"      {return token(sym.NEWARRAY, yytext()); }
	"Print"         {return token(sym.PRINT, yytext()); }
	"ReadInteger"   {return token(sym.READINTEGER, yytext()); }
	"ReadLine"      {return token(sym.READLINE, yytext()); }
	"dtoi"          {return token(sym.DTOI, yytext()); }
	"itod"          {return token(sym.ITOD, yytext()); }
	"btoi"          {return token(sym.BTOI, yytext()); }
	"itob"          {return token(sym.ITOB, yytext()); }
	"private"       {return token(sym.PRIVATE, yytext()); }
	"protected"     {return token(sym.PROTECTED, yytext()); }
	"public"        {return token(sym.PUBLIC, yytext()); }
	"true"          {return token(sym.BOOLEANLITERAL, yytext()); }
	"false"         {return token(sym.BOOLEANLITERAL, yytext()); }


	"+"     {return token(sym.PLUS, yytext()); }
	"-"     {return token(sym.MINUS, yytext()); }
	"*"     {return token(sym.MULTIPLY, yytext()); }
	"/"     {return token(sym.DIVIDE, yytext()); }
	"%"     {return token(sym.MOD, yytext()); }
	"<"     {return token(sym.LESS, yytext()); }
	"<="    {return token(sym.LESSEQUAL, yytext()); }
	">"     {return token(sym.GREATER, yytext()); }
	">="    {return token(sym.GREATEREQUAL, yytext()); }
	"="     {return token(sym.ASSIGN, yytext()); }
	"=="    {return token(sym.EQUAL, yytext()); }
	"!="    {return token(sym.NOTEQUAL, yytext()); }
	"&&"    {return token(sym.AND, yytext()); }
	"||"    {return token(sym.OR, yytext()); }
	"!"     {return token(sym.NOT, yytext()); }
	";"     {return token(sym.SEMICOLON, yytext()); }
	","     {return token(sym.COMMA, yytext()); }
	"."     {return token(sym.DOT, yytext()); }

	"{"    {return token(sym.OPENCURLYBRACES, yytext()); }
	"}"    {return token(sym.CLOSECURLYBRACES, yytext()); }
	"("    {return token(sym.OPENPARENTHESIS, yytext()); }
	")"    {return token(sym.CLOSEPARENTHESIS, yytext()); }
	"[]"   {return token(sym.OPENCLOSEBRACKET, yytext()); }
	"["    {return token(sym.OPENBRACKET, yytext()); }
	"]"    {return token(sym.CLOSEBRACKET, yytext()); }
//
//
	{decimal}               {return token(sym.DECIMAL, yytext());}
	{floatingPointAll}      {return token(sym.FLOATINGPOINT, yytext());}
	{hexadecimal}           {return token(sym.DECIMAL, yytext()); }
	{id}                    {return token(sym.IDENTIFIER, yytext());}
	{whiteSpace}            {;}
	{singleLineComment}     {;}
	{multiLineComment}      {;}
	"\""                    {yybegin(STRING); string = "" + yytext();}
}
<STRING> {
	"\""    {
		yybegin(YYINITIAL);
		return token(sym.STRINGLITERAL, string + yytext());
		}
	 .      {string = string + yytext();}
}


//<<EOF>>             { tokenType == sym.EOF; }