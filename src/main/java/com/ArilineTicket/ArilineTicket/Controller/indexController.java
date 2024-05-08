package com.ArilineTicket.ArilineTicket.Controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ArilineTicket.ArilineTicket.Model.Entity.User;
import com.ArilineTicket.ArilineTicket.Model.Service.Customer_TypeService;
import com.ArilineTicket.ArilineTicket.Model.Service.UserSerivce;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = { "", "/" })
public class indexController {

    @Autowired
    UserSerivce userSerivce = new UserSerivce();
    Customer_TypeService customer_TypeService = new Customer_TypeService();

    @GetMapping(value = "/index")
    public String index(Model model, User user) {

        model.addAttribute("user", user);
        return "hello";
    }

    @GetMapping(value = { "", "/" })
    public String showLogin(Model model, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        User user = new User();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userCookie".equals(cookie.getName())) {
                    String userStr = cookie.getValue();
                    // Xử lý logic với username
                    user = userSerivce.login(userStr);
                    model.addAttribute("user", user);
                    return index(model, user);
                }
            }
        }
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping(value = { "/showRegister" })
    public String showRegister(Model model, String mess) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("mess", mess);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(Model model, @ModelAttribute("user") User user1,
            @RequestParam(name = "passAgain") String pass, @RequestParam(name = "birth") String birth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate;
        try {
            utilDate = dateFormat.parse(birth);
            Date sqlDate = new Date(utilDate.getTime());
            user1.setBirth(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user1.setUser_role("user");
        user1.setCustomer_Type(customer_TypeService.getById(6));
        user1.setUser_id(0);
        user1.setUser_point(BigDecimal.valueOf(1.0));
        if (pass.equals(user1.getPassword())) {
            if (userSerivce.getInvalidAttributes(user1).isEmpty() || userSerivce.getInvalidAttributes(user1) == null) {
                if (userSerivce.add(user1)) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(90000) + 10000;
                    model.addAttribute("code", randomNumber);
                    userSerivce.sendMail(user1.getEmail(), "Code Login for you",
                            randomNumber + "");
                    return "nhanCode";
                } else {
                    return showRegister(model, "Đã Có Lỗi");
                }
            } else {
                ArrayList<String> errr = new ArrayList<>();
                errr = userSerivce.getInvalidAttributes(user1);
                String err = "";
                for (String loi : errr) {
                    err = err + " " + loi;
                }
                System.out.println(err);
                return showRegister(model, err);
            }
        } else {
            String mess = "passAgain";
            return showRegister(model, mess);
        }
    }

    @GetMapping(value = "/logOut")
    public String logOut(Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        User user = new User();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userCookie".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping(value = "/login")
    public String toLogin(@ModelAttribute("user") User user1, Model model,
            @RequestParam(value = "agree", required = false) Boolean rememberme,
            HttpServletResponse response,
            HttpServletRequest request) {
        User user = new User();

        user1.setAddress(null);
        user1.setAvatar(null);
        user1.setBirth(null);
        user1.setCustomer_Type(null);
        user1.setGender(0);
        user1.setNation(null);
        user1.setPassport(null);
        user1.setPhone(null);
        user1.setUser_name(null);
        user1.setUser_id(0);
        user1.setUser_point(null);
        user1.setUser_role(null);

        boolean flag = userSerivce.toLogin(user1);
        if (Boolean.TRUE.equals(rememberme)) {
            if (flag) {
                Cookie cookie = new Cookie("userCookie", user1.getEmail());
                user = userSerivce.login(user1.getEmail());
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                HttpSession session = request.getSession(true);
                session.setAttribute("userName", user.getUser_name());
                return index(model, user);
            } else {
                return showLogin(model, request);
            }
        } else if (!Boolean.TRUE.equals(rememberme)) {
            if (flag) {
                user = userSerivce.login(user1.getEmail());
                return index(model, user);
            }
        } else {
            user = userSerivce.login(user1.getEmail());
            return index(model, user);

        }
        return showLogin(model, request);
    }
}
