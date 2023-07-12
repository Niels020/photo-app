package myproject;

import java.io.IOException;
import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Customer customer;
    static Order order;
    static Dates dates;
    static Invoice invoice;
    static ConvertToJson<Invoice> convertToJson;
    static ConvertToPdf convertToPdf;
    static String PATH = "C:/Users/HaasNielsde(Calco)/Apps/photoshop/savefiles/";

    public static void main(String[] args) {

        customerInfo();
        orderInfo();
        saveOrder();

        scanner.close();
    }
    

    static void customerInfo() {
        customer = new Customer();

        System.out.println("Welcome new customer");
        delayProgram(500);

        System.out.println("Please type in you're full name");
        getCustomerName();

        System.out.println("Please type in you're address");
        getCustomerAddress();

        System.out.println("Please type in you're area code");
        getCustomerAreaCode();

        System.out.println("Please type in you're city");
        getCustomerCity();

        System.out.println("Please type in you're email");
        getCustomerEmail();

        System.out.println("Please type in you're telephone number");
        getCustomerNumber();

        System.out.println("Thank you. Let's start you're order!");
        delayProgram(1000);

    }

    static void getCustomerName() {
        String name = scanner.nextLine();
        if(name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid name. Please try again.");
            getCustomerName();
        }
        else customer.addCustomerInfo(name, 0);
    }

    static void getCustomerAddress() {
        String address = scanner.nextLine();
        if (address.isEmpty() || !address.matches("[a-zA-Z0-9 ]+")) {
            System.out.println("Invalid address. Please try again.");
            getCustomerAddress();
        } else {
            customer.addCustomerInfo(address, 1);
        }
    }

    static void getCustomerAreaCode() {
        String areaCode = scanner.nextLine();
        if (areaCode.isEmpty() || areaCode.length() < 6 || !areaCode.matches("[A-Za-z0-9]+")) {
            System.out.println("Invalid area code. Please try again.");
            getCustomerAreaCode();
        } else {
            customer.addCustomerInfo(areaCode, 2);
        }
    }

    static void getCustomerCity() {
        String city = scanner.nextLine();
        if(city.isEmpty() || !city.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid city. Please try again.");
            getCustomerCity();
        }
        else customer.addCustomerInfo(city, 3);
    }

    static void getCustomerEmail() {
        String email = scanner.nextLine();
        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email. Please try again.");
            getCustomerEmail();
        } 
        else customer.addCustomerInfo(email, 4);
    }

    static void getCustomerNumber() {
        String number = scanner.nextLine();
        if (number.isEmpty() || !number.matches("[0-9]+")) {
            System.out.println("Invalid number. Please try again.");
            getCustomerNumber();
        } 
        else customer.addCustomerInfo(number, 5);
    }

    static void orderInfo() {
        order = new Order();

        System.out.println("Here is our price list");
        delayProgram(1000);
        order.displayPriceListItems();
        delayProgram(1000);

        orderInput();
    }

    static void orderInput() {
        int id;
        int quantity;

        System.out.println("To choose a item enter the item id");
        String inputId = scanner.nextLine();

        if(!inputId.matches("[0-9]+")) {
            System.out.println("Invalid id. Please try again.");
            orderInput();
            return;
        } else {
            id = Integer.parseInt(inputId);
            if(id < 1 || id > 12) {
                System.out.println("Invalid id. Please try again.");
                orderInput();
                return;
            }
        }

        System.out.println("Now enter how many you like to order");
        String inputQuantity = scanner.nextLine();
        
        if(!inputQuantity.matches("[0-9]+")) {
            System.out.println("Invalid quantity. Please try again.");
            orderInput();
            return;
        } else {
            quantity = Integer.parseInt(inputQuantity);
            if(quantity < 1) {
                System.out.println("Invalid quantity. Please try again.");
                orderInput();
                return;
            }
        }

        order.inputOrders(id, quantity);
        anotherOrderInput();
    }

    static void anotherOrderInput() {

        System.out.println("Would you like to add another item? (y/n):");
        String  yesOrNo = scanner.nextLine();

        if (yesOrNo.equals("y")) {
            orderInput();

        } else if (yesOrNo.equals("n")) {
            System.out.println("Thank you for your order!");
            order.createOrder();
            dates = new Dates();
            dates.calcDates(order.getTotalTime());
            delayProgram(2000);
            return;

        } else if(yesOrNo.isEmpty()) {
            System.out.println("invalid input");
            anotherOrderInput();

        }else {
            System.out.println("invalid input");
            anotherOrderInput();
        }
    }

    static void saveOrder() {
        
        invoice = new Invoice();
        invoice.setCustomerInfo(customer.getCustomerInfo());
        invoice.setOrder(order.getOrderTable());
        invoice.setOrderDetails(dates.getOrderTime(), dates.getProductionTime(), dates.getPickupTime());
        invoice.displayInvoice();

        convertToJson = new ConvertToJson<>(invoice, PATH, invoice.getOrderId());
        convertToJson.save();

        showInvoice(invoice.getOrderId());
    }

    static void showInvoice(String orderId) {

        System.out.println("Would you like to review your invoice as a pdf? (y/n):");
        String  yesOrNo = scanner.nextLine();

        if(yesOrNo.equals("y")) {

            convertToPdf = new ConvertToPdf(PATH, orderId);
            try {
                convertToPdf.ConvertPdf();
                convertToPdf.openPdfFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(yesOrNo.equals("n")) {
            System.out.println("Thank you");
        } else {
            System.out.println("invalid input");
            showInvoice(invoice.getOrderId());
        }

    
    }

    static void delayProgram(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}