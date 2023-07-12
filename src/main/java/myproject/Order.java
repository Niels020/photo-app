package myproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String path;
    private ConvertCSV convertCSV;
    private List<String[]> priceListCSV;
    private List<Integer[]> orderInputs;
    private List<String[]> orderTable;
    private BigDecimal totalPrice;
    private int totalTime;

    public Order() {
        // Here all the data from PhotoShop_PriceList.csv will be set in priceListCSV
        path = "./data/PhotoShop_PriceList.csv";
        convertCSV = new ConvertCSV(path);
        convertCSV.convert();
        priceListCSV = convertCSV.getData();

        // Variables to hold the selected orders
        orderInputs = new ArrayList<>();
        orderTable = new ArrayList<>();
        totalPrice = new BigDecimal(0);

    }

    // Getters and setters for variables we need in FinalOrder

    public void setOrderTable(List<String[]> orderTable) {
        this.orderTable = orderTable;
    }

    public List<String[]> getOrderTable() {
        return orderTable;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTime() {
        return totalTime;
    }


    // This method adds orders to List<String[]> orderTable
    public void createOrder() {

        // Add First line to orderTable
        String[] tableHeader = {"Photo type","Price","Amount","Total cost"};
        orderTable.add(tableHeader);

        // loops over List<Integer[]> orderInputs
        for(int i = 0; i < orderInputs.size(); i ++) {

            // Each entry has 2 numbersThe id and the number of orders
            int orderedId = orderInputs.get(i)[0];
            int orderedIndex = orderedId -1;
            int orderedQuantity = orderInputs.get(i)[1];

            // Here it gets the row of List<String[]> priceListCSV at the ordered id
            String[] orderedRow = priceListCSV.get(orderedIndex);

            String type = orderedRow[1];
            String price = orderedRow[2];
            String time = orderedRow[3];
            String quantity = Integer.toString(orderedQuantity);

            String priceTotal = calcPrices(price, orderedQuantity);
            calcHours(time, orderedQuantity);

            // Add each order to the List<String[]> orderTable
            String[] tableRow = {type, price, quantity, priceTotal};
            orderTable.add(tableRow);
        }

        // Add last line to orderTable
        String[] tableEnd = {"Total price"," "," ", totalPrice.toString()};
        orderTable.add(tableEnd);

    }

    // Calculate the price for each row in List<String[]> orderTable
    private String calcPrices(String price, int quantity) {
        BigDecimal p = new BigDecimal(price);
        BigDecimal t = p.multiply(BigDecimal.valueOf(quantity));
        totalPrice = totalPrice.add(t);

        String totalPriceRow = t.toString();
        return totalPriceRow;
    }

    // Calculate the totalTime to produce the order
    private void calcHours(String time, int quantity) {
        String[] y = time.split(":");
        int hours = Integer.parseInt(y[0]);
        
        // Multiplying time to quantity
        totalTime += (hours * quantity);
    }

    // Method for the user to choose orders and the quantity of the order
    public void inputOrders(int id, int quantity) {

        boolean isNewOrder = checkIsNewOrder(id);

        if(isNewOrder) {

            Integer[] newOrder = {id, quantity};
            orderInputs.add(newOrder);
        } else {

            for (Integer[] row : orderInputs) {
                if(row[0] == id) {
                    row[1] += quantity; 
                }
            } 
        }
    }

    // Checking List<Integer[]> orderInputs if the new id excists.
    private boolean checkIsNewOrder(int newId) {
        for (Integer[] row : orderInputs) {
            if (row[0] == newId) {
                return false;
            }
        }
        return true;
    }

    // Display all price list items in the console
    public void displayPriceListItems() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");

        for (String[] row : priceListCSV) {
            String[] item = row.clone();

            item[0] = "id: " + item[0];
            item[1] = "Format: " + item[1];
            item[2] = "Price: $" + item[2];
            item[3] = "Time to make: " + item[3];

            String priceListAsString = String.join(", ", item);
            System.out.println(priceListAsString);
        }

        System.out.println("");
        System.out.println("--------------------------------------------------------------------------------");    
    }
}
