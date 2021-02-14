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

}
