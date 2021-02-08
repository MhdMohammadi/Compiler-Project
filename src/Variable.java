
public class Variable {
    private String name;
    Type type;
    private int nodeIndex;
    private int number;
    private AccessMode accessMode;

    public Variable() {

    }

    public Variable(String name, Type type, int number, AccessMode accessMode) {
        this.name = name;
        this.type = type;
        this.number = number;
        this.accessMode = accessMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
