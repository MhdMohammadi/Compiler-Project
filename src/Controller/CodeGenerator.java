package Controller;

import Model.Code;
import Model.Type;
import Model.*;
import Enum.*;

import java.util.ArrayList;

public class CodeGenerator {
    public Code readLine() {
        Code code = new Code();
        code.addCode("li $v0, 5");
        code.addCode("syscall");
        code.addCode("move $t0, $v0");
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
        for (Node node: nodes) {
            code.addCode(node.getCode());
            if (Type.getTypeByName("int",0).equals(node.getType())){
                code.addCode("li $v0, 1");
                code.addCode("move $a0, $t0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("double",0).equals(node.getType())){
                code.addCode("li $v0, 3");
                code.addCode("mov.s $f12, $f0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("string",0).equals(node.getType())){
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
        } else if (Type.getTypeByName("double", 0).equals(t1)){
            return calcDoubleExpr(node1, node2, operator);
        }
        return null;
    }

    public Code calcIntExpr (Node node1, Node node2, Operator operator){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator){
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
                code.addCode("div $t0, $t1, $t0");
                break;
        }
        return code;
    }

    public Code calcBooleanExpr(Node node1, Node node2, Operator operator){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator){
            case OROR:
                code.addCode("or $t0, $t0, $t1");
                break;
            case ANDAND:
                code.addCode("and $t0, $t0, $t1");
                break;
        }
        return code;
    }
    //todo nemidunam ke or bayad bokonim ya na

    public Code calcDoubleExpr(Node node1, Node node2, Operator operator){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("s.s $f0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("l.s $f1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        switch (operator){
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

    public Code ifCondition(Node node){
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


    public Code whileLoop(Node node){
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

    public Code forLoop(Node node){
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


}
