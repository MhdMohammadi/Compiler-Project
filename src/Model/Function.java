package Model;

import java.util.ArrayList;

public class Function {
    private String name;
    private Type type;
    private AccessMode accessMode = AccessMode.PUBLIC;
    private ArrayList<Variable> parameter = new ArrayList<Variable>();
    private Node node;

    public Function(){
        this.accessMode = AccessMode.PUBLIC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Variable> getParameter() {
        return parameter;
    }

    public void setParameter(ArrayList<Variable> parameter) {
        this.parameter = parameter;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
