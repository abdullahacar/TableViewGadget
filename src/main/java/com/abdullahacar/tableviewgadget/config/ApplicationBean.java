package com.abdullahacar.tableviewgadget.config;

import com.abdullahacar.tableviewgadget.modules.delivery.DeliveryService;
import com.abdullahacar.tableviewgadget.modules.login.LoginService;

public class ApplicationBean {

    private String token;

    private static ApplicationBean instance;

    public static ApplicationBean getInstance() {
        if (instance == null) {
            instance = new ApplicationBean();
        }
        return instance;
    }

    protected ApplicationBean() {

        System.out.println("Application bean initialized !");

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    private volatile DeliveryService deliveryService;
    private volatile LoginService loginService;


    public DeliveryService getDeliveryService() {

        if (deliveryService == null) {
            synchronized (this) {
                if (deliveryService == null) {
                    deliveryService = new DeliveryService(getInstance());
                }
            }
        }
        return deliveryService;

    }

    public LoginService getLoginService() {
        if (loginService == null) {
            synchronized (this) {
                if (loginService == null) {
                    loginService = new LoginService(getInstance());
                }
            }
        }
        return loginService;
    }

}
