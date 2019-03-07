import java.util.Random;

public class Main {
    public static String[] navn = {"Per", "Pål", "Narren", "Kongen", "Prinsessa", "Espen"};
    public static Random r = new Random(42);
    public static KontaktNett k = new KontaktNett();
    public static int grense;

    public static void main(String[] args) {
        genererSamtaler(k, 10);

        grense = 10;

        /*
        Kongen + prinsessen har tett kontakt
        Espen + prinsessen har tett kontakt
        Kongen + Espen har indrekte tett kontakt
         */
        for (int i = 0; i < grense + 1; i++) {
            k.registrerSamtale(navn[4], navn[5]);
            k.registrerSamtale(navn[5], navn[4]);
            k.registrerSamtale(navn[4], navn[3]);
            k.registrerSamtale(navn[3], navn[4]);
        }


        for (Samtale s: k.samtaler.keySet()) {
            if (s.toString().contains("Narren"))
            System.out.println(s.toString() + " - " + k.samtaler.get(s));
        }

        System.out.println("Samtaler: " + k.samtaler.size());

        System.out.println("\nDIREKTE KONTAKTER");
        for (String person: k.personer) {
            System.out.println("\n" + person + ":");
            System.out.println(k.finnDirekteKontakter(person, grense));
        }

        System.out.println("\nINDIREKTE KONTAKTER");
        for (String person: k.personer) {
            System.out.println("\n" + person + ":");
            System.out.println(k.finnAlleKontakter(person, grense));
        }



    }

    public static void printTestData(Samtale s) {
        System.out.println(s.toString() + ": over " + grense + " - " + k.harTettKontakt(s.getSender(), s.getMottaker(), grense));
    }

    public static void genererSamtaler(KontaktNett k, int antall) {
        for (int i = 0; i < antall; i++) {
            Samtale s = genererSamtale();
            String sender = s.getSender();
            String mottaker = s.getMottaker();
            k.registrerSamtale(sender, mottaker);
        }
    }


    public static Samtale genererSamtale() {
        String sender = genererNavn(null);
        String mottaker = genererNavn(sender);
        return new Samtale(sender, mottaker);
    }

    /**
     *
     * @param ikkeDetteNavnet forhindrer at en person snakker med seg selv
     * @return det genererte navnet
     */
    public static String genererNavn(String ikkeDetteNavnet) {
        String ettNavn = navn[r.nextInt(navn.length)];
        return (ettNavn.equals(ikkeDetteNavnet)) ? genererNavn(ikkeDetteNavnet) : ettNavn;
    }
}
