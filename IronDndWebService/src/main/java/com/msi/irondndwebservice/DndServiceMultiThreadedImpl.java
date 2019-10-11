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
        ExecutorService es = Executors.newFixedThreadPool(5);
        Endpoint ep = Endpoint.create(new DndServiceImpl());
        ep.setExecutor(es);
        ep.publish("http://127.0.0.1:10000/DndService");
    }
}
