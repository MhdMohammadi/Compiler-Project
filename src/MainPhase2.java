import java.io.*;

public class MainPhase2 {
    public static void main(String[] args) throws IOException {
        String inputFileName = "t01.in";
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
            writer = new FileWriter("output/t08.out");


        parser p = new parser(new Scanner(reader));
        try {
            p.parse();
            writer.write("OK");
      //      System.out.println(parser.root.getChildren().size());
        } catch (Exception e) {
           // System.out.println();
             writer.write("Syntax Error");
        }
        writer.flush();
        writer.close();
    }
}