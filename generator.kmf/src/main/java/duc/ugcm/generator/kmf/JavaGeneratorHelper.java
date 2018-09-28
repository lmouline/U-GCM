package duc.ugcm.generator.kmf;

import duc.ugcm.ast.Relation;

public class JavaGeneratorHelper {
    public String upperFirst(String aString) {
        if(aString == null || aString.length() == 0) {
            return aString;
        }

        return Character.toUpperCase(aString.charAt(0)) + aString.substring(1);
    }

    /*
        0 -> package name
        1 -> metamodel name
     */
    public String[] metamodelInfo(String metaModelName) {
        int lastDot = metaModelName.lastIndexOf('.');
        if(lastDot == -1) {
            return new String[]{metaModelName.toLowerCase(), upperFirst(metaModelName)};
        }

        return new String[]{metaModelName.substring(0,lastDot), upperFirst(metaModelName.substring(lastDot + 1))};
    }

    public boolean haveOposite(Relation relation) {
        return relation.getOppositeOf() != null;
    }
}
