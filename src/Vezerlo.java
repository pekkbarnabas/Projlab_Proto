import java.io.*;
import java.util.*;

public class Vezerlo {
    private Map<String, Csomopont> csomopontok = new HashMap<>();
    private Map<String, Utszakasz> utszakaszok = new HashMap<>();
    private Map<String, Sav> savok = new HashMap<>();
    private Map<String, Jarmu> jarmuvek = new HashMap<>();
    
    private Takarito takarito;
    private Buszvezeto buszvezeto;
    private Idojaras idojaras;
    private Bolt bolt;
    private List<IIdoMulo> idomulok = new ArrayList<>();
    
    private boolean fut;
    private boolean isRandom;
    private boolean autoTick;

    public Vezerlo() {
        init();
    }

    private void init() {
        csomopontok.clear();
        utszakaszok.clear();
        savok.clear();
        jarmuvek.clear();
        idomulok.clear();
        
        takarito = new Takarito();
        takarito.setRaktar(new Raktar());
        bolt = new Bolt();
        takarito.setBolt(bolt);
        
        buszvezeto = new Buszvezeto();
        
        idojaras = new Idojaras(0);
        idomulok.add(idojaras);
        
        fut = true;
        isRandom = true;
        autoTick = false;
    }

    public boolean isFut() {
        return fut;
    }

    public void parancsFeldolgoz(String parancs) {
        if (parancs == null || parancs.trim().isEmpty()) return;
        
        String[] szavak = parancs.trim().split("\\s+");
        if (szavak.length == 0) return;

        String parancsSzo = szavak[0].toLowerCase();
        boolean hibaVolt = false;

        try {
            switch (parancsSzo) {
                case "create":
                    if (szavak.length < 3) throw new Exception("Kevés paraméter! (create <típus> <név> ...)");
                    feldolgozCreate(szavak);
                    break;
                case "link":
                    if (szavak.length < 4) throw new Exception("Kevés paraméter! (link sav <s1> <s2>)");
                    feldolgozLink(szavak);
                    break;
                case "set":
                    if (szavak.length < 4) throw new Exception("Kevés paraméter a set parancshoz!");
                    feldolgozSet(szavak);
                    break;
                case "add":
                    if (szavak.length < 3) throw new Exception("Kevés paraméter az add parancshoz!");
                    feldolgozAdd(szavak);
                    break;
                case "buy":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (buy <árucikk>)");
                    Arucikk targy = Arucikk.valueOf(szavak[1].toUpperCase());
                    if (takarito != null) {
                        boolean siker = takarito.vasarol(targy);
                        if (!siker) {
                            hibaVolt = true;
                        }
                    }
                    break;
                case "equip":
                    if (szavak.length < 3) throw new Exception("Kevés paraméter! (equip <hókotró> <fej>)");
                    Hokotro h = (Hokotro) jarmuvek.get(szavak[1]);
                    if (h == null) throw new Exception("Nem létező hókotró!");
                    
                    if (szavak[2].equalsIgnoreCase("nincs")) {
                        takarito.eszkozkiosztas(h, null);
                    } else {
                        IKotrofej fej = letrehozFej(szavak[2]);
                        takarito.eszkozkiosztas(h, fej);
                    }
                    break;
                case "work":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (work <hókotró>)");
                    Hokotro dolgozo = (Hokotro) jarmuvek.get(szavak[1]);
                    
                    if (dolgozo != null) {
                        boolean siker = dolgozo.takarit();
                        if (!siker) {
                            hibaVolt = true; // Megakadályozzuk a "> OK" kiírását!
                        }
                    } else {
                        throw new Exception("Nem létező hókotró!");
                    }
                    break;
                case "move":
                    if (szavak.length < 3) throw new Exception("Kevés paraméter! (move <jármű> <célsáv>)");
                    Jarmu j = jarmuvek.get(szavak[1]);
                    Sav celSav = savok.get(szavak[2]);
                    if (j == null || celSav == null) throw new Exception("Nem létező jármű vagy sáv!");
                    boolean sikeresLepes = true;
                    if (j instanceof Busz) {
                        sikeresLepes = ((Busz) j).lep(celSav);
                    } else if (j instanceof Hokotro) {
                        sikeresLepes = ((Hokotro) j).lep(celSav);
                    } else if (j instanceof Auto) {
                        ((Auto) j).savotValt(celSav); 
                        sikeresLepes = true;
                    }
                    if (!sikeresLepes) {
                        hibaVolt = true; 
                    }
                    break;
                case "tick":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (tick <szám/auto>)");
                    if (szavak[1].equalsIgnoreCase("auto")) {
                        autoTick = true;
                        autoTickFuttatas();
                    } else {
                        int n = Integer.parseInt(szavak[1]);
                        for (int i = 0; i < n; i++) tick();
                    }
                    if (idojaras != null) {
                        idojaras.idotLep();
                    }
                    break;
                case "random":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (random on/off)");
                    isRandom = szavak[1].equalsIgnoreCase("on");
                    break;
                case "save":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (save <fájlnév>)");
                    mentes(szavak[1]);
                    break;
                case "load":
                    if (szavak.length < 2) throw new Exception("Kevés paraméter! (load <fájlnév>)");
                    beolvas(szavak[1]);
                    break;
                case "loadall":
                    loadAllTesztek();
                    break;
                case "stat":
                    if (szavak.length < 3) throw new Exception("Kevés paraméter! (stat <típus> <név>)");
                    stat(szavak[1], szavak[2]);
                    break;
                case "statall":
                    statAll();
                    break;
                case "help":
                    helpMenu();
                    break;
                case "reset":
                    init();
                    break;
                case "exit":
                    autoTick = false;
                    fut = false;
                    break;
                default:
                    System.out.println("ERROR: Ismeretlen parancs");
                    hibaVolt = true;
            }
        } catch (Exception hiba) {
            System.out.println("ERROR: " + hiba.getMessage());
            hibaVolt = true;
        }

        if (!hibaVolt && !parancsSzo.equals("save") && !parancsSzo.equals("load") && !parancsSzo.equals("reset") && !parancsSzo.equals("stat") && !parancsSzo.equals("statall") && !parancsSzo.equals("help")) {
            System.out.println("> OK");
        }
    }

