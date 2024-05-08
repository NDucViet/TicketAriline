package com.ArilineTicket.ArilineTicket.Model.Entity;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int user_id;
    private String email;
    private String user_name;
    private Date birth;
    private String address;
    private int gender;
    private String phone;
    private String avatar;
    private Customer_Type customer_Type;
    private String user_role;
    private BigDecimal user_point;
    private String passport;
    private String password;
    private String nation;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        User person = (User) obj;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
