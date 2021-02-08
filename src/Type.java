import java.awt.*;
import java.util.ArrayList;

public class Type {
    static class PreType{
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

    public static void createPreType(String name, String parent){
        PreType preType = new PreType(name, parent);
        allPreTypes.add(preType);
    }

    public static boolean validate(){
        allPreTypes.add(new PreType("int", null));
        allPreTypes.add(new PreType("double", null));
        allPreTypes.add(new PreType("boolean", null));
        allPreTypes.add(new PreType("string", null));
        for (int i = 0; i < allPreTypes.size(); i++) {
            for (int j = 0; j < allPreTypes.size(); j++) {
                if (i != j) {
                    if (allPreTypes.get(i).getName().equals(allPreTypes.get(j).getName())) {
                        return false;
                    }
                }
            }
        }
        for (PreType p: allPreTypes){
            Type type = new Type();
            type.setName(p.getName());
            allTypes.add(type);
        }
        for (int i = 0; i < allPreTypes.size(); i++){
            String parentName = allPreTypes.get(i).getParent();
            if(parentName.equals("int") || parentName.equals("double") || parentName.equals("boolean") || parentName.equals("string"))
                return false;
            Type x = getTypeByName(allPreTypes.get(i).getParent());
            if (x == null){
                return false;
            } else {
                allTypes.get(i).setParent(x);
            }
        }
        return true;
    }

    public static Type getTypeByName(String name){
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
