import java.util.Random;

public class Main {
    private static String[] navn = {"Per", "Pål", "Narren", "Kongen", "Prinsessa", "Espen"};
    private static Random r = new Random(42);
    private static KontaktNett k = new KontaktNett();
    private static int grense;

    public static void main(String[] args) {
        //lager noen tilfeldige samtaler
        genererSamtaler(10);

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

        System.out.println("\nSamtaler: " + k.samtaler.size());

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

    /**
     * Ikke i bruk for øyeblikket
     * @param s samtalen som data blir printet for
     */
    public static void printTestData(Samtale s) {
        System.out.println(s.toString() + ": over " + grense + " - " + k.harTettKontakt(s.getSender(), s.getMottaker(), grense));
    }

    /**
     * Genererer et antall samtaler med 'genererSamtale'-metoden
     * @param antall hvor mange samtaler som blir laget
     */
    public static void genererSamtaler(int antall) {
        for (int i = 0; i < antall; i++) {
            Samtale s = genererSamtale();
            String sender = s.getSender();
            String mottaker = s.getMottaker();
            k.registrerSamtale(sender, mottaker);
        }
    }


    /**
     * Genererer en samtale mellom to tilfeldige personer, som ikke er samme person
     * @return genererte samtalen
     */
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
