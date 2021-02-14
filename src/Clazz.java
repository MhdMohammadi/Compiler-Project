import java.util.ArrayList;

public class Clazz {
    private ArrayList<Variable> variables = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();

    public ArrayList<Variable> getVariables() {
        return variables;
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
}
