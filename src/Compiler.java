public class Compiler {
    private Node root;
    private int cnt = 0;

    public Compiler(Node root){
        this.root = root;
    }

    public static void semanticError(){
        System.out.println("semantic error");
        System.exit(0);
    }

    public void preProcess(Node v){
        v.setIndex(cnt);
        for(Variable variable : v.getDefinedVariables())
            variable.setNodeIndex(v.getIndex());
        cnt ++;
        for (Node node: v.getChildren()) {
            node.setParent(v);
            preProcess(node);
        }
    }

    public boolean setVariablesType(Node v){
        if(v.getLeftHand() == LeftHand.Variable){
            Type type = Type.getTypeByName((String)v.getChildren().get(0).getValue());
            if(type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for(Node node : v.getChildren())
            setVariablesType(node);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
