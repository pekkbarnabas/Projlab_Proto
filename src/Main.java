import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Vezerlo vezerlo = new Vezerlo();
        Scanner scanner = new Scanner(System.in);

        // A futás addig tart, amíg a Vezerlo 'fut' változója igaz (vagy amíg van bemenet)
        while (vezerlo.isFut() && scanner.hasNextLine()) {
            String sor = scanner.nextLine();
            
            // Üres sorokat és kommenteket átugorjuk
            if (sor.trim().isEmpty() || sor.startsWith("//")) {
                continue;
            }
            
            vezerlo.parancsFeldolgoz(sor);
        }
        
        scanner.close();
    }
}