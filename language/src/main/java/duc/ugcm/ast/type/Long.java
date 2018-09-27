package duc.ugcm.ast.type;

public class Long extends PrimitiveType {
    public static final java.lang.String NAME = "Long";

    private static final Long INSTANCE = new Long();
    private Long(){}

    public static Long getInstance() {
        return INSTANCE;
    }
}
