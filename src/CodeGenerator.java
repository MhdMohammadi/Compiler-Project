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
                code.addCode("mov.d $f12, $f0");
                code.addCode("syscall");
            } else if (Type.getTypeByName("string",0).equals(node.getType())){
                code.addCode("li $v0, 4");
                code.addCode("move $a0, $t0");
                code.addCode("syscall");
            }
        }
        return code;
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

    public Code addExprInt(Node node1, Node nod2){
        return null;
    }

    public Code addExprBoolean(Node node1, Node node2){
        return null;
    }

    public Code addExprDouble(Node node1, Node node2){
        return null;
    }
}
