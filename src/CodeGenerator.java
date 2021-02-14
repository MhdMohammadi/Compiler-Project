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
            if (node.getType())
        }
    }

    public Code addExpr(Node node1, Node node2) {
        Type t1 = node1.getType();
        if (Type.getTypeByName("int", 0).equals(t1)) {
            return addExprInt(node1, node2);
        } else if (Type.getTypeByName("boolean", 0).equals(t1)) {
            return addExprBoolean(node1, node2);
        } else if (Type.getTypeByName("double", 0).equals(t1)){
            return addExprDouble(node1, node2);
        }
        return null;
    }

    public Code addExprInt(Node node1, Node node2){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        code.addCode("add $t0, $t0, $t1");
        return code;
    }

    public Code addExprBoolean(Node node1, Node node2){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $t0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("lw $t1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        code.addCode("or $t0, $t0, $t1");
        return code;
    }

    public Code addExprDouble(Node node1, Node node2){
        Code code = new Code();
        code.addCode(node1.getCode());
        code.addCode("sub $sp, $sp, 4");
        code.addCode("sw $f0, 0($sp)");
        code.addCode(node2.getCode());
        code.addCode("lw $f1, 0($sp)");
        code.addCode("add $sp, $sp, 4");
        code.addCode("add $f0, $f0, $f1");
        return code;
    }
}
