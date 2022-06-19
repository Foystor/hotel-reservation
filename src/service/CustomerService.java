package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    // map of customers
    private final Map<String, Customer> mapOfCustomers = new HashMap<>();   // key: email   value: customer object
    private static CustomerService customerService;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     * add a customer
     * @param email string email
     * @param firstName string first name
     * @param lastName string last name
     */
    public void addCustomer(String email, String firstName, String lastName) {
        mapOfCustomers.put(email,new Customer(firstName, lastName, email));
    }


    /**
     * get a specific customer
     * @param customerEmail string email
     * @return the customer object
     */
    public Customer getCustomer(String customerEmail) {
        return mapOfCustomers.get(customerEmail);
    }


    /**
     * get all the customers
     * @return a list of customers
     */
    public Collection<Customer> getAllCustomers() {
        return mapOfCustomers.values();
    }


    /**
     * mapOfCustomers getter
     * @return mapOfCustomers
     */
    public Map<String, Customer> getMapOfCustomers() {
        return mapOfCustomers;
    }
}
