import java.io.*;

public class MainPhase3 {
    public static void dfs(Node v){
        System.out.println(v.getLeftHand().toString() + " " + v.getProductionRule().toString());
        for (Node node: v.getChildren())
            dfs(node);
    }

    public static void main(String[] args) throws Exception {
        String inputFileName = "t01.in";
        //String outputFileName = null;

/*        if (args != null)
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-i"))
                    inputFileName = args[i + 1];
                if (args[i].equals("-o"))
                    outputFileName = args[i + 1];
            }
*/
        Reader reader;
        //Writer writer;

        if (inputFileName != null)
            reader = new FileReader("tests/" + inputFileName);
        else
            reader = new FileReader("tests/t02.in");

        /*if (outputFileName != null)
            writer = new FileWriter("output/" + outputFileName);
        else
            writer = new FileWriter("output/t08.out");
*/

        parser p = new parser(new Scanner(reader));
        p.parse();
        System.out.println(parser.root.getChildren().size());
        dfs(parser.root);
        /* try {
            p.parse();
  //          writer.write("OK");
            System.out.println("OK");
            System.out.println(parser.root.getChildren().size());
        } catch (Exception e) {
            System.out.println("Syntax Error");
    //        writer.write("Syntax Error");
        }*/
        //writer.flush();
        // writer.close();
    }
}