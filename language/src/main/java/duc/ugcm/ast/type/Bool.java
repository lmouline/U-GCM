package duc.ugcm.ast.type;

public class Bool extends PrimitiveType {
    public static final java.lang.String NAME = "Bool";

    private static final Bool INSTANCE = new Bool();
    private Bool(){}

    public static Bool getInstance() {
        return INSTANCE;
    }
}
