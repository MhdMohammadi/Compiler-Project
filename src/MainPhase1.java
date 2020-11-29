import java.io.*;

public class MainPhase1 {
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

            Reader reader = null;
            Writer writer;

            if (inputFileName != null)
                reader = new FileReader("tests/" + inputFileName);
            else
                reader = new FileReader("tests/t08.in");

            if (outputFileName != null)
                writer = new FileWriter("out/" + outputFileName);
            else
                writer = new OutputStreamWriter(System.out);

            Scanner scanner = new Scanner(reader);

            while (true) {
                try {
                    writer.write(scanner.yylex() + '\n');
                } catch (Exception e) {
                    if(e.getMessage().equals("UNDEFINED_TOKEN"))
                        writer.write(e.getMessage() + '\n');
                    break;
                }
            }
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
