package sample.daytoursnyttsdk;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DB {
    public ArrayList<DayTour> getDayToursDatabase(SearchModel sm) throws Exception {
            // TO-do: láta fallið skila hlut af DayTours með öllu útfylltu
            //        (þarf að ná í gögn úr Participants gagnagrunni t.d.)

            Class.forName("org.sqlite.JDBC");
            Connection connDayTour = null;
            Connection connParticipant = null;
            ArrayList<DayTour> fylkiAfRodum = new ArrayList<>();
            try
            {
                connDayTour = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/daytoursnyttsdk/DayTours.db");
                connParticipant = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/daytoursnyttsdk/DayTours.db");
                Statement statementDayTour = connDayTour.createStatement();
                Statement statementParticipant= connParticipant.createStatement();
                /*
                String sqlSkipun = "SELECT * FROM DayTours WHERE day BETWEEN " + sm.getDateFrom() + " AND " + sm.getDateTo()
                        + " AND price BETWEEN " + "sm.getPriceMin()" + " AND " + sm.getPriceMax()
                        + " AND location = " + sm.getLocation() + " AND spots >= " + sm.getMinSpotsLeft()
                        + " AND activityType = " + sm.getActivityType() + " AND activityDifficulty BETWEEN " + sm.getActivityDifficultyMin()
                        + " AND " + sm.getActivityDifficultyMax() + " AND hotelPickUp = " + sm.isHotelPickUp()
                        + " AND duration BETWEEN " + sm.getDurationMin() + " AND " + sm.getDurationMax();

                 */

                /** Útbúa part af SQL-skipun sem sér um activityTypes **/
                String activityTypeStrengur = "";
                int first = 0;
                for(int i= 0; i<sm.getActivityType().size(); i++){
                    if(first == 0){
                        activityTypeStrengur = activityTypeStrengur + "activityType = " + '"' + sm.getActivityType().get(i) + '"';
                        first = 1;
                        continue;
                    }
                    activityTypeStrengur = activityTypeStrengur + " OR " +  "activityType = " + '"' + sm.getActivityType().get(i) + '"';
                }

                /** SQL-skipunin sem notuð verður til að ná í gögn um DayTours frá gagnagrunni **/
                String sqlSkipun;
                if(sm.getLocation().equals("ANY")){
                    sqlSkipun = "SELECT * FROM DayTours WHERE price BETWEEN " + sm.getPriceMin() + " AND " + sm.getPriceMax()
                            + " AND spots >= " + sm.getMinSpotsLeft()
                            + " AND (" + activityTypeStrengur + ") AND " + "activityDifficulty BETWEEN " + sm.getActivityDifficultyMin()
                            + " AND " + sm.getActivityDifficultyMax() + " AND hotelPickUp = " + sm.isHotelPickUp()
                            + " AND duration BETWEEN " + sm.getDurationMin() + " AND " + sm.getDurationMax();
                }
                else{
                    sqlSkipun = "SELECT * FROM DayTours WHERE price BETWEEN " + sm.getPriceMin() + " AND " + sm.getPriceMax()
                            + " AND location = " + '"' +  sm.getLocation() + '"' + " AND spots >= " + sm.getMinSpotsLeft()
                            + " AND (" + activityTypeStrengur + ") AND " + "activityDifficulty BETWEEN " + sm.getActivityDifficultyMin()
                            + " AND " + sm.getActivityDifficultyMax() + " AND hotelPickUp = " + sm.isHotelPickUp()
                            + " AND duration BETWEEN " + sm.getDurationMin() + " AND " + sm.getDurationMax();
                }

                /*
                // GAMLA sqlSkipun
                String sqlSkipun = "SELECT * FROM DayTours WHERE price BETWEEN " + sm.getPriceMin() + " AND " + sm.getPriceMax()
                        + " AND location = " + '"' +  sm.getLocation() + '"' + " AND spots >= " + sm.getMinSpotsLeft()
                        + " AND activityType = " + '"' + sm.getActivityType() + '"' + " AND activityDifficulty BETWEEN " + sm.getActivityDifficultyMin()
                        + " AND " + sm.getActivityDifficultyMax() + " AND hotelPickUp = " + sm.isHotelPickUp()
                        + " AND duration BETWEEN " + sm.getDurationMin() + " AND " + sm.getDurationMax()
                        + " AND day BETWEEN " + '"' + sm.getDateFrom() + '"' + " AND " + '"' + sm.getDateTo() + '"';
                 //System.out.println(sqlSkipun);
                 */


                /** Náð í gögn frá gagnagrunni og sett sem fylki af strengjum **/
                ResultSet r = statementDayTour.executeQuery(sqlSkipun);
                ResultSetMetaData rm = r.getMetaData();
                int colCount = rm.getColumnCount();
                while(r.next()){
                    if(!((LocalDate.parse(r.getString(2)).isAfter(sm.getDateFrom()) ||
                            LocalDate.parse(r.getString(2)).isEqual(sm.getDateFrom())) &&
                            (LocalDate.parse(r.getString(2)).isBefore(sm.getDateTo()) ||
                                    LocalDate.parse(r.getString(2)).isEqual(sm.getDateTo())))) {
                        continue;
                    }
                    String rod = "";
                    for (int i = 1; i <= colCount; i++) {
                        rod += r.getString(i) + ", ";
                    }

                    /** Ná í participants fyrir núverandi DayTour hlut úr gagnagrunni **/
                    List<String> list = Arrays.asList(rod.split("\\s*,\\s*"));
                    // Ná í og búa til participants
                    ArrayList<Participant> medlimir = new ArrayList<>();
                    String sqlParticipants = "SELECT * FROM Participants WHERE PK = " + list.get(9) + ";";
                    ResultSet resultParticipants = statementParticipant.executeQuery(sqlParticipants);

                    while(resultParticipants.next()){
                        Participant medlimur = new Participant(resultParticipants.getString(1),
                                resultParticipants.getString(3),resultParticipants.getString(2),
                                resultParticipants.getString(4), resultParticipants.getInt(5));
                        medlimir.add(medlimur);
                    }
                    /*
                    //TEMP gervigögn - breyta þegar Participant er útfært
                    Participant dummy = new Participant("test", "test", "test", "test", 5);
                    ArrayList<Participant> dummyFylki = new ArrayList<>();
                    dummyFylki.add(dummy);
                    //TEMP gervigögn
                     */
                    /** Búa til DayTour hlut og bæta honum við fylkið **/
                    DayTour temp = new DayTour(list.get(0), list.get(3), Integer.parseInt(list.get(8)), LocalDate.parse(list.get(1)),
                            Integer.parseInt(list.get(4)), Integer.parseInt(list.get(2)), list.get(5), Integer.parseInt(list.get(6)),
                            Integer.parseInt(list.get(7)), medlimir, Integer.parseInt(list.get(9)));
                    fylkiAfRodum.add(temp);

                    //fylkiAfRodum.add(rod);
                }

            }
            catch(SQLException e){
                System.err.println(e.getMessage());
            }
            finally{
                try
                {
                    if(connDayTour != null) connDayTour.close();
                    if(connParticipant != null) connParticipant.close();
                }
                catch(SQLException e)
                {
                    System.err.println(e);
                }
            }
            return fylkiAfRodum;
    }

    protected void addParticipantDatabase(ArrayList<Participant> ppl) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = null;
        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/daytoursnyttsdk/DayTours.db");
            Statement statement;

            for(int i=0; i<ppl.size(); i++){
                statement = conn.createStatement();
                String sqlSkipun = "INSERT INTO Participants(name, email, phone, kennitala, pk) VALUES " +
                        "(" + '"' + ppl.get(i).getName() + '"' + ", " + '"' + ppl.get(i).getEmail() + '"' + ", " +
                        '"' + ppl.get(i).getPhoneNr() + '"' + ", " + '"' + ppl.get(i).getKennitala() + '"' + ", " +
                        '"' + ppl.get(i).getID() + '"' +  ");";
                //System.out.println(sqlSkipun);
                statement.execute(sqlSkipun);

                // Fækka plássum eftir í þeirri ferð sem þáttakandi var að skrá sig
                statement = conn.createStatement();
                sqlSkipun = "UPDATE DayTours SET spots = spots - 1 WHERE PK = " + ppl.get(i).getID() + ";";
                statement.execute(sqlSkipun);

            }

        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        finally{
            try {
                if(conn != null) conn.close();
            }
            catch(SQLException e) {
                System.err.println(e);
            }
        }
    }
    protected void removeParticipantDatabase(ArrayList<Participant> ppl) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/java/sample/daytoursnyttsdk/DayTours.db");
            Statement statement;

            for(int i=0; i<ppl.size(); i++){
                statement = conn.createStatement();
                String sqlSkipun = "DELETE FROM Participants WHERE kennitala = " + '"' + ppl.get(i).getKennitala() + '"'
                        + " AND PK = " + '"' + ppl.get(i).getID() + '"' + ";";
                //System.out.println(sqlSkipun);
                statement.execute(sqlSkipun);

                // Fjölga plássum eftir í þeirri ferð sem þáttakandi var að skrá sig úr
                statement = conn.createStatement();
                sqlSkipun = "UPDATE DayTours SET spots = spots + 1 WHERE PK = " + ppl.get(i).getID() + ";";
                statement.execute(sqlSkipun);
            }

        } catch(SQLException e){
            System.err.println(e.getMessage());
        } finally{
            try {
                if(conn != null) conn.close();
            }
            catch(SQLException e) {
                System.err.println(e);
            }
        }
    }
}