import java.util.ArrayList;

public class Compiler {
    private Node root;
    private int cnt = 0;

    public Compiler(Node root){
        this.root = root;
    }

    public static void semanticError(){
        System.out.println("semantic error");
        System.exit(0);
    }

    public void preProcess(Node v){
        v.setIndex(cnt);
        for(Variable variable : v.getDefinedVariables())
            variable.setNodeIndex(v.getIndex());
        cnt ++;
        for (Node node: v.getChildren()) {
            node.setParent(v);
            preProcess(node);
        }
    }

    public Variable findVariable(Node node){
        Node node1 = node;
        while (true){
            for (Variable variable: node1.getDefinedVariables()){
                if (variable.getName().equals((String)node.getValue())){
                    if (variable.getNumber() < node.getIndex()) {
                        return variable;
                    }
                }
            }
            if (node1.getParent() == null){
                break;
            } else {
                node1 = node1.getParent();
            }
        }
        return null;
    }

    public Function findFunction(Node node){
        Node node1 = node;
        while (true){
            for (Function function: node1.getDefinedFunctions()){
                if (function.getName().equals((String)node.getValue())){
                    return function;
                }
            }
            if (node1.getParent() == null){
                break;
            } else {
                node1 = node1.getParent();
            }
        }
        return null;
    }


    public void setVariablesType(Node v){
        if(v.getLeftHand() == LeftHand.Variable){
            Type type = Type.getTypeByName((String)v.getChildren().get(0).getTypeName());
            if(type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for(Node node : v.getChildren())
            setVariablesType(node);
    }

    public void setFunctionType(Node v){
        if(v.getLeftHand() == LeftHand.FunctionDecl){
            Type type = Type.getTypeByName((String)v.getChildren().get(0).getValue());
            if(type == null) Compiler.semanticError();
            v.getDefinedFunctions().get(0).setType(type);
        }
        for(Node node : v.getChildren())
            setFunctionType(node);
    }

    public void areAllVariablesUnique(Node v){
        ArrayList<Variable> variables = v.getDefinedVariables();
        for(int i = 0; i < variables.size(); i++)
            for(int j = i + 1; j < variables.size(); j++) {
                if (variables.get(i).getName().equals(variables.get(j).getName()))
                    semanticError();
            }
        for(Node node : v.getChildren())
            areAllVariablesUnique(node);
    }

    public void setAllNodesType(Node v){
        for(Node node : v.getChildren())
            setVariablesType(node);
        switch (v.getLeftHand()){
            case Constant:
                switch (v.getProductionRule()){
                    case INTEGER_LITERAL:
                        v.setType(Type.getTypeByName("int"));
                        break;
                    case DOUBLE_LITERAL:
                        v.setType(Type.getTypeByName("double"));
                        break;
                    case BOOLEAN_LITERAL:
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case STRING_LITERAL:
                        v.setType(Type.getTypeByName("string"));
                        break;
                    case NULL:
                        //todo
                        //what's NULL's type?
                        break;
                }
                break;
            case Call:
                switch (v.getProductionRule()){
                    case IDENTIFIER_LPAREN_Actuals_RPAREN:
                        //todo
                        break;
                    case Expr_DOT_IDENTIFIER_LPAREN_Actuals_RPAREN:
                        //todo
                        break;
                }
                break;
            case LValue:
                switch (v.getProductionRule()){
                    case IDENTIFIER:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_DOT_IDENTIFIER:
                        Type type = v.getChildren().get(0).getType();
                        //todo
                        break;
                    case Expr_LBRACK_Expr_RBRACK:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                }
                break;
            case Expr:
                switch (v.getProductionRule()){
                    case LValue_EQ_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.EQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Constant:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case LValue:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case THIS:
                        // todo
                        break;
                    case Call:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case LPAREN_Expr_RPAREN:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_PLUS_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.PLUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MINUS_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MINUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MULT_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MULT))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_DIV_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.DIV))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MOD_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MOD))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case MINUS_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), Operator.SINGLE_MINUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_LT_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_LTEQ_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_GT_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_GTEQ_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_EQEQ_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.EQEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_NOTEQ_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.NOTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_ANDAND_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.ANDAND))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case Expr_OROR_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.OROR))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case NOT_Expr:
                        if(!Type.possible(v.getChildren().get(0).getType(), Operator.SINGLE_NOT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean"));
                        break;
                    case READINTEGER_LPAREN_RPAREN:
                        v.setType(Type.getTypeByName("int"));
                        break;
                    case READLINE_LPAREN_RPAREN:
                        v.setType(Type.getTypeByName("string"));
                        break;
                    case NEW_IDENTIFIER:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case NEWARRAY_LPAREN_Expr_COMMA_Type_RPAREN:
                        v.setType(Type.createArrayType(v.getChildren().get(1).getType()));
                        break;
                }
                break;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
