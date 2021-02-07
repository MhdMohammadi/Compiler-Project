public class Type {
    private Type parent;
    private boolean isArray;
    private String name;

    public Type getParent() {
        return parent;
    }

    public void setParent(Type parent) {
        this.parent = parent;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
