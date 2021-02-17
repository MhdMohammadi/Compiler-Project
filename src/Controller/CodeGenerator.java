package Controller;

import Model.Code;
import Model.Type;
import Model.*;
import Enum.*;

import java.util.ArrayList;

public class CodeGenerator {
    public ArrayList<Object> floatingPoints = new ArrayList<>();

    public Code createFinalCode(Node root) {
        Code code = new Code();
        code.addCode(root.getCode());
        code.addCode(addFloatingPoints());
        code.addCode(gatherGlobalFunction(root));
//        code.addCode(gatherClassCodes(root));
        System.out.println(code.getText());
        return code;
    }

    private Code addFloatingPoints() {
        Code code = new Code();
        code.addCode("TRUE: .asciiz \"true\"");
        code.addCode("FALSE: .asciiz \"false\"");
        code.addCode("ENDL: .asciiz \"\\n\"");
        return code;
    }

    public Code gatherClassCodes(Node node) {
        Code code = new Code();
        if (node.getLeftHand() == LeftHand.ClassDecl)
            code.addCode(node.getCode());
        for (Node v : node.getChildren()) {
            code.addCode(gatherClassCodes(v));
        }
        return code;
    }

    public Code gatherGlobalFunction(Node node) {
        Code code = new Code();
        for (Function function : node.getDefinedFunctions()) {
            if (function.getName().equals("main")) {
                code.addCode(".text");
                code.addCode(".globl " + function.getLabel().getName());
                code.addCode(function.getNode().getCode());
            }
        }
        for (Function function : node.getDefinedFunctions()) {
            if (function.getName().equals("itob"))
                code.addCode(itob(function));
            else if (function.getName().equals("btoi"))
                code.addCode(btoi(function));
            else if (function.getName().equals("dtoi"))
                code.addCode(dtoi(function));
            else if (function.getName().equals("itod"))
                code.addCode(itod(function));
            else if (!function.getName().equals("main"))
                code.addCode(function.getNode().getCode());
        }
        return code;
    }

