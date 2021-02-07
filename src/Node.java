import java.util.ArrayList;


enum Type{
    INT, DOUBLE, STRING, BOOLEAN
}

public class Node {
    private LeftHand leftHand;
    private ProductionRule productionRule;
    private ArrayList<Node> children = new ArrayList<Node>();
    private Object value;
    private Type type;

    public Node(LeftHand leftHand, ProductionRule productionRule){
        this.leftHand = leftHand;
        this.productionRule = productionRule;
    }

    public ProductionRule getProductionRule() {
        return productionRule;
    }

    public void setProductionRule(ProductionRule productionRule) {
        this.productionRule = productionRule;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LeftHand getLeftHand() {
        return leftHand;
    }

    public void setLeftHand(LeftHand leftHand) {
        this.leftHand = leftHand;
    }
}