import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KontaktNett {
    // Oppgave 1a - feltdeklarasjonerr
    //public fordi det ble slitsomt med getters
    public HashMap<Samtale, Integer> samtaler;
    public ArrayList<String> personer;


    public KontaktNett() {
        // Oppgave 1b - constructor
        samtaler = new HashMap<>();
        personer = new ArrayList<>();

    }

    /**
     * Registrer en samtale i grafen
     * @param fra - navnet på personen som ringte
     * @param til - navnet på personen han ringte til
     */
    public void registrerSamtale(String fra, String til) {
        // oppgave 1c
        Samtale s = new Samtale(fra, til);

        //plusser på en i vekten til kanten, hvis kanten allerede finnes
        if (this.samtaler.containsKey(s)) {
            int antall = this.samtaler.get(s);
            this.samtaler.put(s, antall + 1);
            System.out.printf("Samtale (%s): %d -> %d.\n", s.toString(), antall, antall + 1);
        }
        //lager en ny kant hvis den ikke eksisterer
        else {
            this.samtaler.put(s, 1);
            System.out.printf("Samtale (%s) opprettet.\n", s.toString());

            //legger til personene til lista
            leggTilPerson(s.getSender());
            leggTilPerson(s.getMottaker());
        }
    }


    /**
     * sjekker om personen tilhører lista, og legger dem til
     * @param person
     */
    public void leggTilPerson(String person) {
        if (!personer.contains(person)) {
            personer.add(person);
            System.out.println("    " + person + " lagt til i nettverket.");
        } else {
            System.out.println("    " + person + " finnes allerede i nettverket.");
        }
    }

    /**
     * Returner en liste over personer som en mistenkt har hatt tett
     * kontakt med.
     * @param mistenkt - navnet på den mistenkte personen
     * @param ant - antall ganger to personer må ringt hverandre
     * for å ha hatt tett kontakt
     */
    public List<String> finnDirekteKontakter(String mistenkt, int ant) {
        // oppgave 2a
        ArrayList<String> direkteKontaker = new ArrayList<>();
        for (String person : personer) {
            if (harTettKontakt(mistenkt, person, ant) && !mistenkt.equals(person)) {
                direkteKontaker.add(person);
                //System.out.printf("Mistenkt %s lagt til i listen til %s\n", personer.get(i), mistenkt);
            }
        }

        return direkteKontaker;
    }

    /**
     * Returnerer true hvis de har snakket med hverandre (begge veier) mer enn x ganger
     * @param mistenkt1 første person
     * @param mistenkt2 andre person
     * @param grense hvor mange ganger de må snakke sammen (x)
     * @return om de har snakket sammen mer enn x ganger
     */
    public boolean harTettKontakt(String mistenkt1, String mistenkt2, int grense) {
        Samtale s1 = new Samtale(mistenkt1, mistenkt2);
        try {
            int antall1 = samtaler.get(s1);

            //kan returnere her hvis antall1 er under grense

            //Samtale s2 = hentMotsattSamtale(s1);
            Samtale s2 = new Samtale(mistenkt2, mistenkt1);
            int antall2 = samtaler.get(s2);

            return antall1 >= grense && antall2 >= grense;
        } catch (NullPointerException ex) {
            //returnerer false siden samtalen ikke eksisterer og kan dermed ikke være over grensen)
            return false;
        }
    }

    /**
     * Returner en liste over personer som en mistenkt har hatt
     * direkte eller indirekte tett kontakt med.
     * @param mistenkt - navnet på den mistenkte personen
     * @param ant - antall ganger to personer må ringt hverandre
     * for å ha hatt tett kontakt
     */
    public List<String> finnAlleKontakter(String mistenkt, int ant) {
        // oppgave 2b
        List<String> direkteKontakter = finnDirekteKontakter(mistenkt, ant);
        ArrayList<String> indirekteKontakter = new ArrayList<>(direkteKontakter);

        //itererer alle personer direkte koblet til mistenkte
        for (String direkteKobling: personer) {
            if (harTettKontakt(mistenkt, direkteKobling, ant)) {


                //alle personer koblet direkte til en direkte kobling
                //aka. inderekte koblet til mistenkt
                for (String indirekteKobling : personer) {

                    //legger til i liste hvis direktekobling og indirektekobling har tett kontakt
                    if (harTettKontakt(direkteKobling, indirekteKobling, ant)) {

                        //sjekker at den ikke er i lista, og ikke er mistenkte selv
                        if (!indirekteKontakter.contains(indirekteKobling) && !mistenkt.equals(indirekteKobling)) {
                            indirekteKontakter.add(indirekteKobling);
                        }
                    }
                }
            }
        }

        return indirekteKontakter;
    }
}