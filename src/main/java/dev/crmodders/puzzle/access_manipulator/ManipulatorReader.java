package dev.crmodders.puzzle.access_manipulator;

import dev.crmodders.puzzle.access_manipulator.pairs.FieldModifierPair;
import dev.crmodders.puzzle.access_manipulator.pairs.MethodModifierPair;
import dev.crmodders.puzzle.access_manipulator.transformers.AccessManipulatorTransformer;
import dev.crmodders.puzzle.access_manipulator.transformers.ClassModifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Pattern;

public class ManipulatorReader {


    public static void read(String contents) {
        try {
            readManipulator(contents);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void readManipulator(String contents) throws IOException {
        BufferedReader reader =  new BufferedReader(new StringReader(contents));
        String ln;
        while((ln = reader.readLine())!=null){
            if(ln.isBlank() || ln.isEmpty())
                continue;
            List<String> tokens = Arrays.asList(Pattern.compile("[ \\t]+").split(ln));
            ClassModifier modifier;
            var access = tokens.get(0);
            modifier = switch (access) {
                case "public" -> ClassModifier.PUBLIC;
                case "private" -> ClassModifier.PRIVATE;
                case "protected" -> ClassModifier.PROTECTED;
                default -> throw new RuntimeException("Unsupported access: '" + tokens.get(0) + "'");
            };
            var type = tokens.get(1);
            switch (type) {
                case "class":
                    if (tokens.size()==3)
                        AccessManipulatorTransformer.classesToModify.put(tokens.get(2),modifier);
                    else
                        throw new RuntimeException("Layout is invalid for class AM");
                    break;
                case "field":
                    if (tokens.size()==4) {
                        HashMap<String, FieldModifierPair> hm = new HashMap<>();
                        hm.put(tokens.get(3),new FieldModifierPair(tokens.get(3), tokens.get(2), modifier));
                        AccessManipulatorTransformer.fieldsToModify.put(tokens.get(2), hm);
                    }
                    else
                        throw new RuntimeException("Layout is invalid for field AM");
                    break;
                case "method":
                    if(tokens.size()==5){
                        List<MethodModifierPair> p = new ArrayList<>(1);
                        p.add(new MethodModifierPair(tokens.get(3),tokens.get(4),tokens.get(2),modifier ));
                        AccessManipulatorTransformer.methodsToModify.put(tokens.get(2),p);
                    }else {
                        throw new RuntimeException("Layout is invalid for method AM");
                    }
                    break;
                default:
                    throw new RuntimeException("Unsupported type: '" + tokens.get(1) + "'");
            }
        }
    }

}
