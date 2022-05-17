package sample.daytoursnyttsdk;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchModel {
    public String getLocation() {
        return location;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public int getDurationMax() {
        return durationMax;
    }

    public int getActivityDifficultyMin() {
        return activityDifficultyMin;
    }

    public int getActivityDifficultyMax() {
        return activityDifficultyMax;
    }

    public ArrayList<String> getActivityType() {
        return activityType;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public int getPriceMax() {
        return priceMax;
    }

    public int getMinSpotsLeft() {
        return minSpotsLeft;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public boolean isHotelPickUp() {
        return hotelPickUp;
    }

    private String location;
    private int durationMin;
    private int durationMax;
    private int activityDifficultyMin;
    private int activityDifficultyMax;
    private ArrayList<String> activityType;
    private int priceMin;
    private int priceMax;
    private int minSpotsLeft;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private boolean hotelPickUp;


    /**
     * @param loc Staðsetning, með stórum staf og enskum stöfum t.d. "Reykjavik"
     * @param durMin Neðri mörk tímans sem ferðin tekur í mínútum, t.d. 0
     * @param durMax Efri mörk tímans sem ferðin tekur í mínútum, t.d. 180
     * @param actMin Neðri mörk erfiðleikastigs, í int frá 0-5 t.d. 0
     * @param actMax Efri mörk erfiðleikastigs, í int frá 0-5 t.d. 5
     * @param actType Gerðir af ferðum sem leita á eftir, með stórum staf og enskum stöfum t.d. "Gonguferd"
     * @param verdMin Neðri mörk verðs, í int t.d. 0
     * @param verdMax Efri mörk verðs, í int t.d. 10000
     * @param plassEftir Minnsti fjöldi plássa sem þarf að vera eftir í ferðinni, t.d. 5
     * @param dagsFra Fyrri mörk þess tímabils sem leitað er að. Táknað með LocalDate hlut t.d. "2022-04-01"
     * @param dagsTil Seinni mörk þess tímabils sem leitað er að. Táknað með LocalDate hlut t.d. "2022-04-01"
     * @param hotel Hvort þurfi að sækja á hótel, táknað með Boolean t.d. false
     */
    public SearchModel(String loc, int durMin, int durMax, int actMin, int actMax, ArrayList<String> actType, int verdMin, int verdMax,
                       int plassEftir, LocalDate dagsFra, LocalDate dagsTil, boolean hotel){
        location = loc;
        durationMin = durMin;
        durationMax = durMax;
        activityDifficultyMin = actMin;
        activityDifficultyMax = actMax;
        activityType = actType;
        priceMin = verdMin;
        priceMax = verdMax;
        minSpotsLeft = plassEftir;
        dateFrom = dagsFra;
        dateTo = dagsTil;
        hotelPickUp = hotel;
    }
}
