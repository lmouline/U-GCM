package duc.ugcm.ast;

public interface Property {
    String getName();
    int getIndex();
    void setIndex(int index);
    Property clone();
}
