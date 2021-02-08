import java.util.ArrayList;

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

    public boolean setVariablesType(Node v){
        if(v.getLeftHand() == LeftHand.Variable){
            Type type = Type.getTypeByName((String)v.getChildren().get(0).getValue());
            if(type == null) Compiler.semanticError();
            v.getDefinedVariables().get(0).setType(type);
        }
        for(Node node : v.getChildren())
            setVariablesType(node);
    }

    public boolean areAllVariablesUnique(Node v){
        ArrayList<Variable> variables = v.getDefinedVariables();
        for(int i = 0; i < variables.size(); i++)
            for(int j = i + 1; j < variables.size(); j++) {
                if (variables.get(i).getName().equals(variables.get(j).getName()))
                    semanticError();
            }
        return true;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
