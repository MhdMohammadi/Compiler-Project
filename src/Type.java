import java.awt.*;
import java.util.ArrayList;

public class Type {
    class PreType{
        private String name, parent;

        public PreType(String name, String parent){
            this.name = name;
            this.parent = parent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }
    }

    public static ArrayList<Type> allTypes = new ArrayList<>();
    public static ArrayList<PreType> allPreTypes = new ArrayList<>();

    private Type parent;
    private boolean isArray;
    private String name;

    public void createPreType(String name, String parent){
        PreType preType = new PreType(name, parent);
        allPreTypes.add(preType);
    }

    public boolean validate(){

        return false;
    }

    public Type getTypeByName(String name){
        for(Type type : allTypes)
            if(type.name.equals(name))
                return type;
        return null;
    }

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
