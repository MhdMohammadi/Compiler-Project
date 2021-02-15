import java.sql.Struct;
import java.util.ArrayList;

public class Compiler {
    private Node root;


    private int cnt = 0;

    public Compiler(Node root) {
        this.root = root;
    }

    public static void semanticError() {
        System.out.println("semantic error");
        System.exit(0);
    }

    public void preProcess(Node v) {
        v.setIndex(cnt);
        for (Variable variable : v.getDefinedVariables())
            variable.setNodeIndex(v.getIndex());
        cnt++;
        for (Node node : v.getChildren()) {
            node.setParent(v);
            preProcess(node);
        }
    }

    public Variable findVariable(Node node, String name) {
        Node node1 = node;
        while (true) {
            for (Variable variable : node1.getDefinedVariables()) {
                if (variable.getName().equals(name)) {
                    if(node.getLeftHand() == LeftHand.ClassDecl || node.getLeftHand() == LeftHand.Program || variable.getNumber() < node.getIndex())
                    {
                        return variable;
                    }
                }
            }
            if (node1.getParent() == null) {
                break;
            } else {
                node1 = node1.getParent();
            }
        }
        semanticError();
        return null;
    }

    public Function findFunction(Node node, String name) {
        Node node1 = node;
        while (true) {
            for (Function function : node1.getDefinedFunctions()) {
                if (function.getName().equals(name)) {
                    return function;
                }
            }
            if (node1.getParent() == null) {
                break;
            } else {
                node1 = node1.getParent();
            }
        }
        semanticError();
        return null;
    }

    public void debug(Node v){
        System.out.println(v.getLeftHand() + " " + v.getProductionRule());
        Type type = v.getType();
        if(type == null) System.out.println("NULL");
        else System.out.println(type.getName() + " " + type.getParent());
        for(Node node : v.getChildren())
            debug(node);
    }

