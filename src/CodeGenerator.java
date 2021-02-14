public class CodeGenerator {
    public String readLine(){
        String s = "";
        s += "li $v0, 5\n";
        System.out.println("syscall");
        System.out.println("move $t0, $v0");
    }

    public void readInteger(){
        System.out.println("li $v0, 8");
        System.out.println("la $a0, buffer");
        System.out.println("li $a1, 1000");
        System.out.println("syscall");
    }
}
