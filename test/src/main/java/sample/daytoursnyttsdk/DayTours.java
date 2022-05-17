package sample.daytoursnyttsdk;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DayTours {

    private ArrayList<DayTour> dayTourList;
    private SearchModel searchModel;
    private String sort;

    public ArrayList<DayTour> getDayTourList() {
        return dayTourList;
    }
    public DayTours(String rodun){
        sort = rodun;
    }
    public void updateSorting(String rodun){ sort = rodun; }

    public void updateSearchModel(String loc, int durMin, int durMax, int actMin,
                                  int actMax, ArrayList<String> actType, int verdMin, int verdMax,
                                  int plassEftir, LocalDate dagsFra, LocalDate dagsTil, boolean hotel){

        searchModel = new SearchModel(loc, durMin, durMax, actMin, actMax, actType,
                verdMin, verdMax, plassEftir, dagsFra, dagsTil, hotel);
    }

    public SearchModel getSearchModel() {
        return searchModel;
    }

    /** Uppfærir daytour-listann eftir leit í gagnagrunni **/
    public void updateDayTours(ArrayList<DayTour> dayTourArrayList) {
        dayTourList = dayTourArrayList;
        updateSort(sort);
    }

    /** Skilar strengja-ArrayList með lýsingu á daytours **/
    public ArrayList<String> getDayTourDescriptions() {
        ArrayList<String> descriptions = new ArrayList<>();
        for (int i = 0; i < dayTourList.size(); i++) {
            DayTour dt = dayTourList.get(i);
            String s = "";
            s = s + dt.getTourName() + " á " + dt.getLocation() + ", " + dt.getDate().toString();
            descriptions.add(s);
        }
        return descriptions;
    }

    /** Uppfærir röðunarskilyrði í öllum daytours í lista **/
    public void updateSort(String s) {
        for (DayTour dt: dayTourList) {
            dt.sortBy(s);
        }
        if (s.equals("Price: High to Low")) {
            Collections.sort(dayTourList, Collections.reverseOrder());
        }
        else Collections.sort(dayTourList);
    }

    /**
     * @param sm = SearchModel hlutur sem inniheldur þau gögn sem leita á eftir í gagnagrunni.
     * @return ArrayList<DayTour> fylki af DayTour hlutum sem fundust í gagnagrunni.
     *         Uppfærir currDayTours fylkið með útkomu.
     */
    public ArrayList<DayTour> getDayTours(SearchModel sm){
        ArrayList<DayTour> utkoma = new ArrayList<DayTour>();
        DB dbConnection = new DB();
        try {
            utkoma = dbConnection.getDayToursDatabase(sm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dayTourList = utkoma;
        return utkoma;
    }
}
