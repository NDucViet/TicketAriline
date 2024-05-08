package com.ArilineTicket.ArilineTicket.Model.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ArilineTicket.ArilineTicket.Model.Entity.User;
import com.ArilineTicket.ArilineTicket.Model.Repository.User_Repository;

@Service
public class UserSerivce implements IUserSerivce {
    ArrayList<User> users = new ArrayList<>();

    private static final Predicate<String> EMAIL_VALIDATOR = email -> email
            .matches("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");

    private static final Predicate<String> PASSWORD_VALIDATOR = password -> password.length() >= 8;

    private static final Predicate<String> PHONE_VALIDATOR = phone -> phone.matches("^\\d{10}$");

    private static final Predicate<Date> BIRTH_VALIDATOR = birth -> {
        LocalDate birthDate = birth.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return !birthDate.isAfter(currentDate);
    };

    @Autowired
    User_Repository user_Repository = new User_Repository();

    public User login(String email) {
        getAll();
        for (User user2 : users) {
            if (user2.getEmail().equals(email)) {
                return user2;
            }
        }
        return null;
    }

    public boolean toLogin(User user) {
        getAll();

        for (User user2 : users) {
            if (user2.getEmail().equals(user.getEmail()) && user2.getPassword().equals(user.getPassword())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<User> getAll() {
        this.users = user_Repository.getAll();
        if (!users.isEmpty()) {
            return users;
        }
        return null;
    }

    @Override
    public User getById(int id) {
        if (user_Repository.getById(id) != null) {
            return user_Repository.getById(id);
        }
        return null;
    }

    @Override
    public boolean update(User user) {
        if (getInvalidAttributes(user).isEmpty()) {
            return user_Repository.update(user);
        } else {
            return false;
        }
    }

    public ArrayList<String> getInvalidAttributes(User user) {
        ArrayList<String> invalidAttributes = new ArrayList<>();
        HashSet<User> setList = new HashSet<>();
        setList.addAll(getAll());
        if (!setList.add(user)) {
            invalidAttributes.add("doublicate");
        }
        if (!EMAIL_VALIDATOR.test(user.getEmail())) {
            invalidAttributes.add("email");
        }
        if (!PASSWORD_VALIDATOR.test(user.getPassword())) {
            invalidAttributes.add("password");
        }
        if (!PHONE_VALIDATOR.test(user.getPhone())) {
            invalidAttributes.add("phone");
        }
        if (!BIRTH_VALIDATOR.test(user.getBirth())) {
            invalidAttributes.add("birth");
        }

        return invalidAttributes;
    }

    @Override
    public boolean add(User user) {
        if (getInvalidAttributes(user).isEmpty()) {
            return user_Repository.add(user);
        } else {
            return false;
        }
    }

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vietndde170616@fpt.edu.vn");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Sent successfully....");
    }
}
