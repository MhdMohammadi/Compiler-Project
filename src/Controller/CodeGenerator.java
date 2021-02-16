package Controller;

import Model.Code;
import Model.Type;
import Model.*;
import Enum.*;

import java.util.ArrayList;

public class CodeGenerator {
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

    public Code getGlobalVariableAddress(Variable variable){
        Code code = new Code();
        code.addCode("la $t0, " + variable.getName());
        return code;
    }

    public Code storeIntegerVariable(){
        Code code = new Code();
        code.addCode("lw $t0, 0($t0)");
        return code;
    }

    public Code storeDoubleVariable(){
        Code code = new Code();
        code.addCode("l.s $f0, 0($t0)");
        return code;
    }

    public Code readLine() {
        Code code = new Code();
        code.addCode("li $v0, 5");
        code.addCode("syscall");
        code.addCode("move $t0, $v0");
        return code;
    }

    public Code getLocalVariableAddress(int offset){
        Code code = new Code();
        code.addCode("sub $t0, $fp, " + offset);
        return code;
    }

    public Code getClassVariableAddress(int offset){
        Code code = new Code();
        code.addCode("lw $t0, 0($fp)");
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

    public Code readInteger() {
        Code code = new Code();
        code.addCode("li $v0, 8");
        code.addCode("la $a0, buffer");
        code.addCode("li $a1, 1000");
        code.addCode("syscall");
        return code;
    }

    public Code print(ArrayList<Node> nodes) {
        Code code = new Code();
        for (Node node : nodes) {
            code.addCode(node.getCode());
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
        }
        return code;
    }

    public Code calcExpr(Node node1, Node node2, Operator operator) {
        Type t1 = node1.getType();
        if (Type.getTypeByName("int", 0).equals(t1)) {
            return calcIntExpr(node1, node2, operator);
        } else if (Type.getTypeByName("boolean", 0).equals(t1)) {
            return calcBooleanExpr(node1, node2, operator);
        } else if (Type.getTypeByName("double", 0).equals(t1)) {
            return calcDoubleExpr(node1, node2, operator);
        }
        return null;
    }

    public Code calcIntExpr(Node node1, Node node2, Operator operator) {
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
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
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
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
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("s.s $f0, 0($sp)");
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
        }
        return code;
    }

    public Code ifCondition(Node node) {
        Code code = new Code();
        code.addCode(node.getChildren().get(0).getCode());
        Label label = new Label();
        Label label1 = new Label();
        label.creatNewName();
        label1.creatNewName();
        code.addCode("beq $t0, 0, " + label1.getName());
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName());
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
        label.creatNewName();
        label1.creatNewName();
        code.addCode(label.getName() + ":");
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("beq $t0, 0, " + label1.getName());
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName() + ":");
        return code;
    }

    public Code forLoop(Node node) {
        Code code = new Code();
        Label label = new Label();
        label.creatNewName();
        Label label1 = new Label();
        label1.creatNewName();
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode(label.getName() + ":");
        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("beq $t0, 0, " + label1.getName());
        code.addCode(node.getChildren().get(3).getCode());
        code.addCode(node.getChildren().get(2).getCode());
        code.addCode("j " + label.getName());
        code.addCode(label1.getName() + ":");
        return code;
    }

    public Code newArray(Node node) {
        Code code = new Code();
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

    public Code arrayPlusArray(Node node, int size) {
        Code code = new Code();
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node.getChildren().get(1).getCode());
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
        L1.creatNewName();
        Label L2 = new Label();
        L2.creatNewName();
        Label L3 = new Label();
        L3.creatNewName();
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

    public Code compareString(Node node) {
        Code code = new Code();
        code.addCode(node.getChildren().get(0).getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");

        code.addCode(node.getChildren().get(1).getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");

        code.addCode("lw $t2, 0($t0)");
        code.addCode("lw $t3, 0($t1)");

        Label L1 = new Label();
        L1.creatNewName();
        Label L2 = new Label();
        L2.creatNewName();
        Label L3 = new Label();
        L3.creatNewName();
        Label L4 = new Label();
        L4.creatNewName();
        code.addCode("bne $t2, $t3, " + L1.getName());
        code.addCode("add $t0, $t0, 4");
        code.addCode("add $t1, $t1, 4");
        code.addCode(" :");
        code.addCode("beq $t2, 0, " + L3.getName());
        code.addCode("lb $t4, 0($t0)");
        code.addCode("lb $t5, 0($t1)");
        code.addCode("bne $t4, $t5, " + L1.getName());
        code.addCode("sub $t2, $t2, 1");
        code.addCode("j " + L2.getName());
        code.addCode(L3.getName() + " :");
        code.addCode("li $t0, 1");
        code.addCode("j " + L4.getName());
        code.addCode(L1.getName() + " :");
        code.addCode("li $t0, 0");
        code.addCode(L4.getName() + " :");
        if (node.getProductionRule() == ProductionRule.Expr_EQUAL_Expr)
            code.addCode("xor $t0, $t0, 1");
        return code;
    }

    public Code itod(Node node){
        Code code = new Code();
        code.addCode(node.getCode());
        code.addCode("mtc1 $t0, $f0");
        code.addCode("cvt.s.w $f0, $f0");
        return code;
    }

    public Code dtoi(Node node){
        Code code = new Code();
        code.addCode(node.getCode());
        code.addCode("cvt.w.s $f0, $f0");
        code.addCode("mfc1 $t0, $f0");
        return code;
    }

    public Code itob(Node node){
        Code code = new Code();
        Label label = new Label();
        Label label1 = new Label();
        label.creatNewName();
        label1.creatNewName();
        code.addCode(node.getCode());
        code.addCode("beq $t0, 0" + label.getName());
        code.addCode("li $t0, 1");
        code.addCode("j " + label1.getName());
        code.addCode(label.getName() + ":");
        code.addCode("li $t0, 0");
        code.addCode(label1.getName() + ":");
        return code;
    }

    public void call(Node node){
        if (node.getProductionRule().equals("IDENTIFIER_OPENPARENTHESIS_Actuals_CLOSEPARENTHESIS")){
            simpleCall(node);
        } else {
            extendCall(node);
        }
    }

    public Code simpleCall(Node node){
        Code code = new Code();

        return code;
    }

    public Code extendCall(Node node){
        Code code = new Code();
        return code;
    }
}
