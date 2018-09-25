package duc.ugcm.parser;

import duc.ugcm.UGCMLexer;
import duc.ugcm.UGCMParser;
import duc.ugcm.ast.*;
import duc.ugcm.ast.Class;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Parser {

    public static Model parse(CharStream input) {
        BufferedTokenStream tokens = new CommonTokenStream(new UGCMLexer(input));
        UGCMParser parser = new UGCMParser(tokens);
        UGCMParser.ModelDclContext modelDclCtx = parser.modelDcl();

        final Model model = new Model();

        for(UGCMParser.ClassDclContext classDclContext: modelDclCtx.classDcl()) {
            String className = classDclContext.name.getText();
            Class newClass = ParserHelper.getOrCreateClass(model, className);

            if(classDclContext.parent != null) {
                String parentName = classDclContext.parent.IDENT().getText();
                final Class parent = ParserHelper.getOrCreateClass(model, parentName);
                newClass.setParent(parent);
            }

            for (UGCMParser.AttributeDclContext attDclCtx: classDclContext.attributeDcl()) {
                boolean isId = attDclCtx.start.getText().equals("id");
                String attName = attDclCtx.name.getText();
                PrimitiveType type = PrimitiveTypeHelper.getType(attDclCtx.type.getText());

                ParserHelper.createAttribute(newClass, attName, type, isId);
            }

            for (UGCMParser.RelationDclContext relDclCtx: classDclContext.relationDcl()) {
                boolean isContained = relDclCtx.start.getText().equals("contained");
                String relName = relDclCtx.name.getText();
                Class type = ParserHelper.getOrCreateClass(model, relDclCtx.type.getText());

                Relation relation = ParserHelper.getOrCreateRelation(newClass, relName, type, isContained);

                if(relDclCtx.multiplicity != null) {
                    UGCMParser.MultiplicityDclContext multDclCtxt = relDclCtx.multiplicity;
                    Multiplicity multiplicity = ParserHelper.createMultiplicity(multDclCtxt.lower.getText(), multDclCtxt.upper.getText());
                    relation.setMultiplicity(multiplicity);
                }

                if(relDclCtx.opposite != null) {
                    String oppositeName = relDclCtx.opposite.opp.getText();
                    Relation oppositeProp = ParserHelper.getOrCreateRelation(type, oppositeName, newClass, false);
                    relation.setOpposite(oppositeProp);
                }
            }

        }




        return model;
    }
}
