package sample.daytoursnyttsdk;

public class Participant {
    public String getName() {
        return name;
    }
    public String getPhoneNr() {
        return phoneNr;
    }
    public String getEmail() {
        return email;
    }
    public String getKennitala() {
        return kennitala;
    }
    /*
    public int getFjoldi() {
        return fjoldi;
    }
     */
    public int getID() {
        return ID;
    }

    private String name;
    private String phoneNr;
    private String email;
    private String kennitala;
    //private int fjoldi;
    private int ID;

    public Participant(String nafn, String simanumer, String tolvupostur, String kt, int identity){
        name = nafn;
        phoneNr = simanumer;
        email = tolvupostur;
        kennitala = kt;
        //this.fjoldi = fjoldi;
        ID = identity;

    }
}
