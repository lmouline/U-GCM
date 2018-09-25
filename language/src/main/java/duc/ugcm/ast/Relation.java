package duc.ugcm.ast;

public class Relation implements Property {
    private String name;
    private Class type;
    private Relation oppositeOf;
    private Multiplicity multiplicity;
    private boolean isContained;
    private int index;

    public Relation(String name, Class type, boolean isContained, int index) {
       this(name, type, isContained);
        this.index = index;
    }

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

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isMultiple() {
        return multiplicity != null && multiplicity.getUpperBound() > 1;
    }
}
