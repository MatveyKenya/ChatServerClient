package chat_client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Client(getName()).start();
    }

    private static String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя для чата (латиница без пробелов):");
        return scanner.nextLine().trim();
    }
}
