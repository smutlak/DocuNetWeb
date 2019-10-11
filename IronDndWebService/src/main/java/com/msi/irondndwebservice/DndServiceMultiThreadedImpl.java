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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.xml.ws.Endpoint;

public class DndServiceMultiThreadedImpl {

    public static void main(String[] args) {
        int port = 10000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ExecutorService es = Executors.newFixedThreadPool(5);
        Endpoint ep = Endpoint.create(new DndServiceImpl());
        ep.setExecutor(es);
        System.out.println("DND client service(multiple connection) is listening on port:"+port);
        ep.publish("http://127.0.0.1:"+port+"/DndService");
    }
}
