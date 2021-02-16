package Model;

import Enum.*;
import java.util.ArrayList;

public class Node {
    private AccessMode accessMode;
    private LeftHand leftHand;
    private ProductionRule productionRule;
    private ArrayList<Node> children = new ArrayList<Node>();
    private Object value;
    private Code code;
    private Label breakLabel = new Label();
    private Label continueLabel = new Label();

    private String typeName;
    private int arrayDegree = 0;

    private Type type;
    private ArrayList<Variable> definedVariables = new ArrayList<Variable>();
    private ArrayList<Function> definedFunctions = new ArrayList<>();
    private int index;
    private Node parent;

    private ArrayList<Type> actualsTypes = new ArrayList<Type>();

    public Node(LeftHand leftHand, ProductionRule productionRule){
        this.leftHand = leftHand;
        this.productionRule = productionRule;
    }


    public Label getBreakLabel() {
        return breakLabel;
    }

    public void setBreakLabel(Label breakLabel) {
        this.breakLabel = breakLabel;
    }

    public Label getContinueLabel() {
        return continueLabel;
    }

    public void setContinueLabel(Label continueLabel) {
        this.continueLabel = continueLabel;
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

    public ArrayList<Variable> getDefinedVariables() {
        return definedVariables;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getArrayDegree() {
        return arrayDegree;
    }

    public void setArrayDegree(int arrayDegree) {
        this.arrayDegree = arrayDegree;
    }

    public AccessMode getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(AccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public ArrayList<Function> getDefinedFunctions() {
        return definedFunctions;
    }

    public void setDefinedFunctions(ArrayList<Function> definedFunctions) {
        this.definedFunctions = definedFunctions;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public ArrayList<Type> getActualsTypes() {
        return actualsTypes;
    }

    public void setActualsTypes(ArrayList<Type> actualsTypes) {
        this.actualsTypes = actualsTypes;
    }
}