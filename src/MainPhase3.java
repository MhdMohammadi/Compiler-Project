import Controller.Compiler;
import Model.Type;
import Parser.parser;
import Scanner.MyScanner;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class MainPhase3 {

    public static void main(String[] args) {
        String inputFileName = null;
        String outputFileName = null;
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-i")) {
                    inputFileName = args[i + 1];
                }
                if (args[i].equals("-o")) {
                    outputFileName = args[i + 1];
                }
            }
        }
        try {
            Compiler compiler = new Compiler();
            compiler.compile( inputFileName, outputFileName );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}