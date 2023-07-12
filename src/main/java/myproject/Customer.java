package myproject;

// Simple class to store the customer info. Has a method to add to String[] customerInfo
public class Customer {
    private String[] customerInfo;

    public Customer() {
        customerInfo = new String[6];
    }

    public void setCustomerInfo(String[] customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String[] getCustomerInfo() {
        return customerInfo;
    }

    public void addCustomerInfo(String info, int i) {
        customerInfo[i] = info;
    }
}
