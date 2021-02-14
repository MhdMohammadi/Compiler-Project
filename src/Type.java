import java.awt.*;
import java.rmi.registry.LocateRegistry;
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
    private int arrayDegree;
    private String name;

    public static void createPreType(String name, String parent){
        PreType preType = new PreType(name, parent);
        allPreTypes.add(preType);
    }

    //todo age circular bashe mitereke
    public static boolean validate(){
        allPreTypes.add(new PreType("int", null));
        allPreTypes.add(new PreType("double", null));
        allPreTypes.add(new PreType("boolean", null));
        allPreTypes.add(new PreType("string", null));
        for (int i = 0; i < allPreTypes.size(); i++) {
            for (int j = 0; j < allPreTypes.size(); j++) {
                if (i != j) {
                    if (allPreTypes.get(i).getName().equals(allPreTypes.get(j).getName())) {
                        Compiler.semanticError();
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
            if(parentName == null) continue;
            if (parentName.equals("int") || parentName.equals("double") || parentName.equals("boolean") || parentName.equals("string"))
                Compiler.semanticError();
            if (parentName.equals(allPreTypes.get(i).name))
                Compiler.semanticError();
            Type x = getTypeByName(parentName, 0);
            if (x == null)
                Compiler.semanticError();
            else
                allTypes.get(i).setParent(x);
        }
        return true;
    }

    public static Type createArrayType(Type type){
        for(Type type1 : allTypes){
            if(type1.name.equals(type.name) && type1.arrayDegree == type.arrayDegree + 1)
                return type1;
        }
        Type type1 = new Type();
        type1.arrayDegree = type.arrayDegree + 1;
        type1.name = type.name;
        allTypes.add(type1);
        return type1;
    }

    public static Type getTypeByName(String name, int degree){
        for(Type type : allTypes)
            if(type.name.equals(name) && type.arrayDegree == degree)
                return type;
        return null;
    }

    public static boolean possible(Type t1, Type t2, Operator operator){
        if(t1 != t2) return false;

        Type INT = getTypeByName("int", 0);
        Type DOUBLE = getTypeByName("double", 0);
        Type BOOLEAN = getTypeByName("boolean", 0);
        Type STRING = getTypeByName("string", 0);

        if(operator == Operator.MOD && t1 != INT) return false;
        if((operator == Operator.MINUS || operator == Operator.DIV || operator == Operator.MULT)
                && (t1 != INT && t1 != DOUBLE)) return false;
        if((operator == Operator.PLUS) && (t1 != INT && t1 != DOUBLE && t1 != BOOLEAN && t1 != STRING && t1.arrayDegree == 0)) return false;
        if((operator == Operator.ANDAND || operator == Operator.OROR || operator == Operator.SINGLE_NOT || operator == Operator.LT
            || operator == Operator.GT || operator == Operator.LTEQ || operator == Operator.GTEQ || operator == Operator.EQEQ
            || operator == Operator.NOTEQ) && t1 != BOOLEAN) return false;

        return true;
    }

    public static boolean possible(Type t1, Operator operator){
        Type INT = getTypeByName("int", 0);
        Type DOUBLE = getTypeByName("double", 0);
        Type BOOLEAN = getTypeByName("boolean", 0);
        if(operator == Operator.SINGLE_NOT && t1 == BOOLEAN) return true;
        if(operator == Operator.SINGLE_MINUS && (t1 == INT || t1 == DOUBLE)) return true;
        return false;
    }

    public Type getParent() {
        return parent;
    }

    public void setParent(Type parent) {
        this.parent = parent;
    }

    public int isArray() {
        return arrayDegree;
    }

    public void setArray(int array) {
        arrayDegree = array;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
