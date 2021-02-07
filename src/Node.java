import java.util.ArrayList;

enum NonTerminal{
    STRING, VOID, INT, DOUBLE, BOOL,
    CLASS,
    INTERFACE,
    NULL,
    THIS,
    EXTENDS,
    IMPLEMENTS,
    FOR,
    WHILE,
    IF, ELSE,
    RETURN,
    BREAK,
    CONTINUE,
    NEW,
    NEWARRAY,
    PRINT,
    READINTEGER,
    READLINE,
    DTOI,
    ITOB,
    ITOD,
    BTOI,
    PRIVATE, PUBLIC, PROTECTED,
    TRUE, FALSE,
    GT, LT, GTEQ, LTEQ,
    QUESTION,
    EQEQ, NOTEQ,
    ANDAND,
    OROR,
    PLUSPLUS,
    MINUSMINUS,
    PLUS, MINUS, DIV, COMP, NOT, MOD,
    DOT,
    LBRACK, RBRACK,
    LPAREN, RPAREN, LBRACE, RBRACE, COMMA, EQ, MULT, SEMICOLON, COLON,
    UNDEFINED_TOKEN,


    INTEGER_LITERAL,
    DOUBLE_LITERAL,
    STRING_LITERAL,
    IDENTIFIER,
    BOOLEAN_LITERAL,


    //in ja benevisiiiiid
    Program,
    Dec,
    VariableDecl,
    Variable,
    Type,
    FunctionDecl,
    Formals,
    ClassDecl,
    Field,


    AccessMode,
    InterfaceDecl,
    Prototype,
    StmtBlock,
    Stmt,
    IfStmt,
    WhileStmt,
    ForStmt,

    ReturnStmt,
    BreakStmt,
    ContinueStmt,
    PrintStmt,
    Expr,
    LValue,
    Call,
    Actuals,
    Constant,

    DeclPlus,
    VariablePlus,
    ExtendStmt,
    ImplementsStmt,
    IdentifierImplementsStmt,
    FieldStar,
    Prototype_star,
    VariableDecl_star,
    Stmt_star,
    Expr_Stmt,
    else_Stmt,
    Expr_plus,
}

enum ProductionRule{
    DeclPlus,
    Decl , Decl_DeclPlus,
    VariableDecl , FunctionDecl , ClassDecl , InterfaceDecl,
    Variable_SEMICOLON,
    Type_IDENTIFIER,
    Type_LBRACK_RBRACK , IDENTIFIER_MULT , INT , DOUBLE , STRING , BOOL,
    Type_IDENTIFIER_LPAREN_Formals_RPAREN_StmtBlock , VOID_IDENTIFIER_LPAREN_Formals_RPAREN_StmtBlock,
    Variable , Variable_COMMA_VariablePlus,
    VariablePlus ,
    EXTENDS_IDENTIFIER ,
    IMPLEMENTS_IdentifierImplementsStmt,
    IDENTIFIER_COMMA_IdentifierImplementsStmt , IDENTIFIER,
    Field_FieldStar,
    AccessMode_VariableDecl, AccessMode,
    CLASS_IDENTIFIER_ExtendStmt_ImplementsStmt_LBRACE_FieldStar_RBRACE,
    PRIVATE, PROTECTED, PUBLIC,
    INTERFACE_IDENTIFIER_LBRACE_Prototype_star_RBRACE,
    Prototype_Prototype_star,
    Type_IDENTIFIER_LPAREN_Formals_RPAREN_SEMICOLON, VOID_IDENTIFIER_LPAREN_Formals_RPAREN_SEMICOLON,
    LBRACE_VariableDecl_star_Stmt_star_RBRACE,
    VariableDecl_VariableDecl_star_PLUS,
    Stmt_Stmt_star,
    Expr_Stmt_SEMICOLON, IfStmt, WhileStmt, ForStmt, BreakStmt, ContinueStmt, ReturnStmt, PrintStmt, StmtBlock,
    Expr,
    IF_LPAREN_Expr_RPAREN_Stmt_else_Stmt,
    ELSE_Stmt,
    WHILE_LPAREN_Expr_RPAREN_Stmt,
    FOR_LPAREN_Expr_Stmt_SEMICOLON_Expr_SEMICOLON_Expr_Stmt_RPAREN_Stmt,

    RETURN_Expr_SEMICOLON , RETURN_SEMICOLON,
    BREAK_SEMICOLON,
    CONTINUE_SEMICOLON,
    PRINT_LPAREN_Expr_plus_RPAREN_SEMICOLON,
    Expr_COMMA_Expr_plus,

    LValue_EQ_Expr, Constant, LValue, THIS, Call, LPAREN_Expr_RPAREN, Expr_PLUS_Expr,
    Expr_MINUS_Expr, Expr_MULT_Expr, Expr_DIV_Expr, Expr_MOD_Expr, MINUS_Expr, Expr_LT_Expr, Expr_LTEQ_Expr,
    Expr_GT_Expr, Expr_GTEQ_Expr, Expr_EQEQ_Expr, Expr_NOTEQ_Expr, Expr_ANDAND_Expr, Expr_OROR_Expr,
    NOT_Expr, READINTEGER_LPAREN_RPAREN, READLINE_LPAREN_RPAREN, NEW_IDENTIFIER, NEWARRAY_LPAREN_Expr_COMMA_Type_RPAREN,
    ITOD_LPAREN_Expr_RPAREN, DTOI_LPAREN_Expr_RPAREN, ITOB_LPAREN_Expr_RPAREN, BTOI_LPAREN_Expr_RPAREN,
    Expr_DOT_IDENTIFIER, Expr_LBRACK_Expr_RBRACK,
    IDENTIFIER_LPAREN_Actuals_RPAREN, Expr_DOT_IDENTIFIER_LPAREN_Actuals_RPAREN,
    Expr_plus,
    INTEGER_LITERAL , DOUBLE_LITERAL , BOOLEAN_LITERAL , STRING_LITERAL , NULL;
}

enum Type{
    INT, DOUBLE, STRING, BOOLEAN
}

public class Node {
    private NonTerminal nonTerminal;
    private ProductionRule productionRule;
    private ArrayList<Node> children;
    private Object value;
    private Type type;

    public Node(NonTerminal nonTerminal, ProductionRule productionRule){
        this.nonTerminal = nonTerminal;
        this.productionRule = productionRule;
    }

    public NonTerminal getNonTerminal() {
        return nonTerminal;
    }

    public void setNonTerminal(NonTerminal nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public ProductionRule getProductionRule() {
        return productionRule;
    }

    public void setProductionRule(ProductionRule productionRule) {
        this.productionRule = productionRule;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}