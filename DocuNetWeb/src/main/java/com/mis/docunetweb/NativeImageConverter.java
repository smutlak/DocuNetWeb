/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

/**
 *
 * @author smutlak
 */
public class NativeImageConverter {

    static {
        try {
//            System.load("C:/DocuNetDMS/Ltfil15u.dll");
//            System.load("C:/DocuNetDMS/Ltkrn15u.dll");
//            System.load("C:/DocuNetDMS/DndConvert.dll");
            System.loadLibrary("dndconvert2"); // This works too if the dll is in the Java Build Path
        } catch (UnsatisfiedLinkError e) {
            System.out.println("Native code library failed to load.");
            e.printStackTrace();
        }
    }

    public native void Convert(String imageFile, int quality);
}
