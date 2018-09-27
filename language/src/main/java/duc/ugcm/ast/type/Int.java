package duc.ugcm.ast.type;

public class Int extends PrimitiveType {
    public static final java.lang.String NAME = "Int";

    private static final Int INSTANCE = new Int();
    private Int(){}

    public static Int getInstance() {
        return INSTANCE;
    }
}
