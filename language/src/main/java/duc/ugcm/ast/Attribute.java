package duc.ugcm.ast;


public class Attribute implements Property {
    private String name;
    private PrimitiveType type;
    private boolean isId;
    private int index;

    public Attribute(String name, PrimitiveType type, boolean isId, int index) {
        this.name = name;
        this.type = type;
        this.isId = isId;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }
}
