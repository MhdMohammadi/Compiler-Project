import java.util.ArrayList;

public class Function {
    private String name;
    private ArrayList<Variable> parameter = new ArrayList<Variable>();

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

}
