package duc.ugcm.parser;

import duc.ugcm.ast.*;
import duc.ugcm.ast.Class;

class ParserHelper {

    static Class getOrCreateClass(Model model, String name) {
        if(model.containClass(name)) {
            return model.getClass(name);
        }

        Class newClass = new Class(name);
        model.addClass(newClass);
        return newClass;
    }

    static Attribute createAttribute(Class toClass, String attName, PrimitiveType type, boolean isId) {
        if(toClass.containProperty(attName)) {
            throw new RuntimeException("A property with name " + attName + " already exists. Property names should be unique.");
        }

        Attribute attribute = new Attribute(attName, type, isId);
        toClass.addProperty(attribute);
        return attribute;
    }

    public static Relation getOrCreateRelation(Class toClass, String relName, Class type, boolean isContained) {
        Property property = toClass.getProperty(relName);
        if(property == null) {
            property = new Relation(relName, type, isContained);
            toClass.addProperty(property);
        } else if(!(property instanceof Relation)) {
            throw new RuntimeException("A property with name " + relName + " already exists. Property names should be unique.");
        }

        Relation casted = (Relation) property;

        if(!casted.getType().getName().equals(type.getName())) {
            throw new RuntimeException("Consistency error in the opposite definition for the relation " + toClass.getName() + "." + relName + ".");
        }

       return casted;
    }

    static Multiplicity createMultiplicity(String lower, String upper) {
        int lowerBound, upperBound;
        if(lower.equals("*")) {
            lowerBound = Integer.MAX_VALUE;
        } else {
            try {
                lowerBound = Integer.parseInt(lower);
            } catch (NumberFormatException e) {
                throw new RuntimeException(lower + " is not a correct format for the lower bound.");
            }
        }

        if(upper.equals("*")) {
            upperBound = Integer.MAX_VALUE;
        } else {
            try {
                upperBound = Integer.parseInt(upper);
            } catch (NumberFormatException e) {
                throw new RuntimeException(lower + " is not a correct format for the lower bound.");
            }
        }

        if(lowerBound > upperBound) {
            throw new RuntimeException("Right side of multiplicity should be superior to the left side. Here: " + lower + ">" + upper);
        }

        return new Multiplicity(lowerBound, upperBound);
    }


}
