public class Compiler {
    private Node root;
    private int cnt = 0;

    public Compiler(Node root){
        this.root = root;
    }

    public void preprocess(Node v){
        v.setIndex(cnt);
        cnt ++;
        for (Node node: v.getChildren()) {
            node.setParent(v);
            preprocess(node);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
