package duc.ugcm.parser;


import duc.ugcm.ast.type.*;
import duc.ugcm.ast.type.Double;
import duc.ugcm.ast.type.Float;
import duc.ugcm.ast.type.Long;
import duc.ugcm.ast.type.Short;
import duc.ugcm.ast.type.String;


public class PrimitiveTypeHelper {

    public static Type getType(java.lang.String name) {
        switch (name) {
            case Bool.NAME: return Bool.getInstance();
            case Char.NAME: return Char.getInstance();
            case Double.NAME: return Double.getInstance();
            case Float.NAME: return Float.getInstance();
            case Int.NAME: return Int.getInstance();
            case Long.NAME: return Long.getInstance();
            case Short.NAME: return Short.getInstance();
            case String.NAME: return String.getInstance();

        }

        throw new RuntimeException("PrimitiveType " + name + " is unknown.");
    }
}