    public void setVariablesType(Node v) {
        //todo
        if (v.getLeftHand() == LeftHand.Variable) {
            Type type = Type.getTypeByName((String) v.getChildren().get(0).getTypeName(), v.getChildren().get(0).getArrayDegree());
            if (type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for (Node node : v.getChildren())
            setVariablesType(node);
    }

    public void setArraysType(Node v){
        for(Node node : v.getChildren())
            setArraysType(node);
        if(v.getLeftHand() == LeftHand.Type && v.getProductionRule() != ProductionRule.Type_OPENCLOSEBRACKET){
            v.setType(Type.getTypeByName(v.getTypeName(), 0));
        }
        if(v.getLeftHand() == LeftHand.Type && v.getProductionRule() == ProductionRule.Type_OPENCLOSEBRACKET) {
            if(v.getArrayDegree() == 1)
                v.setType(Type.createArrayType(Type.getTypeByName(v.getTypeName(), 0)));
            else
                v.setType(Type.createArrayType(v.getChildren().get(0).getType()));
        }
    }

    public void setFunctionType(Node v) {
        //todo
        if (v.getLeftHand() == LeftHand.FunctionDecl) {
            Function function = v.getDefinedFunctions().get(0);

            Type type = Type.getTypeByName(v.getTypeName(), v.getArrayDegree());
        //    System.out.println(v.getTypeName() + " " + v.getArrayDegree());
            if (type == null) Compiler.semanticError();
            function.setType(type);
        }
        for (Node node : v.getChildren())
            setFunctionType(node);
    }

    public void setClazzType(){
        for(Clazz clazz: Clazz.getClazzes()){
            clazz.setType(Type.getTypeByName(clazz.getName(), 0));
        }
    }

    public void setAllClazzAttributesAndFunctions(){
        for (Clazz clazz : Clazz.getClazzes())
            setClazzAttributesAndFunctions(clazz);
    }

    public boolean haveSameSignature(Function function1, Function function2){
        if (!function1.getType().equals(function2.getType())) return false;
        if (function1.getParameter().size() != function2.getParameter().size()) return false;
        int index = 0;
        for(Variable variable1 : function1.getParameter()){
            Variable variable2 = function2.getParameter().get(index);
            if (!variable1.getType().equals(variable2.getType()))   return false;
            index++;
        }
        return true;
    }

    public boolean isConvertibleTo(Variable convertVariable, Variable mainVariable){
        Type type = convertVariable.getType();
        if (mainVariable.getType().equals(type)) return true;
        while (type.getParent() != null){
            type = type.getParent();
            if(type.equals(mainVariable.getType()))return true;
        }
        return false;
    }

    public boolean areFunctionCallParametersCorrect(Function function, ArrayList<Variable> parameters){
        if (function.getParameter().size() != parameters.size())return false;
        int index = 0;
        for (Variable variable : function.getParameter()){
            if (!isConvertibleTo(parameters.get(index), variable))
                return false;
            index++;
        }
        return true;
    }

    public ArrayList<Function> mergeFunctions(ArrayList<Function> parFunctions, ArrayList<Function> functions){
        ArrayList<Function> mergedFunctions = new ArrayList<>();
        functions.addAll(parFunctions);

        for (Function function : functions){
            boolean find = false;
            int index = 0;
            for (Function parFunction : mergedFunctions){
                if(parFunction.getName().equals(function.getName())){
                    find = true;
                    if(haveSameSignature(parFunction, function)){
                        mergedFunctions.set(index, function);
                    }
                    else Compiler.semanticError();
                }
                index++;
            }
            if (find == false)mergedFunctions.add(function);
        }
        return mergedFunctions;
    }

    public ArrayList<Variable> mergeVariables(ArrayList<Variable> parVariables, ArrayList<Variable> variables){
        ArrayList<Variable> mergedVariables = new ArrayList<>();
        mergedVariables.addAll(parVariables);
        mergedVariables.addAll(variables);
        if (areArrayListVariablesUnique(mergedVariables) == false) semanticError();
        return mergedVariables;
    }

    public void setClazzAttributesAndFunctions(Clazz clazz){
        clazz.setSetAttributesAndFunctions(true);
        Clazz parentClazz = clazz.getParent();
        if(parentClazz == null)return;
        if(parentClazz.isSetAttributesAndFunctions() == false) setClazzAttributesAndFunctions(parentClazz);


        clazz.setFunctions(mergeFunctions(parentClazz.getFunctions(), clazz.getFunctions()));
        clazz.setVariables(mergeVariables(parentClazz.getVariables(), clazz.getVariables()));

    }

    public void areAllVariablesUnique(Node v) {
        ArrayList<Variable> variables = v.getDefinedVariables();
        if (areArrayListVariablesUnique(variables) == false) semanticError();

        for (Node node : v.getChildren())
            areAllVariablesUnique(node);
    }

    public boolean areArrayListVariablesUnique(ArrayList<Variable> variables){
        for (int i = 0; i < variables.size(); i ++){
            for(int j = i + 1; j < variables.size(); j ++){
                if (variables.get(i).getName().equals(variables.get(j).getName()))
                    return false;
            }
        }
        return true;
    }

    // age be terminal bere, type bayad moshakhas shode bashe
    public void setAllNodesType(Node v){
        for(Node node : v.getChildren())
            setAllNodesType(node);
        switch (v.getLeftHand()){
            case Constant:
                switch (v.getProductionRule()) {
                    case INTLITERAL:
                        v.setType(Type.getTypeByName("int", 0));
                        break;
                    case DOUBLELITERAL:
                        v.setType(Type.getTypeByName("double", 0));
                        break;
                    case BOOLEANLITERAL:
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case STRINGLITERAL:
                        v.setType(Type.getTypeByName("string", 0));
                        break;
                    case NULL:
                        //todo
                        //what's NULL's type?
                        break;
                }
                break;
            case Call:
                switch (v.getProductionRule()) {
                    case IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                        Function function = findFunction(v, (String)v.getChildren().get(0).getValue());
                        v.setType(function.getType());
                        break;
                    case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                        //todo
                        break;
                }
                break;
            case LValue:
                switch (v.getProductionRule()) {
                    case IDENTIFIER:
                        Variable variable = findVariable(v, (String) v.getChildren().get(0).getValue());
                        v.setType(variable.getType());
                        break;
                    case Expr_DOT_IDENTIFIER:
                        Type type = v.getChildren().get(0).getType();
                        //todo
                        break;
                    case Expr_OPENBRACKET_Expr_CLOSEBRACKET:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                }
                break;
            case Expr:
                switch (v.getProductionRule()) {
                    case LValue_ASSIGN_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.EQ))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
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
                    case OPENPARENTHESIS_Expr_CLOSEPARENTHESIS:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_PLUS_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.PLUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MINUS_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MINUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MULTIPLY_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MULT))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_DIVIDE_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.DIV))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_MOD_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.MOD))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case MINUS_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), Operator.SINGLE_MINUS))
                            semanticError();
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case Expr_LESS_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_LESSEQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_GREATER_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_GREATEREQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_EQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.EQEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_NOTEQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.NOTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_AND_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.ANDAND))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case Expr_OR_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.OROR))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case NOT_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), Operator.SINGLE_NOT))
                            semanticError();
                        v.setType(Type.getTypeByName("boolean", 0));
                        break;
                    case READINTEGER_OPENPARENTHESIS_CLOSEPARENTHESIS:
                        v.setType(Type.getTypeByName("int", 0));
                        break;
                    case READLINE_OPENPARENTHESIS_CLOSEPARENTHESIS:
                        v.setType(Type.getTypeByName("string", 0));
                        break;
                    case NEW_IDENTIFIER:
                        v.setType(v.getChildren().get(0).getType());
                        break;
                    case NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS:
                        v.setType(Type.createArrayType(v.getChildren().get(1).getType()));
                        break;
                }
                break;
        }
    }

    void checkIntegerIndices(Node v){
        if(v.getProductionRule() == ProductionRule.NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS){
            Type t = v.getChildren().get(0).getType();
            if(t != Type.getTypeByName("int", 0))
                semanticError();
        }
        if(v.getProductionRule() == ProductionRule.Expr_OPENBRACKET_Expr_CLOSEBRACKET){
            Type t = v.getChildren().get(0).getType();
            if(t != Type.getTypeByName("int", 0))
                semanticError();
        }
        for(Node node : v.getChildren())
            checkIntegerIndices(node);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
