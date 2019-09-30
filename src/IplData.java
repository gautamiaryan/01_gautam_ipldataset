import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class IplData {
    private static final int ID=0;
    private static final int SEASON=1;
    private static final int CITY=2;
    private static final int DATE=3;
    private static final int TEAM1=4;
    private static final int TEAM2=5;
    private static final int TOSS_WINNER=6;
    private static final int TOSS_DECISION=7;
    private static final int RESULT=8;
    private static final int DL_APPLIED=9;
    private static final int WINNER=10;
    private static final int WIN_BY_RUNS=11;
    private static final int WIN_BY_WICKETS=12;
    private static final int PLAYER_OF_MATCH=13;
    private static final int VENUE=14;
    private static final int UMPIRE1=15;
    private static final int UMPIRE2=16;
    private static final int UMPIRE3=17;


    private static final int MATCH_ID = 0;
    private static final int INNING = 1;
    private static final int BATTING_TEAM = 2;
    private static final int BOWLING_TEAM = 3;
    private static final int OVER = 4;
    private static final int BALL = 5;
    private static final int BATSMAN = 6;
    private static final int NON_STRIKER = 7;
    private static final int BOWLER = 8;
    private static final int IS_SUPER_OVER = 9;
    private static final int WIDE_RUNS = 10;
    private static final int BYE_RUNS = 11;
    private static final int LEGBYE_RUNS = 12;
    private static final int NOBALL_RUNS = 13;
    private static final int PENALTY_RUNS = 14;
    private static final int BATSMAN_RUNS = 15;
    private static final int EXTRA_RUNS = 16;
    private static final int TOTAL_RUNS = 17;
    private static final int PLAYER_DISMISSED = 18;
    private static final int DISMISSAL_KIND = 19;
    private static final int FIELDER = 20;

    public static void main(String [] args){
        File matchFile=new File("resources/matches.csv");
        List<Match> matchList = parseMatchCsv(matchFile);

        File deliveryFile=new File("resources/deliveries.csv");
        List<Delivery> deliveryList=parseDeliveryCsv(deliveryFile);


      System.out.println(findNumberOfMatchesPlayedByEachTeam(matchList));
      System.out.println(findNumberOfMatchesWonByEachTeam(matchList));
      System.out.println(findExtraRunCocededByEachTeamIn2016(matchList,deliveryList));
      findBowlersListWithBestEconomyRate(matchList, deliveryList);




    }
    private static List<Match> parseMatchCsv(File path) {

        List<Match> matchList = new ArrayList<>();
        try {
            Scanner inputStream = new Scanner(path);
            //use to skip header from csv file
            if (inputStream.hasNext()) {
                inputStream.nextLine();
            }
            while (inputStream.hasNext()) {
                String rowData = inputStream.nextLine();
                String[] matchDataList = rowData.split(",", 18);
                Match matchData = new Match();
                matchData.setId(matchDataList[ID]);
                matchData.setSeason(matchDataList[SEASON]);
                matchData.setCity(matchDataList[CITY]);
                matchData.setDate(matchDataList[DATE]);
                matchData.setTeam1(matchDataList[TEAM1]);
                matchData.setTeam2(matchDataList[TEAM2]);
                matchData.setToss_winner(matchDataList[TOSS_WINNER]);
                matchData.setToss_decision(matchDataList[TOSS_DECISION]);
                matchData.setResult(matchDataList[RESULT]);
                matchData.setDl_applied(matchDataList[DL_APPLIED]);
                matchData.setWinner(matchDataList[WINNER]);
                matchData.setWin_by_runs(matchDataList[WIN_BY_RUNS]);
                matchData.setWin_by_wickets(matchDataList[WIN_BY_WICKETS]);
                matchData.setPlayer_of_match(matchDataList[PLAYER_OF_MATCH]);
                matchData.setVenue(matchDataList[VENUE]);
                matchData.setUmpire1(matchDataList[UMPIRE1]);
                matchData.setUmpire2(matchDataList[UMPIRE2]);
                matchData.setUmpire3(matchDataList[UMPIRE3]);
                matchList.add(matchData);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return matchList;

    }

    private static List<Delivery> parseDeliveryCsv(File path) {

        ArrayList<Delivery> deliveryList = new ArrayList<>();
        try {
            Scanner inputStream = new Scanner(path);
            //use to skip header from csv file
            if (inputStream.hasNext()) {
                inputStream.nextLine();
            }
            while (inputStream.hasNext()) {
                String rowData = inputStream.nextLine();
                String[] deliveryDataList = rowData.split(",", 21);
                Delivery deliveryData = new Delivery();
                deliveryData.setMatch_id(deliveryDataList[MATCH_ID]);
                deliveryData.setInning(deliveryDataList[INNING]);
                deliveryData.setBatting_team(deliveryDataList[BATTING_TEAM]);
                deliveryData.setBowling_team(deliveryDataList[BOWLING_TEAM]);
                deliveryData.setOver(deliveryDataList[OVER]);
                deliveryData.setBall(deliveryDataList[BALL]);
                deliveryData.setBatsman(deliveryDataList[BATSMAN]);
                deliveryData.setNon_striker(deliveryDataList[NON_STRIKER]);
                deliveryData.setBowler(deliveryDataList[BOWLER]);
                deliveryData.setIs_super_over(deliveryDataList[IS_SUPER_OVER]);
                deliveryData.setWide_runs(deliveryDataList[WIDE_RUNS]);
                deliveryData.setBye_runs(deliveryDataList[BYE_RUNS]);
                deliveryData.setLegbye_runs(deliveryDataList[LEGBYE_RUNS]);
                deliveryData.setNoball_runs(deliveryDataList[NOBALL_RUNS]);
                deliveryData.setPenalty_runs(deliveryDataList[PENALTY_RUNS]);
                deliveryData.setBatsman_runs(deliveryDataList[BATSMAN_RUNS]);
                deliveryData.setExtra_runs(deliveryDataList[EXTRA_RUNS]);
                deliveryData.setTotal_runs(deliveryDataList[TOTAL_RUNS]);
                deliveryData.setPlayer_dismissed(deliveryDataList[PLAYER_DISMISSED]);
                deliveryData.setDismissal_kind(deliveryDataList[DISMISSAL_KIND]);
                deliveryData.setFielder(deliveryDataList[FIELDER]);
                deliveryList.add(deliveryData);
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return deliveryList;
    }

    private static Map findNumberOfMatchesPlayedByEachTeam(List<Match> matchList){
        Map<String, Integer> season = new TreeMap<>();
        for (Match matchesRow : matchList) {
            if (season.containsKey(matchesRow.getSeason())) {
                int seasonCount = season.get(matchesRow.getSeason());
                season.put(matchesRow.getSeason(), ++seasonCount);
            } else {
                season.put(matchesRow.getSeason(),1);
            }
        }
        return season;

    }

    private  static Map findNumberOfMatchesWonByEachTeam(List<Match> matchList){

            Map<String, Integer> winner = new TreeMap<>();
            for (Match matchesRow : matchList) {
                if (winner.containsKey(matchesRow.getWinner())) {
                    int winnerCount = winner.get(matchesRow.getWinner());
                    winner.put(matchesRow.getWinner(), ++winnerCount);
                } else if (!"".equals(matchesRow.getWinner())) winner.put(matchesRow.getWinner(), 1);
            }
            return winner;
        }


    private static Map findExtraRunCocededByEachTeamIn2016(List<Match> matchList, List<Delivery> deliveryList){
        Map<String, Integer> extraRuns = new TreeMap<>();
        for (Match matchRow : matchList) {
            if (matchRow.getSeason().equals("2016")) {
                for (Delivery deliveryRow : deliveryList) {
                    if (matchRow.getId().equals(deliveryRow.getMatch_id())) {
                        if (extraRuns.containsKey(deliveryRow.getBowling_team())) {
                            int extraRun = extraRuns.get(deliveryRow.getBowling_team());
                            extraRuns.put(deliveryRow.getBowling_team(), extraRun + Integer.parseInt(deliveryRow.getExtra_runs()));
                        } else {
                            extraRuns.put(deliveryRow.getBowling_team(), Integer.parseInt(deliveryRow.getExtra_runs()));
                        }
                    }
                }
            }
        }
        return extraRuns;
    }
    private static void findBowlersListWithBestEconomyRate(List<Match> matchList, List<Delivery> deliveryList) {

        Map<String, Integer> totalRuns = new TreeMap<>();
        Map<String, Integer> totalBalls = new TreeMap<>();
        for (Match matchRow : matchList) {
            if (matchRow.getSeason().equals("2015")) {
                for (Delivery deliveryRow : deliveryList) {
                    if (matchRow.getId().equals(deliveryRow.getMatch_id())) {
                        if (totalRuns.containsKey(deliveryRow.getBowler())) {
                            int run = totalRuns.get(deliveryRow.getBowler());
                            int ball = totalBalls.get(deliveryRow.getBowler());
                            totalRuns.put(deliveryRow.getBowler(), run + Integer.parseInt(deliveryRow.getTotal_runs()));
                            totalBalls.put(deliveryRow.getBowler(), ++ball);
                        } else {
                            totalRuns.put(deliveryRow.getBowler(), Integer.parseInt(deliveryRow.getTotal_runs()));
                            totalBalls.put(deliveryRow.getBowler(), 1);
                        }
                    }
                }
            }
        }
        findBowlerNameWithBestEconomyRate(totalBalls, totalRuns);
    }

    private static void findBowlerNameWithBestEconomyRate(Map<String, Integer> totalBalls, Map<String, Integer> totalRuns) {

        Set setTotalBalls = totalBalls.entrySet();
        Iterator totalBall = setTotalBalls.iterator();
        float minEconomy = Float.MAX_VALUE;
        String bowlerName = "";
        while (totalBall.hasNext()) {
            Map.Entry balls = (Map.Entry) totalBall.next();
            int run = totalRuns.get(balls.getKey());
            float economy =  run / ((int) balls.getValue() / 6f);
            if (economy < minEconomy) {
                minEconomy = economy;
                bowlerName = (String) balls.getKey();
            }
        }
        System.out.println("Bowler Name : " + bowlerName + ", " + "Economy : " + minEconomy);
    }



}
