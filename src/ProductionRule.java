public enum ProductionRule{
    Program ::=             Decl_DeclStar;

    DeclStar ::=            Decl_DeclStar
						| /*epsilon*/
    ;

    Decl ::=                VariableDecl
						| FunctionDecl
						| ClassDecl
						| InterfaceDecl
    ;

    VariableDecl ::=        Variable_SEMICOLON;

    Variable ::=            Type_IDENTIFIER:
    ;

    Type ::=                INT
						| DOUBLE
						| BOOL
						| STRING
                        | IDENTIFIER
						| Type_OPENCLOSEBRACKET
            ;

    FunctionDecl ::=        Type_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_StmtBlock
						| VOID_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_StmtBlock
    ;

    Formals ::=             Variable_CommaVariables
						| /*epsilon*/
    ;
    CommaVariables ::=      COMMA_Variable_CommaVariables
						| /*epsilon*/
    ;

    ClassDecl ::=           CLASS_IDENTIFIER_ClassDeclExtends_ClassDeclImplements_OPENCURLYBRACES_FieldStar_CLOSECURLYBRACES;
    ClassDeclExtends ::=    EXTENDS_IDENTIFIER
						| /*epsilon*/
    ;
    ClassDeclImplements ::= IMPLEMENTS_IDENTIFIER_CommaIdentifiers
						| /*epsilon*/
    ;
    CommaIdentifiers ::=    COMMA_IDENTIFIER_CommaIdentifiers
						| /*epsilon*/
    ;
    FieldStar ::=           Field_FieldStar
						| /*epsilon*/
    ;

    Field ::=               AccessMode_VariableDecl
						| AccessMode_FunctionDecl
            ;

    AccessMode ::=          PRIVATE
						| PROTECTED
						| PUBLIC
						| /*epsilon*/
    ;

    InterfaceDecl ::=       INTERFACE_IDENTIFIER_OPENCURLYBRACES_PrototypeStar_CLOSECURLYBRACES;
    PrototypeStar ::=       Prototype_PrototypeStar
						| /*epsilon*/
    ;

    Prototype ::=           Type_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_SEMICOLON
						| VOID_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_SEMICOLON
            ;

    StmtBlock ::=           OPENCURLYBRACES_InsideStmtBlock_CLOSECURLYBRACES;
    InsideStmtBlock ::=     VariableDecl_InsideStmtBlock
						| StmtStar
    ;
    StmtStar ::=            Stmt_StmtStar
						| /*epsilon*/
    ;

    Stmt ::=                ExprPrime_SEMICOLON
						| IfStmt
						| WhileStmt
						| ForStmt
						| BreakStmt
						| ContinueStmt
						| ReturnStmt
						| PrintStmt
						| StmtBlock
    ;

    ExprPrime ::=           Expr
						| /*epsilon*/
    ;

    IfStmt ::=              IF_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS_Stmt_ElsePrime
            ;

    ElsePrime ::=           ELSE_Stmt
						| /*epsilon*/
    ;


    WhileStmt ::=           WHILE_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS_Stmt

    ForStmt ::=             FOR OPENPARENTHESIS_ExprPrime_SEMICOLON_Expr_SEMICOLON_ExprPrime_CLOSEPARENTHESIS_Stmt;

    ReturnStmt ::=          RETURN ExprPrime_SEMICOLON
            ;

    BreakStmt ::=           BREAK_SEMICOLON;

    ContinueStmt ::=        CONTINUE_SEMICOLON;

    PrintStmt ::=           PRINT OPENPARENTHESIS_Expr_PrintCommaExpr_CLOSEPARENTHESIS_SEMICOLON
            ;

    PrintCommaExpr ::=      COMMA Expr_PrintCommaExpr
						|
    /*epsilon*/
    ;

    Expr ::=                LValue_ASSIGN_Expr
						| Constant
						| LValue
						| THIS
						| Call
						| OPENPARENTHESIS_Expr_CLOSEPARENTHESIS
						| Expr_PLUS_Expr
						| Expr_MINUS_Expr
						| Expr_MULTIPLY_Expr
						| Expr_DIVIDE_Expr
						| Expr_MOD_Expr
						| MINUS_Expr
                        | Expr_LESS_Expr
						| Expr_LESSEQUAL_Expr
						| Expr_GREATER_Expr
						| Expr_GREATEREQUAL_Expr
						| Expr_EQUAL_Expr
						| Expr_NOTEQUAL_Expr
						| Expr_AND_Expr
						| Expr_OR Expr
						| NOT_Expr
						| READINTEGER_OPENPARENTHESIS_CLOSEPARENTHESIS
						| READLINE_OPENPARENTHESIS_CLOSEPARENTHESIS
						| NEW_IDENTIFIER
						| NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS
						| ITOD_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS
						| DTOI_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS
						| ITOB_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS
						| BTOI_OPENPARENTHESIS_Expr_CLOSEPARENTHESIS
            ;

    LValue ::=              IDENTIFIER
						| Expr_DOT_IDENTIFIER
						| Expr_OPENBRACKET_Expr_CLOSEBRACKET
            ;

    Call ::=                IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS
						| Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS
            ;

    Actuals ::=             Expr_ActualsCommaExpr
						| /*epsilon*/
    ;

    ActualsCommaExpr ::=    COMMA_Expr_ActualsCommaExpr
						| /*epsilon*/
    ;

    Constant ::=            DECIMAL
                        | FLOATINGPOINT
						| BOOLEANLITERAL
						| STRINGLITERAL
						| NULL
    ;
}