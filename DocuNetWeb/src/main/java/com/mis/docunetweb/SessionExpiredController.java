/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author smutlak
 */
@ManagedBean
@RequestScoped
public class SessionExpiredController {

    /**
     * Creates a new instance of SessionExpiredController
     */
    private String msg;
    public SessionExpiredController() {
        msg = "إنتهاء الجلسة، يجب إعادة الطلب";
    }

    public String getMsg() {
        System.out.println("SessionExpiredController.getMsg time=" + new java.util.Date());
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
