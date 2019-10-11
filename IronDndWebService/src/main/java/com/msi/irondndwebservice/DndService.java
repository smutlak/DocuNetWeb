/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.msi.irondndwebservice;

/**
 *
 * @author smutlak
 */
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC) //this annotation stipulates the style of your ws, document or rpc based. rpc is more straightforward and simpler. And old.
public interface DndService {

    @WebMethod
    String checkCall(@WebParam(name = "input") String input);

//    @WebMethod
//    Integer client_LogIn(@WebParam(name = "serverName") String serverName,
//            @WebParam(name = "port") Integer port,
//            @WebParam(name = "userName") String userName,
//            @WebParam(name = "pass") String pass,
//            @WebParam(name = "domain") String domain);

    @WebMethod
    Long getDocumentPagesCount(@WebParam(name = "serverName") String serverName,
            @WebParam(name = "port") Integer port,
            @WebParam(name = "userName") String userName,
            @WebParam(name = "pass") String pass,
            @WebParam(name = "domain") String domain,
            @WebParam(name = "docId") Long docId);

    @WebMethod
    Integer SetDocumentIndicesValues(@WebParam(name = "serverName") String serverName,
            @WebParam(name = "port") Integer port,
            @WebParam(name = "userName") String userName,
            @WebParam(name = "pass") String pass,
            @WebParam(name = "domain") String domain,
            @WebParam(name = "docTypeID") Integer docTypeID,
            @WebParam(name = "docId") Long docId,
            @WebParam(name = "indicesValues") String indicesValues);

    @WebMethod
    Long createDocument(@WebParam(name = "serverName") String serverName,
            @WebParam(name = "port") Integer port,
            @WebParam(name = "userName") String userName,
            @WebParam(name = "pass") String pass,
            @WebParam(name = "domain") String domain,
            @WebParam(name = "docTypeID") Integer docTypeID, 
            @WebParam(name = "parentID") Integer parentID, 
            @WebParam(name = "docName") String docName, 
            @WebParam(name = "docDesc") String docDesc, 
            @WebParam(name = "indicesValues") String indicesValues, 
            @WebParam(name = "pages") String pages);

//    @WebMethod
//    Integer client_LogOut();
}
