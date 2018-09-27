package duc.ugcm.ast.type;

public class String extends PrimitiveType {
    public static final java.lang.String NAME = "String";

    private static final String INSTANCE = new String();
    private String(){}

    public static String getInstance() {
        return INSTANCE;
    }
}
