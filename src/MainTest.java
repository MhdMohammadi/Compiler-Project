import Controller.Compiler;
import Model.Type;
import Parser.parser;
import Scanner.MyScanner;

import java.io.FileReader;
import java.io.Reader;

public class MainTest {

    public static void main(String[] args) throws Exception {
        String inputFileName = "t01.in";
        String outputFileName = "t01.s";
//        String outputFileName = null;

/*        if (args != null)
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-i"))
                    inputFileName = args[i + 1];
                if (args[i].equals("-o"))
                    outputFileName = args[i + 1];
            }
*/
//        Writer writer;
        Compiler compiler = new Compiler();
        compiler.compile(inputFileName, outputFileName);
        /*if (outputFileName != null)
            writer = new FileWriter("output/" + outputFileName);
        else
            writer = new FileWriter("output/t08.out");
*/

//        c.debug(c.getRoot());

//        c.setClazzType();
//        c.setAllClazzAttributesAndFunctions();
//        for (Clazz clazz : Clazz.getClazzes()){
//            System.out.println("class name:");;
//            System.out.println(clazz.getName() + " " + clazz.getType().getName());
//            if(clazz.getParent() != null) System.out.println(clazz.getParent().getName());
//            System.out.println("functions:");
//            for (Function function : clazz.getFunctions()){
//                System.out.println(function.getName() + " " + function.getAccessMode() + " " + function.getType().getName());
//            }
//            System.out.println("variables");
//            for (Model.Variable variable : clazz.getVariables()){
//                System.out.println(variable.getName() + " " + variable.getAccessMode() + " " + variable.getType().getName());
//            }
//        }
//        System.out.println("global functions:");
//        for (Function function : c.getRoot().getDefinedFunctions()){
//            System.out.println(function.getName() + " " + function.getAccessMode() + " " + function.getType().getName());
//        }
//        System.out.println("global variables");
//        for (Model.Variable variable : c.getRoot().getDefinedVariables()){
//            System.out.println(variable.getName() + " " + variable.getAccessMode() + " " + variable.getType().getName());
//        }
//        c.areAllVariablesUnique(c.getRoot());
//        c.setFunctionType(c.getRoot());
//        for (Function function: c.getRoot().getDefinedFunctions()){
//            System.out.println(function.getName() + " " + function.getType().getName() + " " + function.getAccessMode().toString());
//        }
//        c.areAllVariablesUnique(c.getRoot());
//        c.setAllNodesType(c.getRoot());
        /* try {
            p.parse();
  //          writer.write("OK");
            System.out.println("OK");
            System.out.println(Parser.parser.root.getChildren().size());
        } catch (Exception e) {
            System.out.println("Syntax Error");
    //        writer.write("Syntax Error");
        }*/
        //writer.flush();
        // writer.close();
    }
}