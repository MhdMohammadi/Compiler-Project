import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String string;
        Scanner scanner = new Scanner(System.in);
        string = scanner.nextLine();
        if(string.matches("[a-z]+"))
            System.out.println("yes babe");
        else
            System.out.println("ah shit");
    }

}
