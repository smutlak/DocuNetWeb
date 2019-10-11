/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msi.irondndwebservice;

import javax.jws.WebService;

/**
 *
 * @author smutlak
 */
@WebService(endpointInterface = "com.msi.irondndwebservice.DndService")
public class DndServiceImpl implements DndService {

    @Override
    public String checkCall(String input) {
        return "Time on server:" + (new java.util.Date()) + "\n" + "Your input:" + input;
    }

    @Override
    public Long getDocumentPagesCount(String serverName, Integer port, String userName, String pass, String domain, Long docId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer SetDocumentIndicesValues(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Long docId, String indicesValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long createDocument(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Integer parentID, String docName, String docDesc, String indicesValues, String pages) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
