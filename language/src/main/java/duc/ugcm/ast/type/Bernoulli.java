package duc.ugcm.ast.type;

public class Bernoulli extends UncertainTypes {
    public static final java.lang.String NAME = "Bernoulli";

    private static final Bernoulli INSTANCE = new Bernoulli();
    private Bernoulli(){}

    public static Bernoulli getInstance() {
        return INSTANCE;
    }
}
