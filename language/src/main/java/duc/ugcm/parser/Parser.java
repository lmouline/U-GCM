package duc.ugcm.parser;

import duc.ugcm.UGCMLexer;
import duc.ugcm.UGCMParser;
import duc.ugcm.ast.*;
import duc.ugcm.ast.Class;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Parser {
    private static final int INIT_IDX_PROP = 4;
    private int idxClasses = 0;
    private int idxProperties = INIT_IDX_PROP;

    public Model parse(CharStream input) {
        BufferedTokenStream tokens = new CommonTokenStream(new UGCMLexer(input));
        UGCMParser parser = new UGCMParser(tokens);
        UGCMParser.ModelDclContext modelDclCtx = parser.modelDcl();

        final Model model = new Model();

        for(UGCMParser.ClassDclContext classDclContext: modelDclCtx.classDcl()) {
            String className = classDclContext.name.getText();
            Class newClass = getOrCreateClass(model, className);

            if(classDclContext.parent != null) {
                for (int i = 0; i < classDclContext.parent.fullIdentDcl().size(); i++) {
                    UGCMParser.FullIdentDclContext current = classDclContext.parent.fullIdentDcl(i);
                    String parentName = current.getText();
                    final Class parent = getOrCreateClass(model, parentName);
                    newClass.addParent(parent);
                }
            }

            idxProperties = INIT_IDX_PROP;
            for (UGCMParser.AttributeDclContext attDclCtx: classDclContext.attributeDcl()) {
                boolean isId = attDclCtx.start.getText().equals("id");
                String attName = attDclCtx.name.getText();
                PrimitiveType type = PrimitiveTypeHelper.getType(attDclCtx.type.getText());

                createAttribute(newClass, attName, type, isId);

            }

            for (UGCMParser.RelationDclContext relDclCtx: classDclContext.relationDcl()) {
                boolean isContained = relDclCtx.start.getText().equals("contained");
                String relName = relDclCtx.name.getText();
                Class type = getOrCreateClass(model, relDclCtx.type.getText());

                Relation relation = getOrCreateRelation(newClass, relName, type, isContained, true);

                if(relDclCtx.multiplicity != null) {
                    UGCMParser.MultiplicityDclContext multDclCtxt = relDclCtx.multiplicity;
                    Multiplicity multiplicity = createMultiplicity(multDclCtxt.lower.getText(), multDclCtxt.upper.getText());
                    relation.setMultiplicity(multiplicity);
                }

                if(relDclCtx.opposite != null) {
                    String oppositeName = relDclCtx.opposite.opp.getText();
                    Relation oppositeProp = getOrCreateRelation(type, oppositeName, newClass, false, false);
                    relation.setOpposite(oppositeProp);
                }
            }

        }
        return model;
    }

    private Class getOrCreateClass(Model model, String name) {
        if(model.containClass(name)) {
            return model.getClass(name);
        }

        int lastDot = name.lastIndexOf('.');

        String packName, className;
        if(lastDot != -1) {
            packName = name.substring(0, lastDot);
            className = name.substring(lastDot + 1);
        } else {
            packName = null;
            className = name;
        }

        Class newClass = new Class(packName, className, idxClasses);
        idxClasses++;
        model.addClass(newClass);
        return newClass;
    }

    private Attribute createAttribute(Class toClass, String attName, PrimitiveType type, boolean isId) {
        if(toClass.containProperty(attName)) {
            throw new RuntimeException("A property with name " + attName + " already exists. Property names should be unique.");
        }

        Attribute attribute = new Attribute(attName, type, isId, idxProperties);
        idxProperties++;
        toClass.addProperty(attribute);
        return attribute;
    }

    private Relation getOrCreateRelation(Class toClass, String relName, Class type, boolean isContained, boolean updateIdx) {
        Property property = toClass.getProperty(relName);
        if(property == null) {
            property = new Relation(relName, type, isContained);
            toClass.addProperty(property);
        } else if(!(property instanceof Relation)) {
            throw new RuntimeException("A property with name " + relName + " already exists. Property names should be unique.");
        }

        Relation casted = (Relation) property;
        if(updateIdx) {
            casted.setIndex(idxProperties);
            idxProperties++;
        }

        if(!casted.getType().getName().equals(type.getName())) {
            throw new RuntimeException("Consistency error in the opposite definition for the relation " + toClass.getName() + "." + relName + ".");
        }

        return casted;
    }

    private Multiplicity createMultiplicity(String lower, String upper) {
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
