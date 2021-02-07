enum AccessMode{
    PROTECTED, PUBLIC, PRIVATE;
}

public class Variable {
    private String name;
    private String type;
    private int number;
    private AccessMode accessMode;
    public String getType() {
        return type;
    }

    public Variable(){

    }

    public Variable(String name, String type, int number, AccessMode accessMode){
        this.name = name;
        this.type = type;
        this.number = number;
        this.accessMode = accessMode;
    }

    public void setType(String type) {
        this.type = type;
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
}
