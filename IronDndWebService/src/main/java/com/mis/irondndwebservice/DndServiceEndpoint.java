/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.irondndwebservice;

import java.io.File;
import javax.xml.ws.Endpoint;

/**
 *
 * @author smutlak
 */
public class DndServiceEndpoint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Endpoint endPoint = Endpoint.create(new DndServiceImpl());
//        endPoint.publish("http://127.0.0.1:10000/DndService"); //supply your desired url to the publish method to actually expose the service.

        int port = 10000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        String TEMP_DIR = System.getProperty("java.io.tmpdir") + "\\DND\\";
        File tempFolder = new File(TEMP_DIR);
        if (tempFolder.exists()) {
            deleteRecursive(tempFolder);
        }
        Endpoint endPoint = Endpoint.create(new DndServiceImpl());
        System.out.println("DND client service(single connection) is listening on port:" + port);
        endPoint.publish("http://127.0.0.1:" + port + "/DndService");
    }

    public static void deleteRecursive(File path) {
        File[] c = path.listFiles();
        System.out.println("Cleaning out folder:" + path.toString());
        for (File file : c) {
            if (file.isDirectory()) {
                System.out.println("Deleting file:" + file.toString());
                deleteRecursive(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        path.delete();
    }

}
