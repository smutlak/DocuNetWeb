/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author smutlak
 */
public class DndWebHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("DndWebHttpSessionListener::sessionCreated::sessionCreated time=" + new java.util.Date());
        //se.getSession().setMaxInactiveInterval(15 * 60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        System.out.println("DndWebHttpSessionListener::sessionDestroyed::sessionDestroyed time=" + new java.util.Date());
        
        String DOCUNET_DOCUMENTS_PATH_TEMP ="";
        try {
             DOCUNET_DOCUMENTS_PATH_TEMP = (String) (new InitialContext().lookup("java:comp/env/DOCUNET_DOCUMENTS_PATH"));
        } catch (NamingException ex) {
            Logger.getLogger(DndWebHttpSessionListener.class.getName()).log(Level.SEVERE,
                    "Exception caught", ex);
        }
        
        final String DOCUNET_DOCUMENTS_PATH = DOCUNET_DOCUMENTS_PATH_TEMP;
        String DndID_temp = "";

        Object obj = se.getSession().getAttribute("documentController");
        System.out.println("DndWebHttpSessionListener::sessionDestroyed:: Object=" + obj);
        if (obj instanceof DocumentController) {
            DocumentController docController = (DocumentController) obj;
            DndID_temp = docController.getDndID();
        }
        if (DndID_temp != null && !DndID_temp.isEmpty()) {
            System.out.println("DndWebHttpSessionListener::sessionDestroyed::Start Deleting " + DndID_temp);
            final String DndID = DndID_temp;
//            new java.util.Timer().schedule(
//                    new java.util.TimerTask() {
//                @Override
//                public void run() {
//                    if (DOCUNET_DOCUMENTS_PATH != null && !DOCUNET_DOCUMENTS_PATH.isEmpty()) {
//                        Utils.deleteRecursive(new File(DOCUNET_DOCUMENTS_PATH+File.separator+DndID));
//                    }
//                }
//            },
//                    5000
//            );
              Utils.deleteRecursive(new File(DOCUNET_DOCUMENTS_PATH+File.separator+DndID));
        } else {
            System.out.println("DndWebHttpSessionListener::sessionDestroyed::Invalid DndID");
        }
    }
}
