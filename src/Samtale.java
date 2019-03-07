public class Samtale {
    private String sender;
    private String mottaker;

    public Samtale(String sender, String mottaker) {
        this.sender = sender;
        this.mottaker = mottaker;
    }

    public String getSender() {
        return sender;
    }

    public String getMottaker() {
        return mottaker;
    }

    @Override
    public String toString() {
        return sender + " -> " + mottaker;
    }


    @Override
    //blir brukt i bl.a. contains funksjoner i hashmap
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        return o.toString().equals(this.toString());
    }

    @Override
    //blir brukt i bl.a. contains funksjoner i hashmap
    public int hashCode() {
        int a = this.sender.hashCode() / 199933;
        int b = Math.abs(this.mottaker.hashCode() / 199933);
        String str = "" + a + b;
        return Integer.valueOf(str);
    }
}
