import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
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
            Reader read = null;
            Writer writer;
            if (inputFileName != null) {
                read = new FileReader("../tests/" + inputFileName);
            }
            Writer writer = null;
            if (outputFileName != null) {
                writer = new FileWriter("../out/" + outputFileName);
            } else {
                writer = new OutputStreamWriter(System.out);
            }
            Scanner scanner = new Scanner(read);
            while ( !scanner.zzAtEOF )
                scanner.yylex();
            writer.flush();

            writer.close();
        } catch (Exception e) {
            Writer writer;
            String outputFile = null;
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-o")) {
                        outputFile = args[i + 1];
                    }
                }
            }
            if (outputFile != null) {
                writer = new FileWriter("out/" + outputFile);
            } else {
                writer = new OutputStreamWriter(System.out);
            }

            writer.write("NO");
            writer.flush();
            writer.close();
        }
    }
}
