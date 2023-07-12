package myproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


// All data for the savefile and the invoice will be collected in this class
public class Invoice {
    private String orderId;
    private String[] customerInfo;
    private String[] shopAssistant;
    private HashMap<String, String> orderDetails;
    private List<String[]> order;

    Invoice() {
        orderId = getRandomOrderId();
        customerInfo = new String[6];
        shopAssistant = new String[]{"Constant van den Kaart", "2345"};
        orderDetails  = new HashMap<String, String>();
        order = new ArrayList<>();
    }

    public void displayInvoice() {
        System.out.println("----------- orderId -----------");
        System.out.println(orderId);

        System.out.println("----------- customerInfo -----------");
        for(String x : customerInfo) {

            System.out.println(x);
        }

        System.out.println("----------- shopAssistant -----------");
        for(String x : shopAssistant) {
            System.out.println(x);
        }

        System.out.println("----------- orderDetails -----------");
        for (Map.Entry<String, String> x : orderDetails.entrySet()) {
            System.out.println(x.getKey() + ": " + x.getValue());
        }

        System.out.println("----------- order -----------");
        for(int i = 1; i < order.size(); i ++) {
                System.out.println(order.get(0)[0] + "         " + order.get(0)[1] + " " + order.get(0)[2] + " " + order.get(0)[3]);
                System.out.println(order.get(i)[0] + "   " + order.get(i)[1] + "   " + order.get(i)[2] + "  " + order.get(i)[3]);

            }
    }

    public void setCustomerInfo(String[] customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String[] getCustomerInfo() {
        return customerInfo;
    }

    public void setShopAssistant(String[] shopAssistant) {
        this.shopAssistant = shopAssistant;
    }

    public String[] getShopAssistant() {
        return shopAssistant;
    }

    public void setOrderDetails(String orderDate, String productionTime, String pickupTime) {
        orderDetails.put("Order number",orderId);
        orderDetails.put("Order date",orderDate);
        orderDetails.put("Production time",productionTime);
        orderDetails.put("Pickup time",pickupTime);
    }

    public HashMap<String, String> getOrderDetails() {
        return orderDetails;
    }

    public void setOrder(List<String[]> order) {
        this.order = order;
    }

    public List<String[]> getOrder() {
        return order;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    // This method returns a random id as a string
    private String getRandomOrderId() {
        Random random = new Random();
        StringBuilder orderId = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            orderId.append(digit);
        }
        
        return orderId.toString();
    }
}
