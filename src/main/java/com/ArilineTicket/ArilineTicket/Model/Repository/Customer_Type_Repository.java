package com.ArilineTicket.ArilineTicket.Model.Repository;

import java.sql.*;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.ArilineTicket.ArilineTicket.Model.BaseConnection;
import com.ArilineTicket.ArilineTicket.Model.Entity.Customer_Type;

import jakarta.el.ELException;

@Repository
public class Customer_Type_Repository {
    ArrayList<Customer_Type> customer_TypeList = new ArrayList<>();

    public ArrayList<Customer_Type> getAll() {
        try {
            customer_TypeList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from registerAirFlight.customer_type");
            while (rs.next()) {
                int customer_Type_Id = rs.getInt("customer_type_id");
                String customer_Type_Name = rs.getString("customer_type_name");
                String customer_type_class = rs.getString("customer_type_class");
                Customer_Type customer_Type = new Customer_Type(customer_Type_Id, customer_Type_Name,
                        customer_type_class);
                customer_TypeList.add(customer_Type);
            }
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return customer_TypeList;
    }

    public Customer_Type getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from registerAirFlight.customer_type where registerAirFlight.customer_type.customer_type_id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            int customer_Type_Id = rs.getInt("customer_type_id");
            String customer_Type_Name = rs.getString("customer_type_name");
            String customer_type_class = rs.getString("customer_type_class");
            Customer_Type customer_Type = new Customer_Type(customer_Type_Id, customer_Type_Name,
                    customer_type_class);
            st.close();
            return customer_Type;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(Customer_Type customer_Type) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update registerAirFlight.customer_type set customer_type_name =?, customer_type_class=? where customer_type_id =?");
            prsm.setString(1, customer_Type.getTypeName());
            prsm.setString(2, customer_Type.getTypeClass());
            prsm.setInt(3, customer_Type.getTypeId());

            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean add(Customer_Type customer_Type) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into registerAirFlight.customer_type (customer_type_name, customer_type_class) values(?,?)");
            prsm.setString(1, customer_Type.getTypeName());
            prsm.setString(2, customer_Type.getTypeClass());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public static void main(String[] args) {
        Customer_Type_Repository c = new Customer_Type_Repository();
        System.out.println(c.getAll());
    }
}
