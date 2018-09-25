package duc.ugcm;

import duc.ugcm.ast.*;
import duc.ugcm.ast.Class;
import duc.ugcm.parser.Parser;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class TestParser {

    @Test
    public void testEmptyModel() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("emptyModel.ugcm");

        Assertions.assertDoesNotThrow(() -> {
            Model model = Parser.parse(CharStreams.fromStream(in));
            Assertions.assertNotNull(model);
            Assertions.assertEquals(0, model.getClasses().size());
        });
    }

    @Test
    public void testModelWithClasses() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("modelWithClasses.ugcm");

        Assertions.assertDoesNotThrow(() -> {
            Model model = Parser.parse(CharStreams.fromStream(in));
            Assertions.assertEquals(4, model.getClasses().size());
            Assertions.assertTrue(model.containClass("A"));
            Assertions.assertTrue(model.containClass("B"));
            Assertions.assertTrue(model.containClass("C"));
            Assertions.assertTrue(model.containClass("D"));
        });
    }

    @Test
    public void testClassInheritance() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("inheritance.ugcm");

        Assertions.assertDoesNotThrow(() -> {
            Model model = Parser.parse(CharStreams.fromStream(in));
            Assertions.assertEquals(2, model.getClasses().size());
            Assertions.assertTrue(model.containClass("A"));
            Assertions.assertTrue(model.containClass("B"));

            Class parentB = model.getClass("B").getParent();
            Assertions.assertNotNull(parentB);
            Assertions.assertEquals("A",parentB.getName());
        });
    }

    @Test
    public void testAttributes() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("attributes.ugcm");
        Model model = Parser.parse(CharStreams.fromStream(in));

        Class theClass = model.getClass("A");
        Assertions.assertNotNull(theClass);

        Assertions.assertEquals(9, theClass.getProperties().size());

        Property prop = theClass.getProperty("att1");
        Assertions.assertTrue(prop instanceof Attribute);
        Attribute att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Short, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att2");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Int, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att3");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Long, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att4");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Bool, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att5");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Float, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att6");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Double, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("att7");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.Char, att.getType());
        Assertions.assertFalse(att.isId());

        prop = theClass.getProperty("myAttId");
        Assertions.assertTrue(prop instanceof Attribute);
        att = (Attribute) prop;
        Assertions.assertEquals(PrimitiveType.String, att.getType());
        Assertions.assertTrue(att.isId());

    }

    @Test
    public void testRelation() throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("relation.ugcm");
        Model model = Parser.parse(CharStreams.fromStream(in));

        Class classA = model.getClass("A");
        Class classB = model.getClass("B");
        Assertions.assertNotNull(classA);
        Assertions.assertNotNull(classB);

        Property prop = classA.getProperty("b1s");
        Assertions.assertTrue(prop instanceof Relation);
        Relation relation = (Relation) prop;
        Assertions.assertEquals(classB.getName(), relation.getType().getName());
        Assertions.assertTrue(relation.isContained());
        Multiplicity multiplicity = relation.getMultiplicity();
        Assertions.assertNull(multiplicity);
        Relation opposite = relation.getOppositeOf();
        Assertions.assertNull(opposite);

        prop = classA.getProperty("b2");
        Assertions.assertTrue(prop instanceof Relation);
        relation = (Relation) prop;
        Assertions.assertEquals(classB.getName(), relation.getType().getName());
        Assertions.assertFalse(relation.isContained());
        multiplicity = relation.getMultiplicity();
        Assertions.assertNotNull(multiplicity);
        Assertions.assertEquals(0, multiplicity.getLowerBound());
        Assertions.assertEquals(1, multiplicity.getUpperBound());
        opposite = relation.getOppositeOf();
        Assertions.assertNull(opposite);


        prop = classA.getProperty("b3s");
        Assertions.assertTrue(prop instanceof Relation);
        relation = (Relation) prop;
        Assertions.assertEquals(classB.getName(), relation.getType().getName());
        Assertions.assertTrue(relation.isContained());
        multiplicity = relation.getMultiplicity();
        Assertions.assertNotNull(multiplicity);
        Assertions.assertEquals(0, multiplicity.getLowerBound());
        Assertions.assertEquals(Integer.MAX_VALUE, multiplicity.getUpperBound());
        opposite = relation.getOppositeOf();

        Property B_a = classB.getProperty("a");
        Assertions.assertTrue(B_a instanceof Relation);
        Relation rel_B_a = (Relation) B_a;
        Assertions.assertEquals(classA.getName(), rel_B_a.getType().getName());
        Assertions.assertFalse(rel_B_a.isContained());
        multiplicity = rel_B_a.getMultiplicity();
        Assertions.assertNotNull(multiplicity);
        Assertions.assertEquals(0, multiplicity.getLowerBound());
        Assertions.assertEquals(1, multiplicity.getUpperBound());
        Relation opposite_B_a = rel_B_a.getOppositeOf();

        Assertions.assertEquals(B_a.getName(), opposite.getName());
        Assertions.assertEquals(relation.getName(), opposite_B_a.getName());


    }
}