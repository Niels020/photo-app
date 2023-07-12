package myproject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Dates {
    private String path;
    private ConvertCSV convertCSV;
    private List<String[]> shopHoursCSV;
    private List<String> daysOfWeek;
    private List<Integer> daysOfWeekOpening;
    private List<Integer> daysOfWeekClosing;
    private LocalDateTime localDateTime;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter dateTimeFormatter;
    private int totalTime;
    private String productionTime;
    private String pickupTime;
    private String orderTime;

    public Dates() {
        // Here all the data from PhotoShop_OpeningHours.csv will be set in shopHoursCSV
        path = "./data/PhotoShop_OpeningHours.csv";
        convertCSV = new ConvertCSV(path);
        convertCSV.convert();
        shopHoursCSV = convertCSV.getData();

        // Variables to handle the data from List<String[]> shopHoursCSV
        daysOfWeek = new ArrayList<>();
        daysOfWeekOpening = new ArrayList<>();
        daysOfWeekClosing = new ArrayList<>();
        shopCSVToDaysOfWeek();

        // Variables current dates and sets the current order time
        localDateTime = LocalDateTime.now();
        dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        orderTime = localDateTime.format(dateTimeFormatter);
    }

    public String getProductionTime() {
        return productionTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    // Call this method with total production time and it sets the pickup time of the order
    public void calcDates(int totalTime) {
        this.totalTime = totalTime;
        this.productionTime = Integer.toString(totalTime) + ":00";

        int currentHour = localDateTime.getHour();
        int currentDay = localDateTime.getDayOfWeek().getValue() -1;
        int currentClosingHour = daysOfWeekClosing.get(currentDay);

        if(currentHour > currentClosingHour) currentDay += 1;

        findPickupDate(currentDay);
    }

    // This method keeps looping over daysOfWeek till total production time is less then work hours in the day
    private void findPickupDate(int start) {

        for(int i = start; i < daysOfWeek.size(); i ++) {

            int openingHour = daysOfWeekOpening.get(i);
            int closingHour = daysOfWeekClosing.get(i);
            int totalWorkHours = closingHour - openingHour;

            // Found the pickup day!!!
            if(totalTime < totalWorkHours) {
                String pickupDay = daysOfWeek.get(i);
                String pickupHour = Integer.toString(openingHour + totalTime) + ":00";
                String pickupDate = localDateTime.format(dateFormatter);
                
                pickupTime = pickupDay + " " + pickupDate + " After: " + pickupHour;
                return;
            }

            totalTime -= totalWorkHours;
            localDateTime = localDateTime.plusDays(1);

            // Recursion when daysOfWeek is over
            if(i == (daysOfWeek.size() -1)) findPickupDate(0);  
        }
    }

    // Place data in more convenient arrays
    private void shopCSVToDaysOfWeek() {

        // Some hacky code to make data start at monday
        for(int i = 2; i < shopHoursCSV.size(); i ++) {
            addToDaysOfWeek(i);

            if(i == shopHoursCSV.size() -1) {
                // When loop ends add sunday
                addToDaysOfWeek(1);
            }
        }
    }

    private void addToDaysOfWeek(int index) {
        String[] row = shopHoursCSV.get(index);

        // Split up values from a row of shopHoursCSV
        String day = row[1];
        String opening = row[2];
        String closing = row[3];

        int openingHour = hoursFromString(opening);
        int closingHour = hoursFromString(closing);

        // Adding each value to a separate array
        daysOfWeek.add(day);
        daysOfWeekOpening.add(openingHour);
        daysOfWeekClosing.add(closingHour);
    }

    private int hoursFromString(String x) {
        String[] y = x.split(":");
        int h = Integer.parseInt(y[0]);
        return h;
    }
}
