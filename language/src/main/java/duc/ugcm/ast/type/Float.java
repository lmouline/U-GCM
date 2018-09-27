package duc.ugcm.ast.type;

public class Float extends PrimitiveType {
    public static final java.lang.String NAME = "Float";

    private static final Float INSTANCE = new Float();
    private Float(){}

    public static Float getInstance() {
        return INSTANCE;
    }
}
