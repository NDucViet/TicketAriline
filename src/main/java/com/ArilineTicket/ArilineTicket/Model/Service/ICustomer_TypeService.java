package com.ArilineTicket.ArilineTicket.Model.Service;

import java.util.ArrayList;

import com.ArilineTicket.ArilineTicket.Model.Entity.Customer_Type;

public interface ICustomer_TypeService {
    public ArrayList<Customer_Type> getAll();

    public Customer_Type getById(int id);

    public boolean update(Customer_Type customer_Type);

    public boolean add(Customer_Type customer_Type);
}
