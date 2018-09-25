package duc.ugcm.parser;

import duc.ugcm.ast.PrimitiveType;

public class PrimitiveTypeHelper {

    public static PrimitiveType getType(String name) {
        PrimitiveType[] values = PrimitiveType.values();

        for (int i = 0; i < values.length; i++) {
            if(values[i].name().equals(name)) {
                return values[i];
            }
        }

        throw new RuntimeException("PrimitiveType " + name + " is unknown.");
    }
}
