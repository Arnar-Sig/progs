package sample.daytoursnyttsdk;

import java.time.LocalDate;
import java.util.ArrayList;

public class DayTour implements Comparable<DayTour>{
    /** Getters **/

    /** Skilar öllum upplýsingum um ferðina í strengjaformi. **/
    public String getAll() {
        String part = "";
        for(int i=0; i<participants.size(); i++){
            if(participants.size()-i != 1) {
                part = part + participants.get(i).getName() + " - ";
            }
            else{
                part = part + participants.get(i).getName() + " ";
            }
        }
        String ut = tourName + ", " + location + ", " + duration + ", " + date + ", " + spotsLeft + ", " + price + ", "
                + activityType + ", " + activityDifficulty + ", " + hotelPickUp + ", " + part + ", " + ID;
        return ut;
    }
    public String getTourName() {
        return tourName;
    }
    public String getLocation() {
        return location;
    }
    public int getDuration() {
        return duration;
    }
    public LocalDate getDate() {
        return date;
    }
    public int getSpotsLeft() {
        return spotsLeft;
    }
    public Integer getPrice() {
        return price;
    }
    public String getActivityType() {
        return activityType;
    }
    public int getActivityDifficulty() {
        return activityDifficulty;
    }
    public int isHotelPickUp() {
        return hotelPickUp;
    }
    public ArrayList<Participant> getParticipants() {
        return participants;
    }
    public int getID() {
        return ID;
    }

    /** Local breytur **/
    private String tourName;
    private String location;
    private int duration;
    private LocalDate date;
    private int spotsLeft;
    private Integer price;
    private String activityType;
    private int activityDifficulty;
    private int hotelPickUp;
    private ArrayList<Participant> participants;
    private final int ID;
    private String sortType;

    private String[] sortTypes = {"Name", "Price: Low to High", "Price: High to Low"};

    public DayTour(String nafn, String loc, int dur, LocalDate dags, int plassEftir, int verd,
                   String type, int erfidleikastig, int pickup, ArrayList<Participant> medlimir, int identity){
        ID = identity;
        tourName = nafn;
        location = loc;
        duration = dur;
        date = dags;
        spotsLeft = plassEftir;
        price = Integer.valueOf(verd);
        activityType = type;
        activityDifficulty = erfidleikastig;
        hotelPickUp = pickup;
        participants = medlimir;
    }

    public void addParticipants(ArrayList<Participant> ppl){
        if (ppl.size() > spotsLeft){
            System.out.println("Ekki eru nogu morg plass eftir i ferdinni til ad baeta vid " + ppl.size() + " manneskjum." +
                    " Haetti vid adgerd.");
            return;
        }
        DB databaseConnection = new DB();
        try {
            databaseConnection.addParticipantDatabase(ppl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Ekki tokst ad setja Participants i gagnagrunn svo haett var vid adgerdina.");
            return;
        }

        for(int i = 0; i < ppl.size(); i++){
            participants.add(ppl.get(i));
            spotsLeft--;
        }
    }

    //TO-do: Lata gagnagrunn gera eins
    public void removeParticipants(ArrayList<Participant> ppl){
        if (participants.size() == 0 || participants.size() < ppl.size()){
            return;
        }
        DB databaseConnection = new DB();
        try {
            databaseConnection.removeParticipantDatabase(ppl);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (Participant participant : ppl) {
            for (int j = 0; j < participants.size(); j++) {
                if (participant.getKennitala().equalsIgnoreCase(participants.get(j).getKennitala())) {
                    participants.remove(participants.get(j));
                }
            }
        }
    }

    public void removeAllParticipants(){
        if(participants.size() == 0){
            return;
        }
        DB databaseConnection = new DB();
        try {
            databaseConnection.removeParticipantDatabase(participants);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        for (Participant participant : participants) {
            for (int j = 0; j < participants.size(); j++) {
                if (participant.getKennitala().equalsIgnoreCase(participants.get(j).getKennitala())) {
                    participants.remove(participants.get(j));
                }
            }
        }
    }


    @Override
    public int compareTo(DayTour o) {
        if (this.sortType.equals(sortTypes[0])) {
            return this.getTourName().compareTo(o.getTourName());
        }
        else if (this.sortType.equals(sortTypes[1]) || this.sortType.equals(sortTypes[2])) {
            return this.getPrice().compareTo(o.getPrice());
        }
        else return this.getDate().compareTo(o.getDate());
    }

    public void sortBy(String s) {
        sortType = s;
    }
}
