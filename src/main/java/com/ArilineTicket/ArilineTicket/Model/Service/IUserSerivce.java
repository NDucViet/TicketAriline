package com.ArilineTicket.ArilineTicket.Model.Service;

import java.util.ArrayList;

import com.ArilineTicket.ArilineTicket.Model.Entity.User;

public interface IUserSerivce {
    public ArrayList<User> getAll();

    public User getById(int id);

    public boolean update(User user);

    public boolean add(User user);
}
