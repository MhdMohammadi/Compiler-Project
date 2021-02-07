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

enum ProductionRule{}

enum Type{}

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
}
