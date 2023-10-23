package com.abdullahacar.tableviewgadget.modules;

import com.abdullahacar.tableviewgadget.config.ApplicationBean;
import com.abdullahacar.tableviewgadget.modules.delivery.DeliveryService;
import com.abdullahacar.tableviewgadget.modules.login.LoginService;

public class Service {

    protected final ApplicationBean applicationBean;

    public Service() {
        this.applicationBean = ApplicationBean.getInstance();
    }


    public DeliveryService getDeliveryService() {

        return this.applicationBean.getDeliveryService();

    }


    public LoginService getLoginService() {

        return this.applicationBean.getLoginService();

    }

}
