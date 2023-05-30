package myproject;

import java.util.Scanner;


public class App {

    static void greetCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello user");
        System.out.println("Are you a new customer? (yes/no): ");
        String isNewCustomerString = scanner.nextLine();

        if (isNewCustomerString.equalsIgnoreCase("yes")) {
            // NEW CUSTOMER FUNCTIONALITY
            System.out.println("Welcome new customer");
            createNewCustomer();
            
        } else if (isNewCustomerString.equalsIgnoreCase("no")) {
            // EXCISTING CUSTOMER FUNCTIONALITYy
            System.out.println("Welcome back! Please type in you're customer id");

        } else {
            System.out.println("Invalid input. Please enter either 'yes' or 'no'.");
            greetCustomer();
        }

        scanner.close();
    }

    static void createNewCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please type in you're full name");
        String name = scanner.nextLine();
        System.out.println("Please type in you're adress");
        String adress = scanner.nextLine();
        System.out.println("Please type in you're zipcode");
        String zipcode = scanner.nextLine();
        System.out.println("Please type in you're city");
        String city = scanner.nextLine();
        System.out.println("Please type in you're e-mail");
        String email = scanner.nextLine();
        System.out.println("Please type in you're phone number");
        String phone = scanner.nextLine();

        scanner.close();

        Customer customer = new Customer(name, adress, zipcode, city, email, phone);
    }


    
    public static void main(String[] args) {
        greetCustomer();




        



    }
}
