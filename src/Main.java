import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFileName = null;
        String outputFileName = null;

        if (args != null)
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-i"))
                    inputFileName = args[i + 1];
                if (args[i].equals("-o"))
                    outputFileName = args[i + 1];
            }

        Reader reader;
        Writer writer;

        if (inputFileName != null)
            reader = new FileReader("tests/" + inputFileName);
        else
            reader = new FileReader("tests/t02.in");

        if (outputFileName != null)
            writer = new FileWriter("output/" + outputFileName);
        else
            writer = new FileWriter("output/t01.out");


        parser p = new parser(new Scanner(reader));
        try {
            p.parse();
            writer.write("OK");
        } catch (Exception e) {
            writer.write("Syntax Error");
        }
        writer.flush();
        writer.close();
    }
}