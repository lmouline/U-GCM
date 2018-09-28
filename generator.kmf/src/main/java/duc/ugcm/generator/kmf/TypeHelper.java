package duc.ugcm.generator.kmf;

import duc.ugcm.ast.type.*;
import duc.ugcm.ast.type.Double;
import duc.ugcm.ast.type.Float;
import duc.ugcm.ast.type.Long;
import duc.ugcm.ast.type.Short;
import duc.ugcm.ast.type.String;

public class TypeHelper {

    public java.lang.String toJavaType(Type type) {
        if(type instanceof Short) {
            return "java.lang.Short";
        }

        if(type instanceof Int) {
            return "java.lang.Integer";
        }

        if(type instanceof Long) {
            return "java.lang.Long";
        }

        if(type instanceof Float) {
            return "java.lang.Float";
        }

        if(type instanceof Double) {
            return "java.lang.Double";
        }

        if(type instanceof Char) {
            return "java.lang.Char";
        }

        if(type instanceof Bool) {
            return "java.lang.Boolean";
        }

        if(type instanceof String) {
            return "java.lang.String";
        }

        if(type instanceof Bernoulli) {
            return "duc.probability.discrete.Bernoulli";
        }

        throw new RuntimeException("Unrecognized type: " + type.getClass().getTypeName());

    }

    public java.lang.String toKMFType(Type type) {
        java.lang.String baseName = "org.kevoree.modeling.api.meta.PrimitiveTypes.";

        if(type instanceof Short) {
            return baseName + "SHORT";
        }

        if(type instanceof Int) {
            return baseName + "INT";
        }

        if(type instanceof Long) {
            return baseName + "LONG";
        }

        if(type instanceof Float) {
            return baseName + "FLOAT";
        }

        if(type instanceof Double) {
            return baseName + "DOUBLE";
        }

        if(type instanceof Char) {
            return baseName + "STRING";
        }

        if(type instanceof Bool) {
            return baseName + "BOOL";
        }

        if(type instanceof String) {
            return baseName + "STRING";
        }

        throw new RuntimeException("Unrecognized type: " + type.getClass().getTypeName());

    }

}
