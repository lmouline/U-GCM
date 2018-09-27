package duc.ugcm.ast.type;

public class Short extends PrimitiveType {
    public static final java.lang.String NAME = "Short";

    private static final Short INSTANCE = new Short();
    private Short(){}

    public static Short getInstance() {
        return INSTANCE;
    }
}
