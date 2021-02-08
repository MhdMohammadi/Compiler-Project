public class Compiler {
    private Node root;
    private int cnt = 0;

    public Compiler(Node root){
        this.root = root;
    }

    public void preProcess(Node v){
        v.setIndex(cnt);
        cnt ++;
        for (Node node: v.getChildren()) {
            node.setParent(v);
            preProcess(node);
        }
    }

    public Variable findVariable(Node node){
        Node node1 = node;
        while (true){
            for (Variable variable: node1.getDefinedVariables()){
                if (variable.getName().equals((String)node.getValue())){
                    if (variable.getNumber() < node.getIndex()) {
                        return variable;
                    }
                }
            }
            if (node1.getParent() == null){
                break;
            } else {
                node1 = node1.getParent();
            }
        }
        return null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
