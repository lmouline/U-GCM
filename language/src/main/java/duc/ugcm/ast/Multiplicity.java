package duc.ugcm.ast;

public class Multiplicity {
    private int lowerBound;
    private int upperBound;

    public Multiplicity(int lowerBound, int upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }
}
