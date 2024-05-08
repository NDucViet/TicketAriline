package com.ArilineTicket.ArilineTicket.Model.Repository;

import java.math.BigDecimal;
import java.sql.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ArilineTicket.ArilineTicket.Model.BaseConnection;
import com.ArilineTicket.ArilineTicket.Model.Entity.Customer_Type;
import com.ArilineTicket.ArilineTicket.Model.Entity.User;

import jakarta.el.ELException;

@Repository
public class User_Repository {
    ArrayList<User> userList = new ArrayList<>();
    @Autowired
    Customer_Type_Repository customer_Type_Repository = new Customer_Type_Repository();

    public ArrayList<User> getAll() {
        try {
            userList.clear();
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            Statement stsm = con.createStatement();
            ResultSet rs = stsm.executeQuery("select * from registerAirFlight.user");
            while (rs.next()) {
                int userID = rs.getInt("user_id");
                String email = rs.getString("email");
                String userName = rs.getString("user_name");
                Date birth = rs.getDate("birth");
                String address = rs.getString("address");
                int gender = rs.getInt("gender");
                String phone = String.valueOf(rs.getString("phone"));
                String avatar = rs.getString("avatar");
                Customer_Type customer_Type = customer_Type_Repository.getById(rs.getInt("customer_type_id"));
                String user_role = rs.getString("user_role");
                BigDecimal user_points = rs.getBigDecimal("user_points");
                String passport = rs.getString("passport");
                String password = rs.getString("password");
                String nation = rs.getString("nation");
                User user = new User(userID, email, userName, birth, address, gender, phone, avatar, customer_Type,
                        user_role, user_points, passport, password, nation);
                userList.add(user);
            }
            con.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return userList;
    }

    public User getById(int id) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection conn = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement st = conn.prepareStatement(
                    "select * from registerAirFlight.user where registerAirFlight.user.user_id = ?;");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) {
                throw new ELException("Cannot find");
            }
            String email = rs.getString("email");
            String userName = rs.getString("user_name");
            Date birth = rs.getDate("birth");
            String address = rs.getString("address");
            int gender = rs.getInt("gender");
            String phone = String.valueOf(rs.getString("phone"));
            String avatar = rs.getString("avatar");
            Customer_Type customer_Type = customer_Type_Repository.getById(rs.getInt("customer_type_id"));
            String user_role = rs.getString("user_role");
            BigDecimal user_points = rs.getBigDecimal("user_points");
            String passport = rs.getString("passport");
            String password = rs.getString("password");
            String nation = rs.getString("nation");
            User user = new User(id, email, userName, birth, address, gender, phone, avatar, customer_Type, user_role,
                    user_points, passport, password, nation);
            st.close();
            return user;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean update(User user) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "update registerAirFlight.user set registerAirFlight.user.email =?, registerAirFlight.user.user_name=?, registerAirFlight.user.birth = ?, registerAirFlight.user.address =?, registerAirFlight.user.gender =?, registerAirFlight.user.phone =?, registerAirFlight.user.avatar =?, registerAirFlight.user.customer_type_id =?, registerAirFlight.user.user_points = ?, registerAirFlight.user.passport =?, registerAirFlight.user.nation =?, registerAirFlight.user.password = ? where registerAirFlight.user.user_id =?");
            prsm.setString(1, user.getEmail());
            prsm.setString(2, user.getUser_name());
            prsm.setDate(3, user.getBirth());
            prsm.setString(4, user.getAddress());
            prsm.setInt(5, user.getGender());
            prsm.setString(6, user.getPhone());
            prsm.setString(7, user.getAvatar());
            prsm.setInt(8, user.getCustomer_Type().getTypeId());
            prsm.setBigDecimal(9, user.getUser_point());
            prsm.setString(10, user.getPassport());
            prsm.setString(12, user.getPassword());
            prsm.setString(11, user.getNation());
            prsm.setInt(13, user.getUser_id());
            System.out.println(user.toString());
            int result = prsm.executeUpdate();
            System.out.println(result);
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
            // TODO: handle exception
        }
        return false;
    }

    public boolean add(User user) {
        try {
            Class.forName(BaseConnection.nameClass);
            Connection con = DriverManager.getConnection(BaseConnection.url, BaseConnection.username,
                    BaseConnection.password);
            PreparedStatement prsm = con.prepareStatement(
                    "insert into registerAirFlight.user (email, user_name, birth, address, gender, phone, avatar, customer_type_id, user_points, passport, nation, password, user_role) values(?,?,?,?,?,?,?,?,?,?,?,?, ?)");
            prsm.setString(1, user.getEmail());
            prsm.setString(2, user.getUser_name());
            prsm.setDate(3, user.getBirth());
            prsm.setString(4, user.getAddress());
            prsm.setInt(5, user.getGender());
            prsm.setString(6, user.getPhone());
            prsm.setString(7, user.getAvatar());
            prsm.setInt(8, user.getCustomer_Type().getTypeId());
            prsm.setBigDecimal(9, user.getUser_point());
            prsm.setString(10, user.getPassport());
            prsm.setString(12, user.getPassword());
            prsm.setString(11, user.getNation());
            prsm.setString(13, user.getUser_role());
            int result = prsm.executeUpdate();
            con.close();
            return result > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

}