    public Code btoi(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName() + " :");
        code.addCode("lw $t0, 4($fp)");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("jr $ra");
        return code;
    }

    public Code itod(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName() + " :");
        code.addCode("lw $t0, 4($fp)");
        code.addCode("mtc1 $t0, $f0");
        code.addCode("cvt.s.w $f0, $f0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("jr $ra");
        return code;
    }

    public Code dtoi(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName() + " :");
        code.addCode("l.s $f0, 4($fp)");
        code.addCode("cvt.w.s $f0, $f0");
        code.addCode("mfc1 $t0, $f0");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("jr $ra");
        return code;
    }

    public Code itob(Function function) {
        Code code = new Code();
        code.addCode(function.getLabel().getName() + " :");
        Label label = new Label();
        label.createNewName();
        Label label1 = new Label();
        label1.createNewName();
        code.addCode("lw $t0, 4($sp)");
        code.addCode("beq $t0, 0, " + label.getName());
        code.addCode("li $t0, 1");
        code.addCode("j " + label1.getName());
        code.addCode(label.getName() + ":");
        code.addCode("li $t0, 0");
        code.addCode(label1.getName() + ":");
        code.addCode("move $v0, $t0");
        code.addCode("lw  $ra, 8($fp)");
        code.addCode("lw  $fp, 12($fp)");
        code.addCode("jr $ra");
        return code;
    }

    public void generateCode(Node node) {
        switch (node.getLeftHand()) {
            case Program:
                generateProgramCode(node);
                break;
            case DeclStar:
                generateDeclStarCode(node);
                break;
            case Decl:
                generateDeclCode(node);
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
                node.setCode(ifCondition(node));
                break;
            case WhileStmt:
                node.setCode(whileLoop(node));
                break;
            case ForStmt:
                node.setCode(forLoop(node));
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
                Code code = new Code();
                node.setCode(code);
        }
    }

    private void generateDeclCode(Node node) {
        Code code = new Code();
        if (node.getProductionRule() == ProductionRule.FunctionDecl) {
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
        }
        node.setCode(code);
    }

    private void generateDeclStarCode(Node node) {
        Code code = new Code();
        if (node.getChildren().size() > 0) {
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
    }

    public void generateProgramCode(Node node) {
        node.setCode(createGlobalVariables(node.getDefinedVariables()));
        for (Node v : node.getChildren()) {
            generateCode(v);
        }
    }

    private void generateFunctionDeclCode(Node node) {
        Code code = new Code();
        Function function = node.getDefinedFunctions().get(0);
        if (function.getName().equals("main"))
            function.getLabel().setName("main");
        code.addCode(function.getLabel().getName() + " :");
        if (function.getName().equals("main")) {
            code.addCode("move $fp, $sp");
            code.addCode("sub $sp, $sp, 8");
        }
        int index = 3;
        if (node.getProductionRule() == ProductionRule.VOID_IDENTIFIER_OPENPARENTHESIS_Formals_CLOSEPARENTHESIS_StmtBlock)
            index = 2;
        generateCode(node.getChildren().get(index));
        code.addCode(node.getChildren().get(index).getCode());
        if (!node.getDefinedFunctions().get(0).getName().equals("main")) {
            code.addCode("lw  $ra, " + -((1 + function.getParameter().size() + 1) * 4) + "($fp)");
            code.addCode("lw  $fp, " + -((1 + function.getParameter().size()) * 4) + "($fp)");
            code.addCode("jr $ra");
        } else{
            code.addCode("li $v0, 10");
            code.addCode("syscall");
        }
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

    private void generateStmtBlockCode(Node node) {
        Code code = new Code();
        code.addCode("sub $sp, $sp, " + node.getDefinedVariables().size() * 4);
        generateCode(node.getChildren().get(0));
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("add $sp, $sp, " + node.getDefinedVariables().size() * 4);
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

    private void generateStmtCode(Node node) {
        generateCode(node.getChildren().get(0));
        node.setCode(node.getChildren().get(0).getCode());
    }

    private void generateExprPrimeCode(Node node) {
        Code code = new Code();
        if (node.getProductionRule() != ProductionRule.EPSILON) {
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
        }
        node.setCode(code);
    }

    public Code ifCondition(Node node) {
        Code code = new Code();
        code.addCode(node.getChildren().get(0).getCode());
        Label label = new Label();
        Label label1 = new Label();
        label.createNewName();
        label1.createNewName();
        code.addCode("beq $t0, 0, " + label1.getName());
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName() + ":");
        if (node.getChildren().size() == 3) {
            code.addCode(node.getChildren().get(2).getCode());
        }
        code.addCode(label.getName() + ":");
        return code;
    }

    public Code whileLoop(Node node) {
        Code code = new Code();
        Label label = new Label();
        Label label1 = new Label();
        label.createNewName();
        label1.createNewName();
        code.addCode(label.getName() + ":");
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("beq $t0, 0, " + label1.getName());
        node.getChildren().get(1).setBreakLabel(label1);
        node.getChildren().get(1).setContinueLabel(label);
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName() + ":");
        return code;
    }

    public Code forLoop(Node node) {
        Code code = new Code();
        Label label = new Label();
        label.createNewName();
        Label label1 = new Label();
        label1.createNewName();
        Label label2 = new Label();
        label2.createNewName();
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode(label.getName() + ":");
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("beq $t0, 0, " + label1.getName());
        node.getChildren().get(3).setBreakLabel(label1);
        node.getChildren().get(3).setContinueLabel(label2);
        code.addCode(node.getChildren().get(3).getCode());
        code.addCode(label2.getName() + ":");
        code.addCode(node.getChildren().get(2).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName() + ":");
        return code;
    }

    private void generateBreakCode(Node node) {
        node.setCode(Break(node));
    }

    private void generateContinueCode(Node node) {
        node.setCode(Continue(node));
    }

    public void generateReturnCode(Node node) {
        Code code = new Code();
        Node exprNode = node.getChildren().get(0);
        generateCode(exprNode);
        code.addCode(exprNode.getCode());
        code.addCode("add $sp, $fp, 4");
        if (exprNode.getProductionRule() != ProductionRule.EPSILON) {
            Type returnType = exprNode.getChildren().get(0).getType();
            if (!returnType.equals(Type.getTypeByName("double", 0)))
                code.addCode("move $v0, $t0");
        }
        code.addCode("jr $ra");
        node.setCode(code);
        //todo check kon
    }

    public void generatePrintCode(Node node) {
        Code code = new Code();
        if (node.getChildren().size() > 0) {
            Node child = node.getChildren().get(0);
            generateCode(child);
            code.addCode(child.getCode());
            if (Type.getTypeByName("int", 0).equals(node.getChildren().get(0).getType()) ||
                Type.getTypeByName("bool", 0).equals(node.getChildren().get(0).getType())
            ) {
                Label label = new Label(); label.createNewName();
                Label label2 = new Label(); label2.createNewName();
                code.addCode("beq $t0, 0, " + label.getName());
                code.addCode("la $a0, TRUE");
                code.addCode("j " + label2.getName());
                code.addCode(label.getName() + " :");
                code.addCode("la $a0, FALSE");
                code.addCode(label2.getName() + " :");
                code.addCode("li $v0, 4");
                code.addCode("syscall");
            } else if (Type.getTypeByName("double", 0).equals(node.getChildren().get(0).getType())) {
                code.addCode("li $v0, 2");
                code.addCode("mov.s $f12, $f0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("string", 0).equals(node.getChildren().get(0).getType())) {
                code.addCode("li $v0, 4");
                code.addCode("move $a0, $t0");
                code.addCode("syscall");
            }
            code.addCode("la $a0, ENDL");
            code.addCode("li $v0, 4");
            code.addCode("syscall");
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
        return;
    }

    public void generateLValueCode(Node node) {
        switch (node.getProductionRule()) {
            case IDENTIFIER:
                generateLvalueToIdentifierCode(node);
                break;
            case Expr_DOT_IDENTIFIER:
                Code code = new Code();
                Node exprNode = node.getChildren().get(0);
                Node idNode = node.getChildren().get(1);
                generateCode(exprNode);
                code.addCode(exprNode.getCode());
                if (exprNode.getType().getArrayDegree() > 0) Compiler.semanticError();
                Clazz clazz = Clazz.getClazzByName(exprNode.getType().getName());
                if (clazz == null) Compiler.semanticError();
                int offset = getAttributeOffset(clazz, (String) idNode.getValue());
                code.addCode(getClassVariableAddressOutOfClass(offset));
                node.setCode(code);
                break;
            case Expr_OPENBRACKET_Expr_CLOSEBRACKET:
                Node exprNode1 = node.getChildren().get(0);
                Node exprNode2 = node.getChildren().get(1);
                generateCode(exprNode1);
                node.getCode().addCode(exprNode1.getCode());
                node.getCode().addCode("sub $sp, $sp, 4");
                node.getCode().addCode("sw $t0, 0($sp)");
                generateCode(exprNode2);
                node.getCode().addCode(exprNode2.getCode());
                node.getCode().addCode("lw $t1, 0($sp)");
                node.getCode().addCode("add $sp, $sp, 4");
                node.getCode().addCode("add $t0, $t0, $t1");
        }
    }

    public void generateExprCode(Node node) {
        Code code = new Code();
//        System.out.println(node.getProductionRule());
        switch (node.getProductionRule()) {
            case LValue:
                generateCode(node.getChildren().get(0));
                code.addCode(node.getChildren().get(0).getCode());
                if (node.getType().equals(Type.getTypeByName("double", 0))) {
                    code.addCode(loadDoubleVariable());
                } else {
                    code.addCode(loadIntegerVariable());
                }
                node.setCode(code);
                break;
            case LValue_ASSIGN_Expr:
                node.setCode(assignExprs(node.getChildren().get(0), node.getChildren().get(1)));
                break;
            case Constant:
                generateCode(node.getChildren().get(0));
                node.setCode(node.getChildren().get(0).getCode());
                break;
            case THIS:
                code.addCode("lw $t0, 0($fp)");
                node.setCode(code);
                break;
            case Call:
                generateCode(node.getChildren().get(0));
                node.setCode(node.getChildren().get(0).getCode());
                break;
            case OPENPARENTHESIS_Expr_CLOSEPARENTHESIS:
                generateCallCode(node.getChildren().get(0));
                node.setCode(node.getChildren().get(0).getCode());
                break;
            case Expr_PLUS_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.PLUS));
                break;
            case Expr_MINUS_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.MINUS));
                break;
            case Expr_MULTIPLY_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.MULT));
                break;
            case Expr_DIVIDE_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.DIV));
                break;
            case Expr_MOD_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.MOD));
                break;
            case MINUS_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), Operator.SINGLE_MINUS));
                break;
            case Expr_LESS_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.LT));
                break;
            case Expr_LESSEQUAL_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.LTEQ));
                break;
            case Expr_GREATER_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.GT));
                break;
            case Expr_GREATEREQUAL_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.GTEQ));
                break;
            case Expr_EQUAL_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.EQEQ));
                break;
            case Expr_NOTEQUAL_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.NOTEQ));
                break;
            case Expr_AND_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.ANDAND));
                break;
            case Expr_OR_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), node.getChildren().get(1), Operator.OROR));
                break;
            case NOT_Expr:
                node.setCode(calcExpr(node.getChildren().get(0), Operator.SINGLE_NOT));
                System.out.println(node.getCode().getText());
                break;
            case READINTEGER_OPENPARENTHESIS_CLOSEPARENTHESIS:
                node.setCode(readInteger());
                break;
            case READLINE_OPENPARENTHESIS_CLOSEPARENTHESIS:
                node.setCode(readLine());
                break;
            case NEW_IDENTIFIER:
                node.setCode(newIdentifier(node));
                break;
            case NEWARRAY_OPENPARENTHESIS_Expr_COMMA_Type_CLOSEPARENTHESIS:
                node.setCode(newArray(node));
                break;
        }
    }

    public Code generateFunctionCode(Node actualsNode, Function function){
        Code code = new Code();
        code.addCode("lw $t0, 0($fp)");
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(actualsNode.getCode());
        code.addCode("sub $sp, $sp, 8");
        code.addCode("sw $fp, 4($sp)");
        code.addCode("sw $ra, 0($sp)");
        code.addCode("add $fp, $sp, " + (4 * (2 + function.getParameter().size())));
        code.addCode("jal " + function.getLabel().getName());
        if (!function.getType().equals(Type.getTypeByName("double", 0)))
            code.addCode("move $t0, $v0");
        code.addCode("lw $ra, 0($sp)");
        code.addCode("lw $fp, 4($sp)");
        code.addCode("add $sp, $sp, " + (4 * (2 + 1 + function.getParameter().size())));
        return code;
    }

    private void generateCallCode(Node node) {
        Code code = new Code();
        switch (node.getProductionRule()) {
            case IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                Node idNode = node.getChildren().get(0);
                String functionName = (String) idNode.getValue();
                Node actualsNode = node.getChildren().get(1);

                generateCode(actualsNode);

                Node findNode = node;
                while (findNode.getParent() != null) {
                    findNode = findNode.getParent();
                    boolean find = false;
                    for (Function function : findNode.getDefinedFunctions()) {
                        if (function.getName().equals(functionName)) {
                            find = true;
                            node.setCode(generateFunctionCode(actualsNode, function));
                            break;
                        }
                    }
                    if (find) break;
                    if (findNode.getLeftHand() == LeftHand.ClassDecl){
                        Node classIdNode = findNode.getChildren().get(0);
                        String className = (String)classIdNode.getValue();
                        Clazz clazz = Clazz.getClazzByName(className);
                        for (Function function : clazz.getFunctions()){
                            if (function.getName().equals(functionName)){
                                if (function.getAccessMode() == AccessMode.PUBLIC || function.getAccessMode() == AccessMode.PROTECTED){
                                    find = true;
                                    node.setCode(generateFunctionCode(actualsNode, function));
                                    break;
                                }

                            }
                        }
                    }
                    if (find) break;
                }
                break;
            case Expr_DOT_IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS:
                Node exprNode1 = node.getChildren().get(0);
                generateCode(exprNode1);
                code.addCode(exprNode1.getCode());

                Node idNode1 = node.getChildren().get(1);
                String functionName1 = (String) idNode1.getValue();
                Node actualsNode1 = node.getChildren().get(2);

                if (functionName1.equals("length")) {
                    //todo length
                } else {
                    Type type = exprNode1.getType();
                    Clazz clazz = Clazz.getClazzByName(type.getName());
                    for (Function function : clazz.getFunctions()) {
                        if (function.getName().equals(functionName1)) {
                            code.addCode("sub $sp, $sp, 4");
                            code.addCode("sw $t0, 0($sp)");
                            generateCode(actualsNode1);
                            code.addCode(actualsNode1.getCode());
                            code.addCode("sub $sp, $sp, 8");
                            code.addCode("sw $fp, 4($sp)");
                            code.addCode("sw $ra, 0($sp)");
                            code.addCode("add $fp, $sp, " + (4 * (2 + function.getParameter().size())));
                            code.addCode("jal " + function.getLabel().getName());
                            if (!function.getType().equals(Type.getTypeByName("double", 0)))
                                code.addCode("move $t0, $v0");
                            code.addCode("lw $ra, 0($sp)");
                            code.addCode("lw $fp, 4($sp)");
                            code.addCode("add $sp, $sp, " + (4 * (2 + 1 + function.getParameter().size())));
                            break;
                        }
                    }
                }
                node.setCode(code);

        }
        //todo inheritance nazadim
    }

    public void generateActualsCode(Node node) {
        Code code = new Code();
        if (node.getProductionRule() != ProductionRule.EPSILON) {
            generateCode(node.getChildren().get(0));
            code.addCode(node.getChildren().get(0).getCode());
            code.addCode("sub $sp, $sp, 4");
            code.addCode("sw $t0, 0($sp)");
            generateCode(node.getChildren().get(1));
            code.addCode(node.getChildren().get(1).getCode());
        }
        node.setCode(code);
    }

    private void generateConstantCode(Node node) {
        Code code = new Code();
        Node childNode = node.getChildren().get(0);
        String str = (String) childNode.getValue();
        switch (node.getProductionRule()) {
            case INTLITERAL:
                code.addCode("li $t0, " + Integer.parseInt(str));
                break;
            case DOUBLELITERAL:
                code.addCode("li.s $f0, " + Double.parseDouble(str));
                break;
            case BOOLEANLITERAL:
                if (str.equals("false")) {
                    code.addCode("li $t0, 0");
                } else code.addCode("li $t0, 1");
                break;
            case STRINGLITERAL:
                int len = str.length();
                code.addCode("li $v0, 9");
                code.addCode("li $a0, " + (4 + len));
                code.addCode("syscall");
                code.addCode("li $t0, $v0");
                code.addCode("li $t1, " + len);
                code.addCode("sw $t1, 0($t0)");
                for (int i = 0; i < len; i++) {
                    code.addCode("li $t2, " + (int) str.charAt(i));
                    code.addCode("sb $t2 " + (i + 4) + "($t0)");
                }
                break;
            case NULL:
                code.addCode("li $t0, 0");
        }
        node.setCode(code);
    }

    public Code generateLValueToIdentifierCodeForClass(Node findNode, String idName){
        Code code = new Code();
        String className = (String) findNode.getChildren().get(0).getValue();
        Clazz clazz = Clazz.getClazzByName(className);
        if (clazz == null) {
            System.out.println("WTF!");
            Compiler.semanticError();
        } else {
            int offset = getAttributeOffset(clazz, idName);
            code.addCode(getClassVariableAddressInClass(offset));
        }
        return code;
    }

    public Code generateLValueToIdentifierCodeForLocal(Node findNode, int index){
        Code code = new Code();
        Node tempNode = findNode;
        int offset = 4 + index * 4; //this + parameters
        if(findNode.getLeftHand() != LeftHand.FunctionDecl) offset += 8; //$fp and $ra
        while (tempNode.getLeftHand() != LeftHand.FunctionDecl) {
            tempNode = tempNode.getParent();
            if (tempNode.getLeftHand() == LeftHand.StmtBlock || tempNode.getLeftHand() == LeftHand.FunctionDecl) {
                offset += 4 * (tempNode.getDefinedVariables().size());
            }
        }
        code.addCode(getLocalVariableAddress(offset));
        return code;
    }

    public void generateLvalueToIdentifierCode(Node node) {
        String idName = (String) node.getChildren().get(0).getValue();
        Node findNode = node;
        Code code = new Code();
        while (true) {
            int index = 0;
            for (Variable variable : findNode.getDefinedVariables()) {
                if (variable.getName().equals(idName)) {
                    if (findNode.getLeftHand() == LeftHand.Program) {
                        code.addCode(getGlobalVariableAddress(variable));
                        node.setCode(code);
                        return;
                    } else if (findNode.getLeftHand() == LeftHand.ClassDecl) {
                        code.addCode(generateLValueToIdentifierCodeForClass(findNode, idName));
                        node.setCode(code);
                        return;
                    } else if (findNode.getLeftHand() != LeftHand.InsideStmtBlock){
                        //todo codo az mammad begir doros kon
                        code.addCode(generateLValueToIdentifierCodeForLocal(findNode, index));
                        node.setCode(code);
                        return;
                    }

                }
                index++;
            }
// extends variables
            if (findNode.getLeftHand() == LeftHand.ClassDecl){
                Node idNode = findNode.getChildren().get(0);
                String className = (String)idNode.getValue();
                Clazz clazz = Clazz.getClazzByName(className);

                for (Variable variable : clazz.getVariables()){
                    if (variable.getName().equals(idName)){
                        if (variable.getAccessMode() == AccessMode.PUBLIC || variable.getAccessMode() == AccessMode.PROTECTED){
                            int offset = getAttributeOffset(clazz, idName);
                            code.addCode(getClassVariableAddressInClass(offset));
                            node.setCode(code);
                            return;
                        }
                    }
                }
            }
            findNode = findNode.getParent();
        }
    }

    public int getAttributeOffset(Clazz clazz, String name) {
        int offset = 0;
        for (Variable variable : clazz.getVariables()) {
            if (variable.getName().equals(name)) break;
            offset += 4;
        }
        return offset;
    }

    public Code createGlobalVariables(ArrayList<Variable> globalVariables) {
        Code code = new Code();
        code.addCode(".data");
        for (Variable variable : globalVariables) {
            if (variable.getType().equals(Type.getTypeByName("double", 0)))
                code.addCode(variable.getName() + ":    .float   0.0");
            else
                code.addCode(variable.getName() + ":    .word   0");
        }
        return code;
    }

    public Code getGlobalVariableAddress(Variable variable) {
        Code code = new Code();
        code.addCode("la $t0, " + variable.getName());
        return code;
    }

    public Code loadIntegerVariable() {
        Code code = new Code();
        code.addCode("lw $t0, 0($t0)");
        return code;
    }

    public Code loadDoubleVariable() {
        Code code = new Code();
        code.addCode("l.s $f0, 0($t0)");
        return code;
    }

    public Code readLine() {
        //todo ghalate in
        Code code = new Code();
        code.addCode("li $v0, 8");
        code.addCode("la $a0, buffer");
        code.addCode("li $a1, 1000");
        code.addCode("syscall");
        return code;
    }

    public Code readInteger() {
        Code code = new Code();
        code.addCode("li $v0, 5");
        code.addCode("syscall");
        code.addCode("move $t0, $v0");
        return code;
    }


    public Code getLocalVariableAddress(int offset) {
        Code code = new Code();
        code.addCode("sub $t0, $fp, " + offset);
        return code;
    }

    public Code getClassVariableAddressInClass(int offset) {
        Code code = new Code();
        code.addCode("lw $t0, 0($fp)");
        code.addCode("add $t0, $t0, " + offset);
        return code;
    }

    public Code getClassVariableAddressOutOfClass(int offset) {
        Code code = new Code();
        code.addCode("add $t0, $t0, " + offset);
        return code;
    }

    public Code calcDotExpr(Node node1, Node node2) {
        Code code = new Code();
        code.addCode(node1.getCode());
        Clazz clazz = Clazz.getClazzByName(node1.getType().getName());
        int offset = 0;
        Type type = Type.getTypeByName("double", 0);
        for (Variable variable : clazz.getVariables()) {
            if (variable.getName().equals(node2.getValue())) {
                type = variable.getType();
                break;
            }
            offset += 4;
        }
        //todo offset ro ba chizi nabayad jam zad?
        code.addCode("add $t0, $t0, " + offset);
        if (!type.equals(Type.getTypeByName("double", 0))) {
            code.addCode("lw $t0, 0($t0)");
        } else {
            code.addCode("l.s $f1, 0($t0)");
        }
        return code;
    }

    public Code calcExpr(Node node, Operator operator) {
        Type t1 = node.getType();
        if (Type.getTypeByName("int", 0).equals(t1) && operator == Operator.SINGLE_MINUS) {
            Code code = new Code();
            generateCode(node);
            code.addCode(node.getCode());
            code.addCode("sub $t0, $zero, $t0");
            return code;
        }
        if (Type.getTypeByName("double", 0).equals(t1) && operator == Operator.SINGLE_MINUS) {
            Code code = new Code();
            generateCode(node);
            code.addCode(node.getCode());
            code.addCode("neg.s $f0, $f0");
            return code;
        }

        if (Type.getTypeByName("bool", 0).equals(t1) && operator == Operator.SINGLE_NOT) {
            Code code = new Code();
//            System.out.println(node.getChildren().get(0).getLeftHand());
            generateCode(node);
            code.addCode(node.getCode());
            code.addCode("xor $t0, $t0, 1");
            return code;
        }
        Compiler.semanticError();
        return null;
    }

    public Code calcExpr(Node node1, Node node2, Operator operator) {
        Type t1 = node1.getType();
        if (operator == Operator.EQ) {
            return assignExprs(node1, node2);
        }
        if (Type.getTypeByName("int", 0).equals(t1)) {
            return calcIntExpr(node1, node2, operator);
        } else if (Type.getTypeByName("bool", 0).equals(t1)) {
            return calcBooleanExpr(node1, node2, operator);
        } else if (Type.getTypeByName("double", 0).equals(t1)) {
            return calcDoubleExpr(node1, node2, operator);
        } else if (Type.getTypeByName("String", 0).equals(t1)) {
            if (operator == Operator.PLUS)
                return arrayPlusArray(node1, node2, 1);
            if (operator == Operator.EQEQ)
                return compareString(node1, node2);
        } else if (t1.getArrayDegree() > 0) {
            return arrayPlusArray(node1, node2, 4);
        }
        Compiler.semanticError();
        return null;
    }

    private Code assignExprs(Node node1, Node node2) {
        Code code = new Code();
        generateCode(node1);
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        generateCode(node2);
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        if (node2.getType().equals(Type.getTypeByName("double", 0)))
            code.addCode("s.s $f0, 0($t1)");
        else
            code.addCode("sw $t0, 0($t1)");
        return code;
    }

    public Code calcIntExpr(Node node1, Node node2, Operator operator) {
        Code code = new Code();
        generateCode(node1);
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        generateCode(node2);
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator) {
            case PLUS:
                code.addCode("add $t0, $t1, $t0");
                break;
            case MINUS:
                code.addCode("sub $t0, $t1, $t0");
                break;
            case DIV:
                code.addCode("div $t0, $t1, $t0");
                break;
            case MULT:
                code.addCode("mul $t0, $t1, $t0");
                break;
            case MOD:
                code.addCode("div $t1, $t0");
                code.addCode("mfhi $t0");
                break;
            case LT:
                code.addCode("slt $t0, $t1, $t0");
                break;
            case LTEQ:
                code.addCode("slt $t0, $t0, $t1");
                code.addCode("xor $t0, $t0, 1");
                break;
            case GT:
                code.addCode("slt $t0, $t0, $t1");
                break;
            case GTEQ:
                code.addCode("slt $t0, $t1, $t0");
                code.addCode("xor $t0, $t0, 1");
                break;
            case EQEQ:
                code.addCode("seq $t0, $t1, $t0");
                break;
            case NOTEQ:
                code.addCode("seq $t0, $t1, $t0");
                code.addCode("xor $t0, $t0, 1");
                break;
        }
        return code;
    }

    public Code calcBooleanExpr(Node node1, Node node2, Operator operator) {
        Code code = new Code();
        generateCode(node1);
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        generateCode(node2);
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator) {
            case OROR:
                code.addCode("or $t0, $t0, $t1");
                break;
            case ANDAND:
                code.addCode("and $t0, $t0, $t1");
                break;
            case EQEQ:
                code.addCode("seq $t0, $t1, $t0");
                break;
            case NOTEQ:
                code.addCode("seq $t0, $t1, $t0");
                code.addCode("xor $t0, $t0, 1");
                break;
        }
        return code;
    }
    //todo nemidunam ke or bayad bokonim ya na

    public Code calcDoubleExpr(Node node1, Node node2, Operator operator) {
        Code code = new Code();
        generateCode(node1);
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("s.s $f0, 0($sp)");
        generateCode(node2);
        code.addCode(node2.getCode());
        code.addCode("l.s $f1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator) {
            case PLUS:
                code.addCode("add.s $f0, $f1, $f0");
                break;
            case MINUS:
                code.addCode("sub.s $f0, $f1, $f0");
                break;
            case MULT:
                code.addCode("mul.s $f0, $f1, $f0");
                break;
            case DIV:
                code.addCode("div.s $f0, $f1, $f0");
                break;
            default:
                if (operator == Operator.LT)
                    code.addCode("c.lt.s $f1, $f0");
                if (operator == Operator.LTEQ)
                    code.addCode("c.le.s $f1, $f0");
                if (operator == Operator.GT)
                    code.addCode("c.gt.s $f1, $f0");
                if (operator == Operator.GTEQ)
                    code.addCode("c.ge.s $f1, $f0");
                if (operator == Operator.EQEQ)
                    code.addCode("c.eq.s $f1, $f0");
                if (operator == Operator.NOTEQ)
                    code.addCode("c.ne.s $f1, $f0");
                code.addCode("li $t0, 0");
                Label label = new Label();
                label.createNewName();
                code.addCode("bclf " + label.getName());
                code.addCode("li $t0, 1");
                code.addCode(label.getName() + " :");
        }
        return code;
    }

    public Code Break(Node node) {
        Code code = new Code();
        code.addCode("j " + node.getBreakLabel().getName());
        return code;
    }

    public Code Continue(Node node) {
        Code code = new Code();
        code.addCode("j " + node.getContinueLabel().getName());
        return code;
    }


    public Code newArray(Node node) {
        Code code = new Code();
        generateCode(node.getChildren().get(0));
        code.addCode(node.getChildren().get(0).getCode());
        //todo age t0 positive nabood chi
        code.addCode("add $t0, $t0, 1");
        code.addCode("move $a0, $t0");
        code.addCode("mul $a0, $a0, 4");
        code.addCode("li $v0, 9");
        code.addCode("syscall");
        code.addCode("sub $t0, $t0, 1");
        code.addCode("sw $t0, 0($v0)");
        code.addCode("move $t0, $v0");
        return code;
    }

    private Code newIdentifier(Node node) {
        Code code = new Code();
        String name = (String) node.getChildren().get(0).getValue();
        int numberOfVariables = Clazz.getClazzByName(name).getVariables().size();
        code.addCode("li $a0, " + numberOfVariables * 4);
        code.addCode("li $v0, 9");
        code.addCode("syscall");
        code.addCode("move $t0, $v0");
        return code;
    }

    public Code ExprOpenBracketExprCloseBracket(Node node) {
        Code code = new Code();
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("lw $t0, 0($sp)");
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        code.addCode("add $t0, $t0, $t1");
        if (node.getChildren().get(0).getType() == Type.getTypeByName("double", 1))
            code.addCode("s.s $f0, 0($t0)");
        else
            code.addCode("sw $f0, 0($t0)");
        //todo inam index check nashoe ...
        return code;
    }

    public Code arrayPlusArray(Node node1, Node node2, int size) {
        Code code = new Code();
        code.addCode(node1.getChildren().get(0).getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node2.getChildren().get(1).getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");

        code.addCode("lw $t2, 0($t0)");
        code.addCode("lw $t3, 0($t1)");

        code.addCode("add $t2, $t2, $t3");
        code.addCode("add $t2, $t2, 1");
        code.addCode("move $a0, $t2");
        code.addCode("mul $a0, $a0, " + size);
        code.addCode("li $v0, 9");
        code.addCode("syscall");

        code.addCode("move $t4, $v0");
        code.addCode("move $t5, $t4");
        code.addCode("sub $t2, $t2, 1");
        code.addCode("sw $t2, 0($t4)");
        code.addCode("lw $t2, 0($t0)");
        code.addCode("add $t4, $t4, " + size);
        Label L1 = new Label();
        L1.createNewName();
        Label L2 = new Label();
        L2.createNewName();
        Label L3 = new Label();
        L3.createNewName();
        code.addCode(L1.getName() + ":");
        code.addCode("beq $t3, 0, " + L2.getName());
        code.addCode("add $t1, $t1, " + size);
        code.addCode("lw $t6, 0($t1)");
        code.addCode("sw $t6, 0($t4)");
        code.addCode("add $t4, $t4, " + size);
        code.addCode("sub $t3, $t3, 1");
        code.addCode("j " + L1.getName());
        code.addCode(L2.getName() + ":");
        code.addCode("beq $t2, 0, " + L2.getName());
        code.addCode("add $t0, $t0, + " + size);
        code.addCode("lw $t6, 0($t0)");
        code.addCode("sw $t6, 0($t4)");
        code.addCode("add $t4, $t4, " + size);
        code.addCode("sub $t2, $t2, 1");
        code.addCode("j " + L2.getName());
        code.addCode(L3.getName() + ":");
        code.addCode("move $t0, $t5");
        return code;
    }

    public Code compareString(Node node1, Node node2) {
        Code code = new Code();
        code.addCode(node1.getChildren().get(0).getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");

        code.addCode(node2.getChildren().get(1).getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("sw $t0, 0($sp)");
        code.addCode("add $sp, $sp, 4");

        code.addCode("lw $t2, 0($t0)");
        code.addCode("lw $t3, 0($t1)");

        Label L1 = new Label();
        L1.createNewName();
        Label L2 = new Label();
        L2.createNewName();
        Label L3 = new Label();
        L3.createNewName();
        Label L4 = new Label();
        L4.createNewName();
       /* code.addCode("bne $t2, $t3, " + L1.getName());
        code.addCode("add $t0, $t0, 4");
        code.addCode("add $t1, $t1, 4");*/
        code.addCode(L2.getName() + " :");
        //code.addCode("beq $t2, 0, " + L3.getName());
        code.addCode("lb $t4, 0($t2)");
        code.addCode("lb $t5, 0($t3)");
        code.addCode("bne $t4, $t5, " + L1.getName());
        code.addCode("beq $t2, 0, " + L3.getName());
        code.addCode("addi $t2, $t2, 1");
        code.addCode("addi $t3, $t3, 1");
        code.addCode("j " + L2.getName());
        code.addCode(L3.getName() + " :");
        code.addCode("li $t0, 1");
        code.addCode("j " + L4.getName());
        code.addCode(L1.getName() + " :");
        code.addCode("li $t0, 0");
        code.addCode(L4.getName() + " :");
        //todo bayad in doros she
        //        if (node.getProductionRule() == ProductionRule.Expr_EQUAL_Expr)
//            code.addCode("xor $t0, $t0, 1");
        return code;
    }


    public void call(Node node) {
        if (node.getProductionRule().equals("IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS")) {
            simpleCall(node);
        } else {
            extendCall(node);
        }
    }

    public Code simpleCall(Node node) {
        Code code = new Code();

        return code;
    }

    public Code extendCall(Node node) {
        Code code = new Code();
        return code;
    }
}
