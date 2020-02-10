/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
        
        System.out.println("sessionDestroyed time=" + new java.util.Date());
        
        String DOCUNET_DOCUMENTS_PATH_TEMP ="";
        try {
             DOCUNET_DOCUMENTS_PATH_TEMP = (String) (new InitialContext().lookup("java:comp/env/DOCUNET_DOCUMENTS_PATH"));
        } catch (NamingException ex) {
            Logger.getLogger(DndWebHttpSessionListener.class.getName()).log(Level.SEVERE,
                    "Exception caught", ex);
        }
        
        final String DOCUNET_DOCUMENTS_PATH = DOCUNET_DOCUMENTS_PATH_TEMP;
        String DndID_temp = "";
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Object obj = facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "documentController");

        //Object obj = se.getSession().getAttribute("documentController");
        System.out.println(" Object=" + obj);
        if (obj instanceof DocumentController) {
            DocumentController docController = (DocumentController) obj;
            DndID_temp = docController.getDndID();
        }
        if (DndID_temp != null && !DndID_temp.isEmpty()) {
            System.out.println("Start Deleting " + DndID_temp);
            final String DndID = DndID_temp;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                @Override
                public void run() {
                    if (DOCUNET_DOCUMENTS_PATH != null && !DOCUNET_DOCUMENTS_PATH.isEmpty()) {
                        Utils.deleteRecursive(new File(DOCUNET_DOCUMENTS_PATH+File.separator+DndID));
                    }
                }
            },
                    5000
            );
        } else {
            System.out.println("Invalid DndID");
        }
        
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
