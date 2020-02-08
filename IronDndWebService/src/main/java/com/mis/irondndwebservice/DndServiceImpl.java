/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.irondndwebservice;

import static com.invest4all.inclient.OraFrmClient.ALREADY_BOUND_EXC;
import static com.invest4all.inclient.OraFrmClient.AUTHENTICATION_EXC;
import static com.invest4all.inclient.OraFrmClient.DB_IS_DISCONNECTED_EXC;
import static com.invest4all.inclient.OraFrmClient.DOC_TYPE_NOT_FOUND;
import static com.invest4all.inclient.OraFrmClient.EXCEPTION;
import static com.invest4all.inclient.OraFrmClient.INVALID_ATTR_VAL_EXC;
import static com.invest4all.inclient.OraFrmClient.INVALID_COMMUNICATION_EXC;
import static com.invest4all.inclient.OraFrmClient.INVALID_INDICES;
import static com.invest4all.inclient.OraFrmClient.INVALID_NAME_EXC;
import static com.invest4all.inclient.OraFrmClient.INVALID_SEARCH_EXC;
import static com.invest4all.inclient.OraFrmClient.INVALID_USERNAME_PASS;
import static com.invest4all.inclient.OraFrmClient.INVLID_INDICES_COUNT;
import static com.invest4all.inclient.OraFrmClient.IO_EXC;
import static com.invest4all.inclient.OraFrmClient.MALFORMED_URL_EXC;
import static com.invest4all.inclient.OraFrmClient.NAME_NOT_FOUND_EXC;
import static com.invest4all.inclient.OraFrmClient.NAMING_EXC;
import static com.invest4all.inclient.OraFrmClient.NOT_BOUND_EXC;
import static com.invest4all.inclient.OraFrmClient.NO_DOC_TYPES;
import static com.invest4all.inclient.OraFrmClient.NULL_PTR_EXC;
import static com.invest4all.inclient.OraFrmClient.OP_SUCCESS;
import static com.invest4all.inclient.OraFrmClient.REMOTE_EXC;
import static com.invest4all.inclient.OraFrmClient.SERVER_NOT_FOUND;
import static com.invest4all.inclient.OraFrmClient.SQL_EXC;
import static com.invest4all.inclient.OraFrmClient.UNAUTH_USER_EXC;
import com.invest4all.inclient.inClient;
import com.invest4all.incommon.DocInfo;
import com.invest4all.incommon.inDocument;
import com.invest4all.incommon.inDocumentType;
import com.invest4all.incommon.inIndex;
import com.invest4all.incommon.inPage;
import com.invest4all.security.UnAuthenticatedUserException;
import com.invest4all.server.DataBaseIsDisconnectedException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jws.WebService;
import javax.management.InvalidAttributeValueException;
import javax.naming.CommunicationException;
import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.security.sasl.AuthenticationException;

/**
 *
 * @author smutlak
 */
@WebService(endpointInterface = "com.mis.irondndwebservice.DndService")
public class DndServiceImpl implements DndService {

    static String TEMP_DIR = System.getProperty("java.io.tmpdir") + "\\DND\\";

    @Override
    public synchronized String checkCall(String input) {
        return "Time on server:" + (new java.util.Date()) + "   " + "Your input: " + input
                + "     OS current temporary directory is    " + TEMP_DIR
                + "     Possible Error Codes    "
                + "     public static final int CLIENT_ALREADY_LOGGED_IN = -99;     "
                + "     public static final int AUTHENTICATION_EXC = -100;  "
                + "     public static final int REMOTE_EXC = -101;  "
                + "     public static final int MALFORMED_URL_EXC = -102;   "
                + "     public static final int INVALID_SEARCH_EXC = -103;  "
                + "     public static final int INVALID_ATTR_VAL_EXC = -104;    "
                + "     public static final int INVALID_NAME_EXC = -105;    "
                + "     public static final int INVALID_COMMUNICATION_EXC = -106;   "
                + "     public static final int NAME_NOT_FOUND_EXC = -107;  "
                + "     public static final int NOT_BOUND_EXC = -108;   "
                + "     public static final int ALREADY_BOUND_EXC = -109;   "
                + "     public static final int IO_EXC = -110;  "
                + "     public static final int SQL_EXC = -111;     "
                + "     public static final int UNAUTH_USER_EXC = -112;     "
                + "     public static final int NAMING_EXC = -113;  "
                + "     public static final int EXCEPTION = -114;   "
                + "     public static final int NO_DOC_TYPES = -115;    "
                + "     public static final int DOC_TYPE_NOT_FOUND = -116;  "
                + "     public static final int INVALID_INDICES = -117;     "
                + "     public static final int DB_IS_DISCONNECTED_EXC = -118;  "
                + "     public static final int OP_SUCCESS = 0;     "
                + "     public static final int INVALID_USERNAME_PASS = -120;   "
                + "     public static final int SERVER_NOT_FOUND = -121;    "
                + "     public static final int INVLID_INDICES_COUNT = -122;    "
                + "     public static final int NULL_PTR_EXC = -123;";
    }