    private void tick() {
        for (IIdoMulo i : idomulok) {
            i.idotLep();
        }
    }

    private void autoTickFuttatas() {
        System.out.println("Automatikus tickeles inditva. Leallitashoz Ctrl+C (vagy irj be egy parancsot a kovetkezo ciklus elott).");
        new Thread(() -> {
            while (autoTick && fut) {
                try {
                    tick();
                    // Varakozo ido a tickek kozott (pl. 1 masodperc)
                    Thread.sleep(1000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }


    // --- Részletes parancsfeldolgozó segédmetódusok ---

    private void feldolgozCreate(String[] szavak) throws Exception {
        String tipus = szavak[1].toLowerCase();
        String nev = szavak[2];

        if (tipus.equals("csomopont")) {
            csomopontok.put(nev, new Csomopont(nev));
        } else if (tipus.equals("utszakasz")) {
            if (szavak.length < 7) throw new Exception("Kevés paraméter! (create utszakasz <név> <c1> <c2> <hossz> <magasság>)");
            Csomopont c1 = csomopontok.get(szavak[3]);
            Csomopont c2 = csomopontok.get(szavak[4]);
            if (c1 == null || c2 == null) throw new Exception("Nem létező csomópont!");
            int hossz = Integer.parseInt(szavak[5]);
            int magassag = Integer.parseInt(szavak[6]);
            
            Utszakasz u = new Utszakasz(c1, c2, hossz, magassag);
            utszakaszok.put(nev, u);
            c1.addKijarat(u);
            c2.addKijarat(u);
        } else if (tipus.equals("sav")) {
            if (szavak.length < 4) throw new Exception("Kevés paraméter! (create sav <név> <útszakasz>)");
            Utszakasz u = utszakaszok.get(szavak[3]);
            if (u == null) throw new Exception("Nem létező útszakasz!");
            Sav s = new Sav();
            savok.put(nev, s);
            u.addSav(s);
            idomulok.add(s);
        } else if (tipus.equals("jarmu")) {
            if (szavak.length < 5) throw new Exception("Kevés paraméter! (create jarmu <típus> <név> <sáv>)");
            String jarmuTipus = szavak[2].toLowerCase();
            String jNev = szavak[3];
            Sav s = savok.get(szavak[4]);
            
            if (s == null) throw new Exception("A megadott sav nem letezik");

            Jarmu jarmu = null;
            if (jarmuTipus.equals("auto")) {
                if (csomopontok.isEmpty()) throw new Exception("Nincs csomópont a térképen az autóhoz!");
                Csomopont l = csomopontok.values().iterator().next(); 
                jarmu = new Auto(l, l, isRandom);
            } else if (jarmuTipus.equals("hokotro")) {
                jarmu = new Hokotro(takarito);
                takarito.addHokotro((Hokotro) jarmu);
            } else if (jarmuTipus.equals("busz")) {
                if (csomopontok.isEmpty()) throw new Exception("Nincsenek végállomások a buszhoz!");
                List<Csomopont> vegallomasok = new ArrayList<>(csomopontok.values());
                jarmu = new Busz(vegallomasok);
                buszvezeto.busztHozzaad((Busz) jarmu);
            }
            
            if (jarmu != null) {
                jarmuvek.put(jNev, jarmu);
                idomulok.add(jarmu);
                s.elfogad(jarmu);
                jarmu.setAktualisSav(s);
            } else {
                throw new Exception("Ismeretlen járműtípus!");
            }
        } else {
            throw new Exception("Ismeretlen entitás típus!");
        }
    }

    private void feldolgozLink(String[] szavak) {
        if (szavak[1].equals("sav")) {
            Sav s1 = savok.get(szavak[2]);
            Sav s2 = savok.get(szavak[3]);
            s1.getSzomszedok().add(s2);
            s2.getSzomszedok().add(s1);
        }
    }

    private void feldolgozSet(String[] szavak) throws Exception {
        if (szavak[1].equals("sav")) {
            if (szavak.length < 5) throw new Exception("Kevés paraméter! (set sav <név> <tul> <érték>)");
            Sav s = savok.get(szavak[2]);
            if (s == null) throw new Exception("Nem létező sáv!");
            
            switch (szavak[3].toLowerCase()) {
                case "ho": s.setHovastagsag(Integer.parseInt(szavak[4])); break;
                case "jeg": s.setJegpancel(Boolean.parseBoolean(szavak[4])); break;
                case "zuzalek": s.setZuzalekos(Boolean.parseBoolean(szavak[4])); break;
                case "sozasidozito": s.setSozasIdozito(Integer.parseInt(szavak[4])); break;
                case "athaladas": s.setAthaladasokSzama(Integer.parseInt(szavak[4])); break;
                case "blokkolt": s.setBlokkolt(Boolean.parseBoolean(szavak[4])); break;
                default: throw new Exception("Ismeretlen sáv tulajdonság!");
            }
        } else if (szavak[1].equals("jarmu")) {
            if (szavak.length < 5) throw new Exception("Kevés paraméter! (set jarmu <név> <tul> <érték>)");
            Jarmu j = jarmuvek.get(szavak[2]);
            if (j == null) throw new Exception("Nem létező jármű!");
            
            switch (szavak[3].toLowerCase()) {
                case "pozicio": j.setPozicio(Float.parseFloat(szavak[4])); break;
                case "buntetoido": 
                    if (j instanceof Auto) ((Auto)j).setBuntetoido(Integer.parseInt(szavak[4]));
                    else if (j instanceof Busz) ((Busz)j).setBuntetoido(Integer.parseInt(szavak[4]));
                    break;
                case "elakadt":
                    if (j instanceof Auto) ((Auto)j).setElakadt(Boolean.parseBoolean(szavak[4]));
                    else if (j instanceof Busz) ((Busz)j).setElakadt(Boolean.parseBoolean(szavak[4]));
                    break;
                case "sodrodas":
                    if (j instanceof Auto) ((Auto)j).setTesztSodrodas(Boolean.parseBoolean(szavak[4]));
                    break;
                default: throw new Exception("Ismeretlen jármű tulajdonság!");
            }
        } else if (szavak[1].equals("utvonal")) {
             if (szavak.length < 5) throw new Exception("Kevés paraméter! (set utvonal <név> <c1> <c2>)");
             Jarmu j = jarmuvek.get(szavak[2]);
             Csomopont c1 = csomopontok.get(szavak[3]);
             Csomopont c2 = csomopontok.get(szavak[4]);
             if (j == null || c1 == null || c2 == null) throw new Exception("Hibás entitás nevek!");
             
            if (j instanceof Auto) {
                 ((Auto) j).setUtvonal(c1, c2);
             } else if (j instanceof Busz) {
                 ((Busz) j).setUtvonal(Arrays.asList(c1, c2));
             }

             System.out.println("Utvonal beallitva: " + c1.getNev() + " -> " + c2.getNev());

        } else if (szavak[1].equals("idojaras")) {
            if (szavak[2].equals("ho")) idojaras.setIntenzitas(Integer.parseInt(szavak[3]));
        }
    }

    private void feldolgozAdd(String[] szavak) {
        if (szavak[1].equals("penz")) {
            takarito.setPenz(takarito.getPenz() + Integer.parseInt(szavak[2]));
        } else if (szavak[1].equals("pont")) {
             for(int i=0; i<Integer.parseInt(szavak[2]); i++) buszvezeto.pontotKap();
        } else if (szavak[1].equals("raktar")) {
            Arucikk cikk = Arucikk.valueOf(szavak[2].toUpperCase());
            takarito.getRaktar().eroforrasBovit(cikk, Integer.parseInt(szavak[3]));
        }
    }

    private IKotrofej letrehozFej(String tipus) {
        tipus = tipus.toLowerCase();
        if (tipus.equals("sopro") || tipus.equals("soprofej")) return new SoproFej();
        if (tipus.equals("sarkany") || tipus.equals("sarkanyfej")) return new SarkanyFej();
        if (tipus.equals("koszoro") || tipus.equals("koszorofej")) return new KoszoroFej();
        if (tipus.equals("jegtoro") || tipus.equals("jegtorofej")) return new JegtoroFej();
        if (tipus.equals("soszoro") || tipus.equals("soszorofej")) return new SoszoroFej();
        if (tipus.equals("hanyo") || tipus.equals("hanyofej")) return new HanyoFej();
        return null;
    }

    private void mentes(String fajlnev) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(fajlnev))) {
            if (!isRandom) {
                out.println("random off");
            }
            for (String nev : csomopontok.keySet()) out.println("create csomopont " + nev);
            for (Map.Entry<String, Utszakasz> e : utszakaszok.entrySet()) {
                Utszakasz u = e.getValue();
                out.println("create utszakasz " + e.getKey() + " " + u.getKezdopont().getNev() + " " + u.getVegpont().getNev() + " " + u.getHossz() + " " + u.getMagassag());
            }
            for (Map.Entry<String, Sav> e : savok.entrySet()) {
                out.println("create sav " + e.getKey() + " " + kulcsKeresese(utszakaszok, e.getValue().getUtszakasz()));
            }
            for (Map.Entry<String, Sav> e : savok.entrySet()) {
                Sav s = e.getValue();
                if (s.getHovastagsag() > 0) out.println("set sav " + e.getKey() + " ho " + s.getHovastagsag());
                if (s.isJegpancel()) out.println("set sav " + e.getKey() + " jeg true");
                if (s.isBlokkolt()) out.println("set sav " + e.getKey() + " blokkolt true");
                if (s.isZuzalekos()) out.println("set sav " + e.getKey() + " zuzalek true");
                if (s.getSozasIdozito() > 0) out.println("set sav " + e.getKey() + " sozasidozito " + s.getSozasIdozito());
            }
            Set<String> marLinkelt = new HashSet<>();
            for (Map.Entry<String, Sav> e : savok.entrySet()) {
                for (Sav s2 : e.getValue().getSzomszedok()) {
                    String s2Nev = kulcsKeresese(savok, s2);
                    if (!marLinkelt.contains(e.getKey() + "-" + s2Nev) && !marLinkelt.contains(s2Nev + "-" + e.getKey())) {
                        out.println("link sav " + e.getKey() + " " + s2Nev);
                        marLinkelt.add(e.getKey() + "-" + s2Nev);
                    }
                }
            }
            if (idojaras != null && idojaras.getIntenzitas() > 0) out.println("set idojaras ho " + idojaras.getIntenzitas());
            if (takarito != null && takarito.getPenz() > 0) out.println("add penz " + takarito.getPenz());
            if (buszvezeto != null && buszvezeto.getPontszam() > 0) out.println("add pont " + buszvezeto.getPontszam());
            
            if (takarito != null && takarito.getRaktar() != null) {
                for (Arucikk cikk : Arucikk.values()) {
                    int db = takarito.getRaktar().getKeszlet(cikk); 
                    if (db > 0) {
                        out.println("add raktar " + cikk.name().toLowerCase() + " " + db);
                    }
                }
            }

            for (Map.Entry<String, Jarmu> e : jarmuvek.entrySet()) {
                String tipus = (e.getValue() instanceof Auto) ? "auto" : (e.getValue() instanceof Busz) ? "busz" : "hokotro";
                out.println("create jarmu " + tipus + " " + e.getKey() + " " + kulcsKeresese(savok, e.getValue().getAktualisSav()));
            }
            for (Map.Entry<String, Jarmu> e : jarmuvek.entrySet()) {
                if (e.getValue() instanceof Auto) {
                    Auto a = (Auto) e.getValue();
                    if (a.isElakadt()) out.println("set jarmu " + e.getKey() + " elakadt true");
                    if (a.getBuntetoido() > 0) out.println("set jarmu " + e.getKey() + " buntetoido " + a.getBuntetoido());
                    if (!isRandom) {
                        out.println("set jarmu " + e.getKey() + " sodrodas " + a.isTesztSodrodas());
                    }
                    if (a.getLakas() != null && a.getMunkahely() != null) {
                        out.println("set utvonal " + e.getKey() + " " + a.getLakas().getNev() + " " + a.getMunkahely().getNev());
                    }
                } else if (e.getValue() instanceof Hokotro) {
                    Hokotro h = (Hokotro) e.getValue();
                    if (h.getFej() != null) out.println("equip " + e.getKey() + " " + h.getFej().getClass().getSimpleName().toLowerCase());
                }
            }

        }
    }

    private void beolvas(String fajlnev) throws Exception {
        File f = new File(fajlnev);
        if (!f.exists()) throw new Exception("Fajl nem talalhato");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String sor;
            while ((sor = br.readLine()) != null) parancsFeldolgoz(sor);
        }
    }

    private void loadAllTesztek() {
        File dir = new File("."); // Vagy a "tests" mappa
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null) {
            for (File file : files) {
                System.out.println("Futtatás: " + file.getName());
                try { beolvas(file.getName()); } 
                catch (Exception e) { System.out.println("Hiba: " + e.getMessage()); }
            }
        }
    }
    
