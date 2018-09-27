package duc.ugcm.ast.type;

public class Char extends PrimitiveType {
    public static final java.lang.String NAME = "Char";

    private static final Char INSTANCE = new Char();
    private Char(){}

    public static Char getInstance() {
        return INSTANCE;
    }
}
