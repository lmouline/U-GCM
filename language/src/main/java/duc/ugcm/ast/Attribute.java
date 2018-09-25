package duc.ugcm.ast;


public class Attribute implements Property {
    private String name;
    private PrimitiveType type;
    private boolean isId;

    public Attribute(String name, PrimitiveType type, boolean isId) {
        this.name = name;
        this.type = type;
        this.isId = isId;
    }

    public String getName() {
        return name;
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