    private void stat(String tipus, String nev) {
        System.out.println("--- STAT: " + tipus + " " + nev + " ---");
        if (tipus.equalsIgnoreCase("sav")) {
            Sav s = savok.get(nev);
            if (s != null) System.out.println("Ho: " + s.getHovastagsag() + ", Jeges: " + s.isJegpancel() + ", Blokkolt: " + s.isBlokkolt());
        } else if (tipus.equalsIgnoreCase("jarmu")) {
             Jarmu j = jarmuvek.get(nev);
             if (j != null) System.out.println("Pozicio: " + j.getPozicio() + ", Sav: " + kulcsKeresese(savok, j.getAktualisSav()));
        }
    }
    
    private void statAll() {
        System.out.println("====== Osszes Jarmu ======");
        for (String k : jarmuvek.keySet()) stat("jarmu", k);
        System.out.println("====== Osszes Sav ======");
        for (String k : savok.keySet()) stat("sav", k);
    }

    private void helpMenu() {
        System.out.println("Elerheto parancsok:");
        System.out.println("create csomopont/utszakasz/sav/jarmu");
        System.out.println("link sav, set sav/jarmu/idojaras/utvonal");
        System.out.println("buy, add penz/pont/raktar, equip, work, move");
        System.out.println("tick, save, load, reset, stat, exit");
    }

    private <T> String kulcsKeresese(Map<String, T> map, T ertek) {
        if (ertek == null) return "null";
        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (entry.getValue() == ertek) return entry.getKey();
        }
        return "UNKNOWN";
    }
}