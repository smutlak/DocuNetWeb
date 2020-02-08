/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author smutlak
 */
public class DndWebHttpSessionListener implements HttpSessionListener  {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println("sessionCreated");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /*Start 8-Feb-2020
        String DOCUNET_DOCUMENTS_PATH = null;
        try {
            DOCUNET_DOCUMENTS_PATH = (String) (new InitialContext().lookup("java:comp/env/DOCUNET_DOCUMENTS_PATH"));
        } catch (NamingException ex) {
            Logger.getLogger(DndWebHttpSessionListener.class.getName()).log(Level.SEVERE,
                    "Exception caught", ex);
        }
        if(DOCUNET_DOCUMENTS_PATH != null && !DOCUNET_DOCUMENTS_PATH.isEmpty()){
           File folder = new File(DOCUNET_DOCUMENTS_PATH);
           for (File childFolder : folder.listFiles()){
               deleteRecursive(childFolder);
           }
        }
    End 8-Feb-2020*/
    }
    
    private void deleteRecursive(File path) {
        /*Start 8-Feb-2020
        File[] c = path.listFiles();
        Logger.getLogger(DocumentController.class.getName()).log(Level.INFO,
                    "DocuNetWeb deleteRecursive", "Cleaning out folder:" + path.toString());
        System.out.println("Cleaning out folder:" + path.toString());
        for (File file : c) {
            if (file.isDirectory()) {
                Logger.getLogger(DocumentController.class.getName()).log(Level.INFO,
                    "DocuNetWeb deleteRecursive", "Deleting file:" + file.toString());
                System.out.println("Deleting file:" + file.toString());
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        path.delete();
    End 8-Feb-2020*/
    }
    
}
