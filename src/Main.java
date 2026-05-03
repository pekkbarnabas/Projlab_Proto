import java.util.Scanner;

/**
 * A szimulációs program belépési pontja.
 * Felelős a szabványos bemenet (standard input) olvasásáért és a beolvasott
 * sorok (parancsok) továbbításáért a fő vezérlő (Vezerlo) osztály felé.
 */
public class Main {
    /**
     * A program fő metódusa. Inicializálja a vezérlőt és elindítja a parancsfeldolgozó ciklust.
     */
    public static void main(String[] args) {
        // Inicializáljuk a rendszer lelkét, a Vezérlőt
        Vezerlo vezerlo = new Vezerlo();
        Scanner scanner = new Scanner(System.in);

        // A futás addig tart, amíg a Vezerlo 'fut' változója igaz (vagy amíg van bemenet)
        while (vezerlo.isFut() && scanner.hasNextLine()) {
            String sor = scanner.nextLine();
            
            // Üres sorokat és kommenteket átugorjuk
            if (sor.trim().isEmpty() || sor.startsWith("//")) {
                continue;
            }
            
            // Továbbítjuk az érvényes parancsot a Vezérlőnek feldolgozásra
            vezerlo.parancsFeldolgoz(sor);
        }
        
        // Erőforrások felszabadítása a futás végén
        scanner.close();
    }
}