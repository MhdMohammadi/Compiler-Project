package Controller;

import Model.Node;
import Model.Type;
import Model.*;
import Enum.*;
import Parser.parser;
import Scanner.MyScanner;

import java.io.*;
import java.util.ArrayList;

public class Compiler {
    private Node root;
    private int cnt = 0;
    CodeGenerator codeGenerator = new CodeGenerator();
    private static Code finalCode = new Code();
    private static String inputFileName, outputFileName;

    public static void semanticError() {
        finalCode = generateSemanticErrorCode();
        System.out.println(finalCode.getText());
        try {
            writeToFile(Compiler.outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static Code generateSemanticErrorCode(){
        Code code = new Code();
        code.addCode(".text");
        code.addCode(".globl main");
        code.addCode("main:");
        code.addCode("la $a0, errorMsg");
        code.addCode("addi $v0, $zero, 4");
        code.addCode("syscall");
        code.addCode("jr $ra");
        code.addCode(".data");
        code.addCode("errorMsg: .assciiz \"SemanticError\"");
        return code;
    }


    public static void writeToFile( String outputFileName ) throws IOException {
        System.out.println(outputFileName);
        FileWriter out = new FileWriter( outputFileName );
        out.write( finalCode.getText());
        out.close();
    }

    public void compile(String inputFileName, String outputFileName) throws Exception {
        Compiler.inputFileName = inputFileName;
        Compiler.outputFileName = outputFileName;
        Reader reader;
        if (inputFileName != null)
            reader = new FileReader("Tests/" + inputFileName);
        else
            reader = new FileReader("tests/t01.in");
        parser p = new parser(new MyScanner(reader));
        p.parse();
        this.setRoot(parser.root);

        // detect semantic errors
        preProcess(root); // assign indices to parse tree
        Type.createTypes(); // create all types and construct tree of types
        createArrays(root); // create arrays and add them to types & set type of each Type node
        createBuiltinFunctions(root); // btoi, itob, dtoi, itod
        areAllVariablesUnique(root); // are there variables with the same name in a scope?
        areAllFunctionsUnique(root); // are there functions with the same name in a class?
        setVariableType(root); // set the proper type for each variable
        setFunctionType(root); // set the proper type for each function
        setClazzType(); // set the proper type for each class
        setAllClazzAttributesAndFunctions();
        setAllNodesType(root); // set the proper type for Constant, Call, Lvalue and Expr
        checkIntegerIndices(root); // check type of indices and count in NewArray
        checkFunctionCalls(root);

        // produce the final code
        checkReturnTypes(root);
        setFunctionLabels(root);
        codeGenerator.generateCode(root);
        //todo
        finalCode = codeGenerator.createFinalCode(root);
        writeToFile(outputFileName);
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

    public void createArrays(Node v) {
        for (Node node : v.getChildren())
            createArrays(node);
        if (v.getLeftHand() == LeftHand.Type && v.getProductionRule() != ProductionRule.Type_OPENCLOSEBRACKET) {
            v.setType(Type.getTypeByName(v.getTypeName(), 0));
        }
        if (v.getLeftHand() == LeftHand.Type && v.getProductionRule() == ProductionRule.Type_OPENCLOSEBRACKET) {
            if (v.getArrayDegree() == 1)
                v.setType(Type.createArrayType(Type.getTypeByName(v.getTypeName(), 0)));
            else
                v.setType(Type.createArrayType(v.getChildren().get(0).getType()));
        }
    }

    private void createBuiltinFunctions(Node root) {
        Function itob = new Function();
        initializeFunction(itob, "itob", "int", "bool");
        Function btoi = new Function();
        initializeFunction(btoi, "btoi", "bool", "int");
        Function dtoi = new Function();
        initializeFunction(dtoi, "dtoi", "double", "int");
        Function itod = new Function();
        initializeFunction(itod, "itod", "int", "double");

        root.getDefinedFunctions().add(itob);
        root.getDefinedFunctions().add(btoi);
        root.getDefinedFunctions().add(dtoi);
        root.getDefinedFunctions().add(itod);
    }

    private void initializeFunction(Function x, String name, String input, String output) {
        Variable tmp = new Variable();
        tmp.setName("x");
        tmp.setType(Type.getTypeByName(input, 0));
        x.getParameter().add(tmp);
        x.setName(name);
        x.setType(Type.getTypeByName(output, 0));
        Label label = new Label(); label.createNewName();
        x.setLabel(label);
    }

    public void areAllVariablesUnique(Node v) {
        ArrayList<Variable> variables = v.getDefinedVariables();
        if (areArrayListVariablesUnique(variables) == false) semanticError();

        for (Node node : v.getChildren())
            areAllVariablesUnique(node);
    }

    public boolean areArrayListVariablesUnique(ArrayList<Variable> variables) {
        for (int i = 0; i < variables.size(); i++) {
            for (int j = i + 1; j < variables.size(); j++) {
                if (variables.get(i).getName().equals(variables.get(j).getName()))
                    return false;
            }
        }
        return true;
    }

    public void areAllFunctionsUnique(Node v) {
        ArrayList<Function> functions = v.getDefinedFunctions();
        if (areArrayListFunctionsUnique(functions) == false) semanticError();

        for (Node node : v.getChildren())
            areAllFunctionsUnique(node);
    }

    public boolean areArrayListFunctionsUnique(ArrayList<Function> functions) {
        for (int i = 0; i < functions.size(); i++) {
            for (int j = i + 1; j < functions.size(); j++) {
                if (functions.get(i).getName().equals(functions.get(j).getName()))
                    return false;
            }
        }
        return true;
    }

    public void setVariableType(Node v) {
        if (v.getLeftHand() == LeftHand.Variable) {
            Type type = Type.getTypeByName((String) v.getChildren().get(0).getTypeName(), v.getChildren().get(0).getArrayDegree());
            if (type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for (Node node : v.getChildren())
            setVariableType(node);
    }

    public void setFunctionType(Node v) {
        if (v.getLeftHand() == LeftHand.FunctionDecl) {
            Function function = v.getDefinedFunctions().get(0);

            Type type = Type.getTypeByName(v.getTypeName(), v.getArrayDegree());
            if (type == null) Compiler.semanticError();
            function.setType(type);
        }
        for (Node node : v.getChildren())
            setFunctionType(node);
    }

    public void setClazzType() {
        for (Clazz clazz : Clazz.getClazzes()) {
            clazz.setType(Type.getTypeByName(clazz.getName(), 0));
        }
    }

    public void setAllClazzAttributesAndFunctions() {
        for (Clazz clazz : Clazz.getClazzes())
            setClazzAttributesAndFunctions(clazz);
    }

    public void setClazzAttributesAndFunctions(Clazz clazz) {
        clazz.setSetAttributesAndFunctions(true);
        Clazz parentClazz = clazz.getParent();
        if (parentClazz == null) return;
        if (parentClazz.isSetAttributesAndFunctions() == false) setClazzAttributesAndFunctions(parentClazz);


        clazz.setFunctions(mergeFunctions(parentClazz.getFunctions(), clazz.getFunctions()));
        clazz.setVariables(mergeVariables(parentClazz.getVariables(), clazz.getVariables()));

    }

    public ArrayList<Function> mergeFunctions(ArrayList<Function> parFunctions, ArrayList<Function> functions) {
        ArrayList<Function> mergedFunctions = new ArrayList<>();
        mergedFunctions.addAll(parFunctions);

        for (Function function : functions) {
            boolean find = false;
            int index = 0;
            for (Function parFunction : parFunctions) {
                if (parFunction.getName().equals(function.getName())) {
                    find = true;
                    if (haveSameSignature(parFunction, function)) {
                        mergedFunctions.set(index, function);
                    } else Compiler.semanticError();
                }
                index++;
            }
            if (find == false) mergedFunctions.add(function);
        }
        return mergedFunctions;
    }

    public boolean haveSameSignature(Function parFunction, Function function) {
    //    System.out.println("return type signature");
        if (!isConvertibleTo(function.getType(), parFunction.getType())) return false;
     //   System.out.println("ok");
        if (function.getParameter().size() != parFunction.getParameter().size()) return false;
        int index = 0;
        for (Variable variable : function.getParameter()) {
            Variable parVariable = parFunction.getParameter().get(index);
     //       System.out.println("parameters signature");
            if (!isConvertibleTo(parVariable.getType(), variable.getType())) return false;
       //     System.out.println("ok");
            index++;
        }
        return true;
    }

    public static boolean isConvertibleTo(Type convertType, Type mainType) {
    //    System.out.println(convertType.getName() + " " + mainType.getName());
        if (mainType.equals(convertType)) return true;
        if (convertType.getArrayDegree() > 0 || mainType.getArrayDegree() > 0)return false;
        while (convertType.getParent() != null) {
            convertType = convertType.getParent();
            if (convertType.equals(mainType)) return true;
        }
        return false;
    }

    public ArrayList<Variable> mergeVariables(ArrayList<Variable> parVariables, ArrayList<Variable> variables) {
        ArrayList<Variable> mergedVariables = new ArrayList<>();
        mergedVariables.addAll(parVariables);
        mergedVariables.addAll(variables);
        if (areArrayListVariablesUnique(mergedVariables) == false) semanticError();
        return mergedVariables;
    }

    // age be terminal bere, type bayad moshakhas shode bashe
    public void setAllNodesType(Node v) {
        for (Node node : v.getChildren())
            setAllNodesType(node);
        switch (v.getLeftHand()) {
            case Constant:
                switch (v.getProductionRule()) {
                    case INTLITERAL:
                        v.setType(Type.getTypeByName("int", 0));
                        break;
                    case DOUBLELITERAL:
                        v.setType(Type.getTypeByName("double", 0));
                        break;
                    case BOOLEANLITERAL:
                        v.setType(Type.getTypeByName("bool", 0));
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
            //    System.out.println("HI");
                switch (v.getProductionRule()) {
                    case IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                        Function function = findFunction(v, (String) v.getChildren().get(0).getValue());
            //            System.out.println(function.getName() + " " + function.getType());
                        v.setType(function.getType());
                        break;
                    case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                        Node exprNode = v.getChildren().get(0);
                        Node idNode = v.getChildren().get(1);
                        Node actualsNode = v.getChildren().get(2);

                        Type type = exprNode.getType();
                        if (type.getArrayDegree() > 0 && ((String)idNode.getValue()).equals("length") && actualsNode.getChildren().size() == 0){
                            v.setType(Type.getTypeByName("int", 0));
                        }
                        else{
                            if (type.getArrayDegree() > 0)semanticError();
                            if (((String)idNode.getValue()).equals("length"))semanticError();
                            Clazz clazz = Clazz.getClazzByName(type.getName());
                            if (clazz == null)semanticError();
                            boolean find = false;
                            for (Function classFunction: clazz.getFunctions()){
                                if (classFunction.getName().equals((String) idNode.getValue())){
                                    if (classFunction.getAccessMode() == AccessMode.PUBLIC){
                                        find = true;
                                        v.setType(classFunction.getType());
                                        break;
                                    }
                                    else{
                                        Node findNode = v;
                                        while (findNode.getParent() != null){
                                            findNode = findNode.getParent();
                                            if (findNode.getLeftHand() == LeftHand.ClassDecl){
                                                break;
                                            }
                                        }
                                        if (findNode.getLeftHand() == LeftHand.ClassDecl){
                                            Clazz coverClazz = getClazzNode(findNode);
                                            if (coverClazz.equals(clazz)){
                                                if (classFunction.getAccessMode() == AccessMode.PROTECTED){
                                                    find = true;
                                                    v.setType(classFunction.getType());
                                                }
                                                else {
                                                    if (findNode.getDefinedFunctions().contains(classFunction)){
                                                        find = true;
                                                        v.setType(classFunction.getType());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (!find)semanticError();
                        }
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
                        Node exprNode = v.getChildren().get(0);
                        Node idNode = v.getChildren().get(1);
                        Type type = exprNode.getType();
                        if (type.getArrayDegree() > 0)semanticError();
                        Clazz clazz = Clazz.getClazzByName(type.getName());
                        if (clazz == null)semanticError();
                        boolean find = false;
                        for (Variable classVariable : clazz.getVariables()){
                            if (classVariable.getName().equals((String)idNode.getValue())){
                                if (classVariable.getAccessMode() == AccessMode.PUBLIC){
                                    find = true;
                                    v.setType(classVariable.getType());
                                }
                                else{
                                    Node findNode = v;
                                    while (findNode.getParent() != null){
                                        findNode = findNode.getParent();
                                        if (findNode.getLeftHand() == LeftHand.ClassDecl){
                                            break;
                                        }
                                    }
                                    if (findNode.getLeftHand() == LeftHand.ClassDecl){
                                        Clazz coverClazz = getClazzNode(findNode);
                                        if (coverClazz.equals(clazz)){
                                            if (classVariable.getAccessMode() == AccessMode.PROTECTED){
                                                find = true;
                                                v.setType(classVariable.getType());
                                            }
                                            else {
                                                if (findNode.getDefinedVariables().contains(classVariable)){
                                                    find = true;
                                                    v.setType(classVariable.getType());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (!find)semanticError();
                        break;
                    case Expr_OPENBRACKET_Expr_CLOSEBRACKET:
                        Type t1 = v.getChildren().get(0).getType();
                        v.setType(Type.getTypeByName(t1.getName(), t1.getArrayDegree() - 1));
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
                        Node findNode = v;
                        boolean find = false;
                        while (findNode.getParent() != null){
                            findNode = findNode.getParent();
                            if (findNode.getLeftHand() == LeftHand.ClassDecl){
                                find = true;
                                Node idNode = findNode.getChildren().get(0);
                                String className = (String)idNode.getValue();
                                Clazz clazz = Clazz.getClazzByName(className);
                                if (clazz == null) System.out.println("WTF!!!");
                                else v.setType(clazz.getType());
                            }
                        }
                        if (!find)semanticError();
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
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LT)) {
                            semanticError();
                        }
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_LESSEQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.LTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_GREATER_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GT))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_GREATEREQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.GTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_EQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.EQEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_NOTEQUAL_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.NOTEQ))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_AND_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.ANDAND))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case Expr_OR_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), v.getChildren().get(1).getType(), Operator.OROR))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case NOT_Expr:
                        if (!Type.possible(v.getChildren().get(0).getType(), Operator.SINGLE_NOT))
                            semanticError();
                        v.setType(Type.getTypeByName("bool", 0));
                        break;
                    case READINTEGER_OPENPARENTHESIS_CLOSEPARENTHESIS:
                        v.setType(Type.getTypeByName("int", 0));
                        break;
                    case READLINE_OPENPARENTHESIS_CLOSEPARENTHESIS:
                        v.setType(Type.getTypeByName("string", 0));
                        break;
                    case NEW_IDENTIFIER:
                        Type type = Type.getTypeByName((String)v.getChildren().get(0).getValue(), 0);
                        if (type == null) semanticError();
                        v.setType(type);
                        break;
                    case NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS:
                        v.setType(Type.createArrayType(v.getChildren().get(1).getType()));
                        break;
                }
                break;
        }
    }

    public static Clazz getClazzNode(Node node){
        Node classIdNode = node.getChildren().get(0);
        String className = (String)classIdNode.getValue();
        Clazz clazz = Clazz.getClazzByName(className);
        return clazz;
    }

    public static Node getNodeClazz(Node node, Clazz clazz){
        if (node.getLeftHand() == LeftHand.ClassDecl){
            Clazz nodeClazz = getClazzNode(node);
            if (clazz.getName().equals(nodeClazz.getName()))
                return node;
        }
        for (Node child : node.getChildren())
            getNodeClazz(child, clazz);
    //    System.out.println("class node not found!");
        semanticError();
        return null;
    }

    public Variable findVariable(Node node, String name) {
        Node node1 = node;
        while (true) {
            //System.out.println(node1.getLeftHand());
            for (Variable variable : node1.getDefinedVariables())
                if (variable.getName().equals(name))
                    return variable;

            if (node1.getLeftHand() == LeftHand.ClassDecl){
                Clazz clazz = getClazzNode(node1);
                //System.out.println(clazz.getName() + " " + clazz.getParent().getName());
                for (Variable variable : clazz.getVariables()){
              //      System.out.println(variable.getName());
                    if (variable.getName().equals(name)){
                        if (variable.getAccessMode() == AccessMode.PROTECTED || variable.getAccessMode() == AccessMode.PUBLIC){
                            return variable;
                        }
                    }
                }
            }
            if (node1.getParent() == null)
                break;
            else
                node1 = node1.getParent();
        }
        //System.out.println("variable: " + name);
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
            if (node1.getLeftHand() == LeftHand.ClassDecl){
                Clazz clazz = getClazzNode(node1);
                for (Function function : clazz.getFunctions()){
                    if (function.getName().equals(name)){
                        if (function.getAccessMode() == AccessMode.PROTECTED || function.getAccessMode() == AccessMode.PUBLIC)
                            return function;
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

    public void checkIntegerIndices(Node v) {
        if (v.getProductionRule() == ProductionRule.NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS) {
            Type t = v.getChildren().get(0).getType();
            if (!t.equals(Type.getTypeByName("int", 0)))
                semanticError();
        }
        if (v.getProductionRule() == ProductionRule.Expr_OPENBRACKET_Expr_CLOSEBRACKET) {
            Type t = v.getChildren().get(1).getType();
            if (!t.equals(Type.getTypeByName("int", 0)))
                semanticError();
        }
        for (Node node : v.getChildren())
            checkIntegerIndices(node);
    }

    //todo class function calls code
    //todo null

    public void checkFunctionCalls(Node v) {
        for (Node node : v.getChildren()) {
            checkFunctionCalls(node);
        }
        if (v.getLeftHand() == LeftHand.Actuals && v.getProductionRule() == ProductionRule.Expr_ActualsCommaExpr) {
            v.getActualsTypes().add(v.getChildren().get(0).getType());
            v.getActualsTypes().addAll(v.getChildren().get(1).getActualsTypes());
            //System.out.println("+1");
        }
        if (v.getLeftHand() == LeftHand.ActualsCommaExpr && v.getProductionRule() == ProductionRule.COMMA_Expr_ActualsCommaExpr) {
            v.getActualsTypes().add(v.getChildren().get(0).getType());
            v.getActualsTypes().addAll(v.getChildren().get(1).getActualsTypes());
            //System.out.println("+1.1");
        }
        if (v.getLeftHand() == LeftHand.Call) {
            switch (v.getProductionRule()) {
                case IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                    Function function = findFunction(v, (String) v.getChildren().get(0).getValue());


                    if (!areFunctionCallParametersCorrect(function, v.getChildren().get(1).getActualsTypes()))
                        semanticError();
                    break;
                case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                    Node exprNode = v.getChildren().get(0);
                    Node idNode = v.getChildren().get(1);
                    String functionName = (String) idNode.getValue();
                    Node actualsNode = v.getChildren().get(2);
                    Type type = exprNode.getType();
                    if (type.getArrayDegree() > 0 && functionName.equals("length") && actualsNode.getChildren().size() == 0) {
                        return;
                    }
                    if (type.getArrayDegree() > 0) semanticError();
                    Clazz clazz = Clazz.getClazzByName(type.getName());
                    if (clazz == null) semanticError();

                    for (Function classFunction : clazz.getFunctions()) {
                        if (classFunction.getName().equals(functionName)) {
                            if (!areFunctionCallParametersCorrect(classFunction, actualsNode.getActualsTypes()))
                                semanticError();
                            break;
                        }
                    }
            }
        }
    }

    public boolean areFunctionCallParametersCorrect(Function function, ArrayList<Type> parametersTypes) {
        //   System.out.println(function.getName() + " " + function.getParameter().size() + " " + parametersTypes.size());
        if (function.getParameter().size() != parametersTypes.size()) return false;
        int index = 0;
        for (Variable variable : function.getParameter()) {
        //    System.out.println("function call param");
            if (!isConvertibleTo(parametersTypes.get(index), variable.getType()))
                return false;
        //    System.out.println("ok");
            index++;
        }
        return true;
    }


    public void setFunctionLabels(Node node){
        if (node.getLeftHand() == LeftHand.FunctionDecl){
            Function function = node.getDefinedFunctions().get(0);
            Label label = new Label();
            label.createNewName();
            function.setLabel(label);
        }
        for (Node child : node.getChildren()){
            setFunctionLabels(child);
        }
    }

    public void checkReturnTypes(Node node){
        if (node.getLeftHand() == LeftHand.ReturnStmt){
            Node exprNode = node.getChildren().get(0);
            Node findNode = node;
            boolean find = false;
            while(findNode.getParent() != null){
                findNode = findNode.getParent();
                if (findNode.getLeftHand() == LeftHand.FunctionDecl){
                    find = true;
                    Function function = findNode.getDefinedFunctions().get(0);
                    Type returnType = Type.getTypeByName("void", 0);
                    if (exprNode.getChildren().size() > 0)returnType = exprNode.getChildren().get(0).getType();
                //    System.out.println("check return type with expr return type");
                    if (!isConvertibleTo(returnType, function.getType()))semanticError();
                //    System.out.println("ok");
                    break;
                }
            }
            if (!find)semanticError();
        }
        for (Node child : node.getChildren())
            checkReturnTypes(child);
    }

    public void debug(Node v) {
    //    System.out.println(v.getLeftHand() + " " + v.getProductionRule());
        Type type = v.getType();
        if (type == null) System.out.println("NULL");
        else System.out.println(type.getName() + " " + type.getParent());
        for (Node node : v.getChildren())
            debug(node);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Code getFinalCode() {
        return finalCode;
    }

    public void setFinalCode(Code finalCode) {
        this.finalCode = finalCode;
    }
}