/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msi.irondndwebservice;

import com.invest4all.inclient.OraFrmClient;
import javax.jws.WebService;

/**
 *
 * @author smutlak
 */
@WebService(endpointInterface = "com.msi.irondndwebservice.DndService")
public class DndServiceImpl implements DndService {

    @Override
    public String checkCall(String input) {
        return "Time on server:" + (new java.util.Date()) + "\n" + "Your input:" + input + "Possible Error Codes \n </br>"
                + "public static final int CLIENT_ALREADY_LOGGED_IN = -99;\n"
                + "	public static final int AUTHENTICATION_EXC = -100;\n"
                + "	public static final int REMOTE_EXC = -101;\n"
                + "	public static final int MALFORMED_URL_EXC = -102; \n"
                + "	public static final int INVALID_SEARCH_EXC = -103;\n"
                + "	public static final int INVALID_ATTR_VAL_EXC = -104;\n"
                + "	public static final int INVALID_NAME_EXC = -105;\n"
                + "	public static final int INVALID_COMMUNICATION_EXC = -106;\n"
                + "	public static final int NAME_NOT_FOUND_EXC = -107;\n"
                + "	public static final int NOT_BOUND_EXC = -108;\n"
                + "	public static final int ALREADY_BOUND_EXC = -109;\n"
                + "	public static final int IO_EXC = -110;\n"
                + "	public static final int SQL_EXC = -111;\n"
                + "	public static final int UNAUTH_USER_EXC = -112;\n"
                + "	public static final int NAMING_EXC = -113;\n"
                + "	public static final int EXCEPTION = -114;\n"
                + "	public static final int NO_DOC_TYPES = -115;\n"
                + "	public static final int DOC_TYPE_NOT_FOUND = -116;\n"
                + "	public static final int INVALID_INDICES = -117;\n"
                + "	public static final int DB_IS_DISCONNECTED_EXC = -118;\n"
                + "	public static final int OP_SUCCESS = 0;\n"
                + "	public static final int INVALID_USERNAME_PASS = -120;\n"
                + "	public static final int SERVER_NOT_FOUND = -121;\n"
                + "	public static final int INVLID_INDICES_COUNT = -122;\n"
                + "	public static final int NULL_PTR_EXC = -123;";
    }

    @Override
    public Long getDocumentPagesCount(String serverName, Integer port, String userName, String pass, String domain, Long docId) {
        int nRet = OraFrmClient.client_LogIn(serverName, port, userName, pass, domain);
        if (nRet == OraFrmClient.CLIENT_ALREADY_LOGGED_IN) {
            OraFrmClient.client_LogOut();
            nRet = OraFrmClient.client_LogIn(serverName, port, userName, pass, domain);
        }
        if (nRet <= 0) {
            return new Long(nRet);
        }

        Long pagesCount = OraFrmClient.getDocumentPagesCount(docId);
        OraFrmClient.client_LogOut();
        return pagesCount;
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
