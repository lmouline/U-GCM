package duc.ugcm.generator.kmf;

import duc.ugcm.ast.PrimitiveType;

public class PrimitiveTypeHelper {

    public String toJavaType(PrimitiveType type) {
        switch (type) {
            case Short: return "short";
            case Int: return "int";
            case Long: return "long";
            case Float: return "float";
            case Double: return "double";
            case Char: return "char";
            case Bool: return "boolean";
            case String: return "java.lang.String";
            default: return "unknown";
        }
    }

    public String toKMFType(PrimitiveType type) {
        String baseName = "org.kevoree.modeling.api.meta.PrimitiveTypes.";

        switch (type) {
            case Short: return baseName + "SHORT";
            case Int: return baseName + "INT";
            case Long: return baseName + "LONG";
            case Float: return baseName + "FLOAT";
            case Double: return baseName + "DOUBLE";
            case Char: return baseName + "STRING";
            case Bool: return baseName + "BOOL";
            case String: return baseName + "STRING";
            default: return "unknown";
        }
    }
}
