package duc.ugcm.ast;


import duc.ugcm.ast.type.Type;

public class Attribute implements Property {
    private String name;
    private Type type;
    private boolean isId;
    private int index;

    public Attribute(String name, Type type, boolean isId) {
        this.name = name;
        this.type = type;
        this.isId = isId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    @Override
    public Property clone() {
        return new Attribute(this.name, this.type, this.isId);
    }
}
