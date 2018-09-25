package duc.ugcm.ast;

public class Relation implements Property {
    private String name;
    private Class type;
    private Relation oppositeOf;
    private Multiplicity multiplicity;
    private boolean isContained;

    public Relation(String name, Class type, boolean isContained) {
        this.name = name;
        this.type = type;
        this.isContained = isContained;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Relation getOppositeOf() {
        return oppositeOf;
    }

    public void setOpposite(Relation oppositeOf) {
        this.oppositeOf = oppositeOf;
    }

    public Multiplicity getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Multiplicity multiplicity) {
        this.multiplicity = multiplicity;
    }

    public boolean isContained() {
        return isContained;
    }

    public void setContained(boolean contained) {
        isContained = contained;
    }
}
