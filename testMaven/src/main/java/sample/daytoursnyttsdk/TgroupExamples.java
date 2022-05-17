package sample.daytoursnyttsdk;

import java.time.LocalDate;
import java.util.ArrayList;

public class TgroupExamples {
    public static void main(String[] args) throws ClassNotFoundException {

        /** Dæmi 1 - Búa til og fjarlægja þátttakendur úr ferðum **/

        // Aðal DayTours hluturinn þar sem öll gögnin eru geymd
        DayTours dt = new DayTours("Date");

        // Búa til SearchModel hlut sem leitað verður eftir
        ArrayList<String> activities = new ArrayList<>();
        activities.add("Hjolaferd");
        activities.add("Gonguferd");
        activities.add("Sundferd");
        activities.add("Paintball");
        // Nota "ANY" í location ef staðsetning skiptir ekki máli.
        SearchModel sm = new SearchModel("Reykjavik", 0, 10000, 0, 555, activities,
                0, 500000,5, LocalDate.of(2020,1, 1),
                LocalDate.of(2025, 3, 3), false);

        // Uppfæra dt með þeim gögnum sem koma út úr leit með SearchModelinu
        dt.getDayTours(sm);

        // Ná í lýsingar á þeim DayTour hlutum sem komu út úr leitinni
        ArrayList<String> descript = dt.getDayTourDescriptions();
        for(String x: descript){
            System.out.println(x);
        }

        // Setja einn af DayTour hlutum í breytu til að bæta við / fjarlægja þátttakendur
        DayTour sample1 = dt.getDayTourList().get(1);
        System.out.println("Plass eftir a sample1 fyrir eydingu: " + sample1.getSpotsLeft());

        // Þurrka út alla þáttakendur í ferðinni
        System.out.println("Reyni að eyða öllum þátttakendum úr ferð.");
        sample1.removeAllParticipants();
        dt.getDayTours(sm);
        sample1 = dt.getDayTourList().get(1);
        System.out.println("Þátttakendur í ferð eftir eyðingu: ");
        for(Participant x: sample1.getParticipants()){
            System.out.println(x.getName());
        }
        System.out.println("Plass eftir a sample1 eftir eydingu: " + sample1.getSpotsLeft());

        // Bæta við nýjum þátttakendum
        Participant nr1 = new Participant("Lucifer", "5661818", "satan@hell.com", "0501002020", sample1.getID());
        Participant nr2 = new Participant("Kleina", "5661444", "Klein@yahoo.com", "0311552019", sample1.getID());
        ArrayList<Participant> nyirMedlimir = new ArrayList<>();
        nyirMedlimir.add(nr1);
        nyirMedlimir.add(nr2);
        sample1.addParticipants(nyirMedlimir);

        // Ná aftur í gögnin frá leitinni og athuga hvort nýju þátttakendurnir komi með
        dt.getDayTours(sm);
        sample1 = dt.getDayTourList().get(1);
        ArrayList<Participant> users = sample1.getParticipants();
        System.out.println("Þátttakendur í ferðinni eftir að bætt var við þátttakendum: ");
        for(Participant x: users){
            System.out.println(x.getName());
        }
        System.out.println("Plass eftir a sample1 eftir ad bætt vid þátttakendum: " + sample1.getSpotsLeft());


        /** Dæmi 2 - Ná í allar ferðir **/
        /*
        DayTours dt = new DayTours("Date");
        ArrayList<String> activities = new ArrayList<>();
        activities.add("Hjolaferd");
        activities.add("Gonguferd");
        activities.add("Sundferd");
        activities.add("Paintball");
        SearchModel sm = new SearchModel("ANY", 0, 10000, 0, 555, activities,
                0, 500000,5, LocalDate.of(2020,1, 1),
                LocalDate.of(2025, 3, 3), false);
        dt.getDayTours(sm);
        for(DayTour x: dt.getDayTourList()){
            System.out.println(x.getTourName());
        }
         */
    }
}