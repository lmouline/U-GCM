package duc.ugcm.ast.type;

public class Double extends PrimitiveType {
    public static final java.lang.String NAME = "Double";

    private static final Double INSTANCE = new Double();
    private Double(){}

    public static Double getInstance() {
        return INSTANCE;
    }
}