    @Override
    public synchronized Long getDocumentPagesCount(String serverName, Integer port, String userName, String pass, String domain, Long docId) {
        System.out.println("Starting getDocumentPagesCount..serverName=" + serverName + " port=" + port + " userName" + userName + " pass=" + pass + " domain=" + domain + " docId=" + docId);
        inClient c = null;
        AtomicInteger nRet = new AtomicInteger(0);
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet.get() <= 0) {
            System.out.println("Login was unsuccessful" + nRet);
            return new Long(nRet.get());
        }
        try {
            DocInfo docInfo = c.getDocumentInfo(docId);
            if (docInfo != null) {
                System.out.println("Document Pages count" + docInfo.getPages());
                return new Long(docInfo.getPages());
            } else {
                return new Long(-1);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return new Long(REMOTE_EXC);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Long(SQL_EXC);
        } catch (DataBaseIsDisconnectedException e) {
            e.printStackTrace();
            return new Long(DB_IS_DISCONNECTED_EXC);
        } finally {
            logOut(c);
        }
    }

    @Override
    public synchronized Integer SetDocumentIndicesValues(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Long docId, String indicesValues) {
        inClient c = null;
        AtomicInteger nRet = new AtomicInteger(0);
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet.get() <= 0) {
            System.out.println("Login was unsuccessful" + nRet);
            return nRet.get();
        }

