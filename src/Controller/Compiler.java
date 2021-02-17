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

        generateCode(root);

        //todo
        createFinalCode();
        writeToFile(outputFileName);
    }

    public static void writeToFile( String outputFileName ) throws IOException {
        System.out.println(outputFileName);
        FileWriter out = new FileWriter( outputFileName );
        out.write( finalCode.getText());
        out.close();
    }

    public void createFinalCode(){
        Code code = new Code();
    }

    private void createBuiltinFunctions(Node root) {
        Function itob = new Function();
        initializeFunction(itob, "itob", "int", "boolean");
        Function btoi = new Function();
        initializeFunction(btoi, "btoi", "boolean", "int");
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
        Label label = new Label(); label.creatNewName();
        x.setLabel(label);
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
                    if (node1.getLeftHand() == LeftHand.ClassDecl || node1.getLeftHand() == LeftHand.Program || variable.getNumber() < node.getIndex()) {
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

    public void debug(Node v) {
        System.out.println(v.getLeftHand() + " " + v.getProductionRule());
        Type type = v.getType();
        if (type == null) System.out.println("NULL");
        else System.out.println(type.getName() + " " + type.getParent());
        for (Node node : v.getChildren())
            debug(node);
    }

    public void setVariableType(Node v) {
        //todo
        //chera todo?
        if (v.getLeftHand() == LeftHand.Variable) {
            Type type = Type.getTypeByName((String) v.getChildren().get(0).getTypeName(), v.getChildren().get(0).getArrayDegree());
            if (type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for (Node node : v.getChildren())
            setVariableType(node);
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

    public void setClazzType() {
        for (Clazz clazz : Clazz.getClazzes()) {
            clazz.setType(Type.getTypeByName(clazz.getName(), 0));
        }
    }

    public void setAllClazzAttributesAndFunctions() {
        for (Clazz clazz : Clazz.getClazzes())
            setClazzAttributesAndFunctions(clazz);
    }

    public boolean haveSameSignature(Function parFunction, Function function) {
        if (!isConvertibleTo(function.getType(), parFunction.getType())) return false;
        if (function.getParameter().size() != parFunction.getParameter().size()) return false;
        int index = 0;
        for (Variable variable : function.getParameter()) {
            Variable parVariable = parFunction.getParameter().get(index);
            if (!isConvertibleTo(parVariable.getType(), variable.getType())) return false;
            index++;
        }
        return true;
    }

    public boolean isConvertibleTo(Type convertType, Type mainType) {
        if (mainType.equals(convertType)) return true;
        while (convertType.getParent() != null) {
            convertType = convertType.getParent();
            if (convertType.equals(mainType)) return true;
        }
        return false;
    }

    public boolean areFunctionCallParametersCorrect(Function function, ArrayList<Type> parametersTypes) {
        //   System.out.println(function.getName() + " " + function.getParameter().size() + " " + parametersTypes.size());
        if (function.getParameter().size() != parametersTypes.size()) return false;
        int index = 0;
        for (Variable variable : function.getParameter()) {
            if (!isConvertibleTo(parametersTypes.get(index), variable.getType()))
                return false;
            index++;
        }
        return true;
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

    public ArrayList<Variable> mergeVariables(ArrayList<Variable> parVariables, ArrayList<Variable> variables) {
        ArrayList<Variable> mergedVariables = new ArrayList<>();
        mergedVariables.addAll(parVariables);
        mergedVariables.addAll(variables);
        if (areArrayListVariablesUnique(mergedVariables) == false) semanticError();
        return mergedVariables;
    }

    public void setClazzAttributesAndFunctions(Clazz clazz) {
        clazz.setSetAttributesAndFunctions(true);
        Clazz parentClazz = clazz.getParent();
        if (parentClazz == null) return;
        if (parentClazz.isSetAttributesAndFunctions() == false) setClazzAttributesAndFunctions(parentClazz);


        clazz.setFunctions(mergeFunctions(parentClazz.getFunctions(), clazz.getFunctions()));
        clazz.setVariables(mergeVariables(parentClazz.getVariables(), clazz.getVariables()));

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
                        Function function = findFunction(v, (String) v.getChildren().get(0).getValue());
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
                                    find = true;
                                    v.setType(classFunction.getType());
                                    break;
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
                                find = true;
                                v.setType(classVariable.getType());
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

    public void checkIntegerIndices(Node v) {
        if (v.getProductionRule() == ProductionRule.NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS) {
            Type t = v.getChildren().get(0).getType();
            if (t != Type.getTypeByName("int", 0))
                semanticError();
        }
        if (v.getProductionRule() == ProductionRule.Expr_OPENBRACKET_Expr_CLOSEBRACKET) {
            Type t = v.getChildren().get(1).getType();
            if (t != Type.getTypeByName("int", 0))
                semanticError();
        }
        for (Node node : v.getChildren())
            checkIntegerIndices(node);
    }

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
                    if (areFunctionCallParametersCorrect(function, v.getChildren().get(1).getActualsTypes()) == false)
                        semanticError();
                    break;
                case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                    Node exprNode = v.getChildren().get(0);
                    Node idNode = v.getChildren().get(1);
                    String functionName = (String) idNode.getValue();
                    Node actualsNode = v.getChildren().get(2);
                    Type type = exprNode.getType();
                    if (type.getArrayDegree() > 0 && ((String)idNode.getValue()).equals("length") && actualsNode.getChildren().size() == 0){
                        return;
                    }
                    Clazz clazz = Clazz.getClazzByName(type.getName());
                    if (clazz == null)semanticError();

                    for (Function classFunction : clazz.getFunctions()){
                        if (classFunction.getName().equals(functionName)){
                            if (!areFunctionCallParametersCorrect(classFunction, actualsNode.getActualsTypes()))
                                semanticError();
                            break;
                        }
                    }


            }
        }
    }


    public Node getRoot() {
        return root;
    }


    public void setRoot(Node root) {
        this.root = root;
    }

    public int getAttributeOffset(Clazz clazz, String name) {
        int offset = 0;
        for (Variable variable : clazz.getVariables()) {
            if (variable.getName().equals(name)) break;
            offset += 4;
        }
        return offset;
    }

    public void generateLvalueIdentifierCode(Node node) {
        String idName = (String) node.getChildren().get(0).getValue();
        Node findNode = node;
        Code code = new Code();
        while (true) {
            int index = 0;
            for (Variable variable : findNode.getDefinedVariables()) {
                if (variable.getName().equals(idName)) {
                    if (findNode.getLeftHand() == LeftHand.Program) {
                        code.addCode(codeGenerator.getGlobalVariableAddress(variable));
                        node.setCode(code);
                        return;
                    } else if (findNode.getLeftHand() == LeftHand.ClassDecl) {
                        String className = (String) findNode.getChildren().get(0).getValue();
                        Clazz clazz = Clazz.getClazzByName(className);
                        if (clazz == null) {
                            System.out.println("WTF!");
                            semanticError();
                        } else {
                            int offset = getAttributeOffset(clazz, idName);
                            codeGenerator.getClassVariableAddressInClass(offset);
                        }
                        node.setCode(code);
                        return;
                    } else if (variable.getNumber() < node.getIndex()) {
                        Node tempNode = findNode;
                        int offset = 12 + index * 4;
                        while (tempNode.getLeftHand() != LeftHand.FunctionDecl) {
                            tempNode = tempNode.getParent();
                            if (tempNode.getLeftHand() == LeftHand.StmtBlock || tempNode.getLeftHand() == LeftHand.FunctionDecl) {
                                offset += 4 * (tempNode.getDefinedVariables().size());
                            }
                        }
                        code.addCode(codeGenerator.getLocalVariableAddress(offset));
                        node.setCode(code);
                        return;
                    }
                }
                index++;
            }
            if (findNode.getParent() == null) {
                System.out.println("WTF!");
                break;
            } else {
                findNode = findNode.getParent();
            }
        }
        return;
    }

    public void generateLValueCode(Node node) {
        switch (node.getProductionRule()) {
            case IDENTIFIER:
                generateLvalueIdentifierCode(node);
                break;
            case Expr_DOT_IDENTIFIER:
                Node exprNode = node.getChildren().get(0);
                Node idNode = node.getChildren().get(1);
                generateCode(exprNode);
                node.getCode().addCode(exprNode.getCode());
                Clazz clazz = Clazz.getClazzByName(exprNode.getType().getName());
                int offset = getAttributeOffset(clazz, (String) idNode.getValue());
                node.getCode().addCode(codeGenerator.getClassVariableAddressOutOfClass(offset));
                break;
            case Expr_OPENBRACKET_Expr_CLOSEBRACKET:

        }
    }

    public void generateExprCode(Node node) {
        Code code = new Code();
        switch (node.getProductionRule()) {
            case LValue:
                generateCode(node.getChildren().get(0));
                code.addCode(node.getChildren().get(0).getCode());
                if (node.getType().equals(Type.getTypeByName("double", 0))) {
                    code.addCode(codeGenerator.loadDoubleVariable());
                } else {
                    code.addCode(codeGenerator.loadIntegerVariable());
                }
                node.setCode(code);
                break;
        }
    }

    public void generateActualsCode(Node node){
        Code code = new Code();
        if(node.getProductionRule() != ProductionRule.EPSILON){
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
            code.addCode("sub $sp, $sp, 4");
            code.addCode("sw $t0, 0($sp)");
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
    }


    public void generateCode(Node node){
        switch (node.getLeftHand()){
            case Program:
                generateProgramCode(node);
                break;
            case FunctionDecl:
                generateFunctionDeclCode(node);
                break;
            case ClassDecl:
                generateClassDeclCode(node);
                break;
            case StmtBlock:
                generateStmtBlockCode(node);
                break;
            case InsideStmtBlock:
                generateInsideStmtBlockCode(node);
                break;
            case StmtStar:
                generateStmtStarCode(node);
                break;
            case Stmt:
                generateStmtCode(node);
                break;
            case ExprPrime:
                generateExprPrimeCode(node);
                break;
            case IfStmt:
                node.setCode(codeGenerator.ifCondition(node));
                break;
            case WhileStmt:
                node.setCode(codeGenerator.whileLoop(node));
                break;
            case ForStmt:
                node.setCode(codeGenerator.forLoop(node));
                break;
            case BreakStmt:
                generateBreakCode(node);
                break;
            case ContinueStmt:
                generateContinueCode(node);
                break;
            case ReturnStmt:
                generateReturnCode(node);
                break;
            case PrintStmt:
            case PrintCommaExpr:
                generatePrintCode(node);
                break;
            case LValue:
                generateLValueCode(node);
                break;
            case Expr:
                generateExprCode(node);
                break;
            case Call:
                generateCallCode(node);
                break;
            case Actuals:
            case ActualsCommaExpr:
                generateActualsCode(node);
                break;
            case Constant:
                generateConstantCode(node);
                break;
            default:
        }
    }

    private void generateReturnCode(Node node) {
    }

    private void generateContinueCode(Node node) {
        codeGenerator.Continue(node);
    }

    private void generateBreakCode(Node node) {
        codeGenerator.Break(node);
    }

    private void generateConstantCode(Node node) {
        Code code = new Code();
        Node childNode = node.getChildren().get(0);
        switch (node.getProductionRule()){
            case INTLITERAL:
                code.addCode("li $t0, " + Integer.parseInt((String)childNode.getValue()));
                break;
            case DOUBLELITERAL:
                code.addCode("li.s $f0, " + Double.parseDouble((String)childNode.getValue()));
                break;
            case BOOLEANLITERAL:
                if((String)childNode.getValue() == "false"){
                    code.addCode("li $t0, 0");
                }
                else code.addCode("li $t0, 1");
                break;
            case STRINGLITERAL:
                String str = (String)childNode.getValue();
                int len = str.length();
                code.addCode("li $v0, 9");

                code.addCode("li $a0, " + (4 + len));
                code.addCode("syscall");
                code.addCode("li $t0, $v0");
                code.addCode("li $t1, " + len);
                code.addCode("sw $t1, 0($t0)");
                for (int i = 0; i < len; i ++){
                    code.addCode("li $t2, " + (int)str.charAt(i));
                    code.addCode("sb $t2 " + (i + 4) + "($t0)");
                }
                break;
            case NULL:
                //todo
        }
        node.setCode(code);
    }

    private void generateCallCode(Node node) {
        Code code = new Code();
        switch (node.getProductionRule()){
            case IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                Node idNode = node.getChildren().get(0);
                Node actualsNode = node.getChildren().get(1);

                generateCode(actualsNode);

                Node findNode = node;
                while (findNode.getParent() != null){
                    findNode = findNode.getParent();
                    for (Function function : findNode.getDefinedFunctions()){
                        if (function.getName().equals((String) idNode.getValue())){
                            code.addCode("lw $t0, 0($fp)");
                            code.addCode("sub $sp, $sp, 4");
                            code.addCode("sw $t0, 0($sp)");
                            code.addCode(actualsNode.getCode());
                            code.addCode("sub $sp, $sp, 8");
                            code.addCode("sw $fp, 4($sp)");
                            code.addCode("sw $ra, 0($sp)");
                            code.addCode("sub $fp, $sp, " + (4 * function.getParameter().size()));
                            code.addCode("jal " + function.getLabel().getName());
                            if (!function.getType().equals(Type.getTypeByName("double", 0)))
                                code.addCode("move $t0, $v0");
                            code.addCode("lw $ra, 0($sp)");
                            code.addCode("lw $fp, 4($sp)");
                            code.addCode("add $sp, $sp, " + (4 * (2 + 1 + function.getParameter().size())));
                            node.setCode(code);
                            //f(a, b)
                            break;
                        }
                    }
                }
                break;
            case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                //todo
                Node exprNode = node.getChildren().get(0);
                Node identifierNode = node.getChildren().get(1);
                Node actualNode = node.getChildren().get(2);
                generateCode(exprNode);

        }
    }

    public void generatePrintCode(Node node) {
        Code code = new Code();
        if (node.getChildren().size() > 0) {
            Node child = node.getChildren().get(0);
            generateCode(child);
            code.addCode(child.getCode());
            if (Type.getTypeByName("int", 0).equals(node.getType())) {
                code.addCode("li $v0, 1");
                code.addCode("move $a0, $t0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("double", 0).equals(node.getType())) {
                code.addCode("li $v0, 3");
                code.addCode("mov.s $f12, $f0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("string", 0).equals(node.getType())) {
                code.addCode("li $v0, 4");
                code.addCode("move $a0, $t0");
                code.addCode("syscall");
            }
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
        return;
    }

    private void generateExprPrimeCode(Node node) {
        Code code = new Code();
        if (node.getProductionRule() != ProductionRule.EPSILON) {
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
        }
        node.setCode(code);
    }

    private void generateStmtCode(Node node) {
        generateCode(node.getChildren().get(0));
        node.setCode(node.getChildren().get(0).getCode());
    }

    private void generateStmtStarCode(Node node) {
        Code code = new Code();
        if (node.getProductionRule() != ProductionRule.EPSILON) {
            generateCode(node.getChildren().get(0));
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(0).getCode());
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
    }

    private void generateInsideStmtBlockCode(Node node) {
        if (node.getProductionRule() == ProductionRule.StmtStar) {
            generateCode(node.getChildren().get(0));
            node.setCode(node.getChildren().get(0).getCode());
        }
        if (node.getProductionRule() == ProductionRule.VariableDecl_InsideStmtBlock) {
            generateCode(node.getChildren().get(1));
            node.setCode(node.getChildren().get(1).getCode());
        }
    }

    private void generateStmtBlockCode(Node node) {
        Code code = new Code();
        code.addCode("sub $sp, $sp, " + node.getDefinedVariables().size() * 4);
        generateCode(node.getChildren().get(0));
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("add $sp, $sp, " + node.getDefinedVariables().size() * 4);
        node.setCode(code);
    }

    private void generateClassDeclCode(Node node) {
        for (Node v : node.getChildren())
            generateCode(v);
        Code code = new Code();
        for (Function function : node.getDefinedFunctions())
            code.addCode(function.getNode().getCode());
        node.setCode(code);
    }

    private void generateFunctionDeclCode(Node node) {
        Code code = new Code();
        Label label = new Label();
        label.creatNewName();
        node.getDefinedFunctions().get(0).setLabel(label);
        code.addCode(label.getName() + " :");
        int index = 2;
        if (node.getProductionRule() == ProductionRule.VOID_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_StmtBlock)
            index = 1;
        generateCode(node.getChildren().get(index));
        code.addCode(node.getChildren().get(index).getCode());
        code.addCode("lw  $ra, " + ((1 + node.getDefinedFunctions().get(0).getParameter().size() + 1) * 4) + "($fp)");
        code.addCode("lw  $fp, " + ((1 + node.getDefinedFunctions().get(0).getParameter().size()) * 4) + "($fp)");
        code.addCode("j $ra");
        node.setCode(code);
    }

    public void generateProgramCode(Node node) {
        node.setCode(codeGenerator.createGlobalVariables(node.getDefinedVariables()));
        for (Node v : node.getChildren()) {
            generateCode(v);
        }

    }

    public Code gatherGlobalFunction(Node node) {
        Code code = new Code();
        for (Function function : node.getDefinedFunctions()) {
            if (function.getName().equals("itob"))
                code.addCode(itob(function));
            else if (function.getName().equals("btoi"))
                code.addCode(btoi(function));
            else if (function.getName().equals("dtoi"))
                code.addCode(dtoi(function));
            else if (function.getName().equals("itod"))
                code.addCode(itod(function));
            else
                code.addCode(function.getNode().getCode());
        }
        return code;
    }

    public Code btoi(Function function){
        Code code = new Code();
        code.addCode(function.getLabel().getName()+ " :");
        code.addCode("lw $t0, 4($fp)");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("j $ra");
        return code;
    }

    public Code itod(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName()+ " :");
        code.addCode("lw $t0, 4($fp)");
        code.addCode("mtc1 $t0, $f0");
        code.addCode("cvt.s.w $f0, $f0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("j $ra");
        return code;
    }

    public Code dtoi(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName()+ " :");
        code.addCode("l.s $f0, 4($fp)");
        code.addCode("cvt.w.s $f0, $f0");
        code.addCode("mfc1 $t0, $f0");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("j $ra");
        return code;
    }

    public Code itob(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName()+ " :");
        Label label = new Label(); label.creatNewName();
        Label label1 = new Label(); label1.creatNewName();
        code.addCode("lw $t0, 4($sp)");
        code.addCode("beq $t0, 0" + label.getName());
        code.addCode("li $t0, 1");
        code.addCode("j " + label1.getName());
        code.addCode(label.getName() + ":");
        code.addCode("li $t0, 0");
        code.addCode(label1.getName() + ":");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("j $ra");
        return code;
    }

    public Code getFinalCode() {
        return finalCode;
    }

    public void setFinalCode(Code finalCode) {
        this.finalCode = finalCode;
    }
}