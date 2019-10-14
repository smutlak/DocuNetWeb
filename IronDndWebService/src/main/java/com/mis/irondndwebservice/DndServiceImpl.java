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
import com.invest4all.security.UnAuthenticatedUserException;
import com.invest4all.server.DataBaseIsDisconnectedException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import javax.jws.WebService;
import javax.management.InvalidAttributeValueException;
import javax.naming.CommunicationException;
import javax.naming.InvalidNameException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.security.sasl.AuthenticationException;
import javax.swing.JOptionPane;

/**
 *
 * @author smutlak
 */
@WebService(endpointInterface = "com.mis.irondndwebservice.DndService")
public class DndServiceImpl implements DndService {

    @Override
    public synchronized String checkCall(String input) {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        return "Time on server:" + (new java.util.Date()) + "\n </br>" + "Your input: " + input
                + "\n </br> OS current temporary directory is " + tempDir
                + "Possible Error Codes \n </br>"
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
    public synchronized Long getDocumentPagesCount(String serverName, Integer port, String userName, String pass, String domain, Long docId) {
        inClient c = null;
        Integer nRet = 0;
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet <= 0) {
            return new Long(nRet);
        }
        try {
            DocInfo docInfo = c.getDocumentInfo(docId);
            if (docInfo != null) {
                return new Long(docInfo.getPages());
            } else {
                return new Long(-1);
            }
        } catch (RemoteException e) {
            return new Long(REMOTE_EXC);
        } catch (SQLException e) {
            return new Long(SQL_EXC);
        } catch (DataBaseIsDisconnectedException e) {
            return new Long(DB_IS_DISCONNECTED_EXC);
        } finally {
            logOut(c);
        }
    }

    @Override
    public synchronized Integer SetDocumentIndicesValues(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Long docId, String indicesValues) {
        inClient c = null;
        Integer nRet = 0;
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet <= 0) {
            return nRet;
        }

        if (nRet <= 0) {
            return nRet;
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
            return REMOTE_EXC;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return SQL_EXC;
        } catch (DataBaseIsDisconnectedException e) {
            return DB_IS_DISCONNECTED_EXC;
        } catch (Exception e) {
            return EXCEPTION;
        } finally {
            logOut(c);
        }
    }

    @Override
    public synchronized Long createDocument(String serverName, Integer port, String userName, String pass, String domain, Integer docTypeID, Integer parentID, String docName, String docDesc, String indicesValues, String pages) {
        inClient c = null;
        Integer nRet = 0;
        c = logIn(serverName, port, userName, pass, domain, nRet);
        if (nRet <= 0) {
            return new Long(nRet);
        }

        if (nRet <= 0) {
            return new Long(nRet);
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
            return new Long(REMOTE_EXC);
        } catch (SQLException e) {
            return new Long(SQL_EXC);
        } catch (DataBaseIsDisconnectedException e) {
            return new Long(DB_IS_DISCONNECTED_EXC);
        } catch (RuntimeException e) {
            return new Long(SQL_EXC);
        } finally {
            logOut(c);
        }
    }

    @Override
    public Integer GetDocumentPages(String serverName, Integer port, String userName, String pass, String domain, Long docId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private inClient logIn(String serverName, int port, String userName, String pass, String domain, Integer error) {
        try {
            String tmp_pass = "";
            if (pass != null) {

                tmp_pass = pass;
            }
            inClient c = new inClient(serverName, port, userName, tmp_pass, domain);
            int nRet = c.login(serverName, port, userName, tmp_pass, domain);
            if (nRet == -2) {
                error = INVALID_USERNAME_PASS;
                return null;
            } else if (nRet == -1) {
                error = SERVER_NOT_FOUND;
                return null;
            } else if (nRet == -3) {
                error = DB_IS_DISCONNECTED_EXC;
                return null;
            } else {
                error = c.getDbId();
                return c;
            }

        } catch (AuthenticationException e) {
            error = AUTHENTICATION_EXC;
        } catch (RemoteException e) {
            error = REMOTE_EXC;
        } catch (MalformedURLException e) {
            error = MALFORMED_URL_EXC;
        } catch (InvalidAttributeValueException e) {
            error = INVALID_ATTR_VAL_EXC;
        } catch (InvalidSearchFilterException e) {
            error = INVALID_SEARCH_EXC;
        } catch (InvalidNameException e) {
            error = INVALID_NAME_EXC;
        } catch (CommunicationException e) {
            error = INVALID_COMMUNICATION_EXC;

        } catch (NameNotFoundException e) {
            error = NAME_NOT_FOUND_EXC;
        } catch (NotBoundException e) {
            error = NOT_BOUND_EXC;
        } catch (AlreadyBoundException e) {
            error = ALREADY_BOUND_EXC;
        } catch (IOException e) {
            error = SQL_EXC;
        } catch (SQLException e) {
            error = SQL_EXC;
        } catch (UnAuthenticatedUserException e) {
            error = UNAUTH_USER_EXC;
        } catch (NamingException e) {
            error = NAMING_EXC;
        } catch (Exception e) {
            error = EXCEPTION;
        }
        return null;
    }

    private Integer logOut(inClient client) {
        try {
            client.logout();
            client = null;
            return OP_SUCCESS;
        } catch (RemoteException e) {
            return REMOTE_EXC;
        } catch (SQLException e) {
            return SQL_EXC;
        } catch (IOException e) {
            return IO_EXC;
        } catch (NotBoundException e) {
            return NOT_BOUND_EXC;
        } catch (NullPointerException e) {
            return NULL_PTR_EXC;
        } catch (Exception e) {
            return EXCEPTION;
        }
    }

    private static String convertToCp(String sText) {
        try {
            byte[] Bytes = sText.getBytes("UTF16");
            byte[] Bytes2 = new byte[(Bytes.length - 2) / 2];
            int j = 0;
            for (int i = 2; i < Bytes.length; i++) {
                if (Bytes[i] != 0) {
                    Bytes2[j++] = Bytes[i];
                }
            }
            return new String(Bytes2, "Cp1256");
        } catch (java.io.UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, "UnsupportedEncodingException");
            return "";
        }
    }
}