        try {
            indicesValues = convertToCp(indicesValues);
            if ((indicesValues == null) || (indicesValues.trim().equals(""))) {
                return INVALID_INDICES;
            }

            Vector<inDocumentType> v = new Vector<inDocumentType>();
            c.getDocumentTypes(v);
            if (v.size() == 0) {
                return NO_DOC_TYPES;
            }

            inDocumentType doctype = null;
            for (inDocumentType inDocTp : v) {
                if (inDocTp.getID() == docTypeID) {
                    doctype = inDocTp;
                    break;
                }
            }

            if (doctype == null) {
                return DOC_TYPE_NOT_FOUND;
            }

            c.getDocTypeIndices(doctype);
            Vector<inIndex> indices = doctype.getIndices();
            if ((indices == null) || (indices.size() == 0)) {
                return INVALID_INDICES;
            }

            String[] indValsArray = indicesValues.split("\t");
            if (indices.size() != indValsArray.length) {
                return INVLID_INDICES_COUNT;
            }

            int i = 0;
            for (Iterator iterator = indices.iterator(); iterator.hasNext();) {
                inIndex name = (inIndex) iterator.next();
                name.setValue(indValsArray[i]);
                i++;
            }
            inDocument doc = new inDocument();
            c.getDocument(docId, doc);

            doc.setDocType(doctype);
            doc.setIndicesValues(indices);
            c.setDocumentIndicesValues(doc);
            return 1;
        } catch (RemoteException e) {
            e.printStackTrace();
            return REMOTE_EXC;
        } catch (SQLException e) {
            e.printStackTrace();
            return SQL_EXC;
        } catch (DataBaseIsDisconnectedException e) {
            e.printStackTrace();
            return DB_IS_DISCONNECTED_EXC;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        } finally {
            logOut(c);
        }
    }

    @Override
    public synchronized Long createDocument(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Integer parentID, String docName, String docDesc, String indicesValues, String pages) {
        inClient c = null;
        AtomicInteger nRet = new AtomicInteger(0);
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet.get() <= 0) {
            System.out.println("Login was unsuccessful" + nRet);
            return new Long(nRet.get());
        }

        try {
            indicesValues = convertToCp(indicesValues);

            if ((indicesValues == null) || (indicesValues.trim().equals(""))) {
                return new Long(INVALID_INDICES);
            }

            Vector<inDocumentType> v = new Vector<inDocumentType>();
            c.getDocumentTypes(v);
            if (v.size() == 0) {
                return new Long(NO_DOC_TYPES);
            }
            inDocumentType doctype = null;
            for (inDocumentType inDocTp : v) {
                if (inDocTp.getID() == docTypeID) {
                    doctype = inDocTp;
                    break;
                }
            }
            if (doctype == null) {
                return new Long(DOC_TYPE_NOT_FOUND);
            }

            c.getDocTypeIndices(doctype);
            Vector<inIndex> indices = doctype.getIndices();
            if ((indices == null) || (indices.size() == 0)) {
                return new Long(INVALID_INDICES);
            }
            String[] indValsArray = indicesValues.split("\t");
            if (indices.size() != indValsArray.length) {
                return new Long(INVLID_INDICES_COUNT);
            }
            int i = 0;
            for (Iterator iterator = indices.iterator(); iterator.hasNext();) {
                inIndex name = (inIndex) iterator.next();
                name.setValue(indValsArray[i]);
                i++;
            }

            inDocument doc = new inDocument();
            doc.setName(docName);
            doc.setParentID(parentID);
            doc.setDescription(docDesc);
            doc.setIsCompressed(false);
            doc.setIsCompressed(false);
            doc.setSaveMethod(1); // MEDIA SAVE METHOD

            doc.setDocType(doctype);
            doc.setIndicesValues(indices);
            return c.createDocument(doc);

        } catch (RemoteException e) {
            e.printStackTrace();
            return new Long(REMOTE_EXC);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Long(SQL_EXC);
        } catch (DataBaseIsDisconnectedException e) {
            e.printStackTrace();
            return new Long(DB_IS_DISCONNECTED_EXC);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Long(SQL_EXC);
        } finally {
            logOut(c);
        }
    }

    @Override
    public Integer GetDocumentPages(String serverName, Integer port, String userName, String pass, String domain, Long docId, String infoTabDelemeted) {
        inClient c = null;
        AtomicInteger nRet = new AtomicInteger(0);
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet.get() <= 0) {
            System.out.println("Login was unsuccessful" + nRet);
            return nRet.get();
        }

        try {
            /*
                CDndDocument* pDocInfo = new CDndDocument(server_id, doc_id, lVersionID);
		if(pDoc != NULL) pDocInfo->SetDocTypeID(pDoc->GetDocTypeID());

		CString sDocPath = _T("");
		sDocPath.Format(_T("%s\\%d\\%d\\"), theApp.TEMP_FOLDER, server_id, doc_id);
		CreateDirectory(sDocPath, NULL);
		sDocPath.AppendFormat(_T("%ld\\"), lVersionID);
		CreateDirectory(sDocPath, NULL);

		pDocInfo->SetCashFolder(sDocPath);
		pDocInfo->GetDocumentPages(sDocPath, TRUE);
             */
            inDocument doc = new inDocument();
            c.getDocument(docId, doc);

            String sDocPath = TEMP_DIR + '\\' + serverName.replaceAll("[\\\\/:*?\"<>|]/", "_") + '\\' + docId + '\\';
            File tempFolder = new File(sDocPath);
            if (tempFolder.exists()) {
                DndServiceEndpoint.deleteRecursive(tempFolder);
            }
            tempFolder.mkdirs();
            System.out.println("Document(" + docId + ") path=" + sDocPath);
            //sDocPath.Format(_T("%s\\%d\\%d\\"), theApp.TEMP_FOLDER, server_id, doc_id);
            c.getDocumentPages(doc, sDocPath);
            for (inPage page : doc.getPages()) {
                c.DownloadPage(doc, page, 1, sDocPath);
                //c.DownloadPage(doc, page, 2, sDocPath);
            }
            OutputStreamWriter writer
                    = new OutputStreamWriter(new FileOutputStream(sDocPath + '\\' + docId + "_info.txt"), StandardCharsets.UTF_8);

            writer.write(infoTabDelemeted);
            writer.flush();
            writer.close();

            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        } finally {
            logOut(c);
        }
    }

    private inClient logIn(String serverName, int port, String userName, String pass, String domain, AtomicInteger error) {
        System.out.println("Starting login..serverName=" + serverName + " port=" + port + " userName" + userName + " pass=" + pass + " domain=" + domain);
        try {
            String tmp_pass = "";
            if (pass != null) {
                if (pass.trim().length() > 0 && !pass.equalsIgnoreCase("null")) {
                    tmp_pass = pass;
                }
            }
            inClient c = null;
            c = new inClient(serverName, port, userName, tmp_pass, domain);
            int nRet = c.login(serverName, port, userName, tmp_pass, domain);
            switch (nRet) {
                case -2:
                    error.set(INVALID_USERNAME_PASS);
                    return null;
                case -1:
                    error.set(SERVER_NOT_FOUND);
                    return null;
                case -3:
                    error.set(DB_IS_DISCONNECTED_EXC);
                    return null;
                default:
                    error.set(c.getDbId());
                    System.out.println("Login user:" + error.get() + "client object" + c);
                    return c;
            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
            error.set(AUTHENTICATION_EXC);
        } catch (RemoteException e) {
            e.printStackTrace();
            error.set(REMOTE_EXC);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            error.set(MALFORMED_URL_EXC);
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
            error.set(INVALID_ATTR_VAL_EXC);
        } catch (InvalidSearchFilterException e) {
            e.printStackTrace();
            error.set(INVALID_SEARCH_EXC);
        } catch (InvalidNameException e) {
            e.printStackTrace();
            error.set(INVALID_NAME_EXC);
        } catch (CommunicationException e) {
            e.printStackTrace();
            error.set(INVALID_COMMUNICATION_EXC);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
            error.set(NAME_NOT_FOUND_EXC);
        } catch (NotBoundException e) {
            e.printStackTrace();
            error.set(NOT_BOUND_EXC);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
            error.set(ALREADY_BOUND_EXC);
        } catch (IOException e) {
            e.printStackTrace();
            error.set(SQL_EXC);
        } catch (SQLException e) {
            e.printStackTrace();
            error.set(SQL_EXC);
        } catch (UnAuthenticatedUserException e) {
            e.printStackTrace();
            error.set(UNAUTH_USER_EXC);
        } catch (NamingException e) {
            e.printStackTrace();
            error.set(NAMING_EXC);
        } catch (Exception e) {
            e.printStackTrace();
            error.set(EXCEPTION);
        }
        System.out.println("Ending login..");
        return null;
    }

    private Integer logOut(inClient client) {
        try {
            client.logout();
            client = null;
            return OP_SUCCESS;
        } catch (RemoteException e) {
            e.printStackTrace();
            return REMOTE_EXC;
        } catch (SQLException e) {
            e.printStackTrace();
            return SQL_EXC;
        } catch (IOException e) {
            e.printStackTrace();
            return IO_EXC;
        } catch (NotBoundException e) {
            e.printStackTrace();
            return NOT_BOUND_EXC;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return NULL_PTR_EXC;
        } catch (Exception e) {
            e.printStackTrace();
            return EXCEPTION;
        }
    }

    private static String convertToCp(String sText) {
//        try {
//            byte[] Bytes = sText.getBytes("UTF16");
//            byte[] Bytes2 = new byte[(Bytes.length - 2) / 2];
//            int j = 0;
//            for (int i = 2; i < Bytes.length; i++) {
//                if (Bytes[i] != 0) {
//                    Bytes2[j++] = Bytes[i];
//                }
//            }
//            return new String(Bytes2, "Cp1256");
//        } catch (java.io.UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return "";
//        }
        return sText;
    }
}
