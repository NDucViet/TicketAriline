package com.ArilineTicket.ArilineTicket.Model.Service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ArilineTicket.ArilineTicket.Model.Entity.Customer_Type;
import com.ArilineTicket.ArilineTicket.Model.Repository.Customer_Type_Repository;

@Service
public class Customer_TypeService implements ICustomer_TypeService {

    ArrayList<Customer_Type> customer_Types = new ArrayList<>();

    @Autowired
    Customer_Type_Repository customer_Type_Repository = new Customer_Type_Repository();

    @Override
    public ArrayList<Customer_Type> getAll() {
        this.customer_Types = customer_Type_Repository.getAll();
        if (!customer_Types.isEmpty()) {
            return customer_Types;
        }
        return null;
    }

    @Override
    public Customer_Type getById(int id) {
        if (customer_Type_Repository.getById(id) != null) {
            return customer_Type_Repository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(Customer_Type customer_Type) {
        if (customer_Type_Repository.update(customer_Type)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Customer_Type customer_Type) {
        if (customer_Type_Repository.add(customer_Type)) {
            return true;
        }
        return false;
    }

}
