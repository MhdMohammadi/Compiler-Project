import java.util.ArrayList;

public class Clazz{
    private static ArrayList<Clazz> clazzes = new ArrayList<Clazz>();
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();
    private Type type;
    private String name;
    private boolean isSetAttributesAndFunctions = false;

    public Clazz(){
        clazzes.add(this);
    }

    public static Clazz getClazzByName(String name){
        for (Clazz clazz : getClazzes()){
            if(clazz.getName().equals(name))return clazz;
        }
        return null;
    }

    public static ArrayList<Clazz> getClazzes() {
        return clazzes;
    }

    public static void setClazzes(ArrayList<Clazz> clazzes) {
        Clazz.clazzes = clazzes;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public Clazz getParent(){
        if(this.getType().getParent() == null)return null;
        return Clazz.getClazzByName(this.getType().getParent().getName());
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<Function> functions) {
        this.functions = functions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSetAttributesAndFunctions() {
        return isSetAttributesAndFunctions;
    }

    public void setSetAttributesAndFunctions(boolean setAttributesAndFunctions) {
        isSetAttributesAndFunctions = setAttributesAndFunctions;
    }
}
