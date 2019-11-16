/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.PNGEncodeParam;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author smutlak
 */
public class ImageConverter {

    public static InputStream convertTiff(String filename) throws Exception {
        SeekableStream s = new FileSeekableStream(filename);
        TIFFDecodeParam param = null;
        ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
        RenderedImage op = dec.decodeAsRenderedImage(0);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PNGEncodeParam params = PNGEncodeParam.getDefaultEncodeParam(op);
        //pngparam.setQuality(67);
        ImageEncoder en = ImageCodec.createImageEncoder("png", os, params);
        en.encode(op);
        os.flush();
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;

//        final BufferedImage tif = ImageIO.read(new File(filename));
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        ImageIO.write(tif, "png", os);
//        InputStream is = new ByteArrayInputStream(os.toByteArray());
//        return is;
    }

//    public static byte[] convert(byte[] tiff) throws Exception {
//
//        byte[] out = new byte[0];
//        InputStream inputStream = new ByteArrayInputStream(tiff);
//
//        TIFFDecodeParam param = null;
//
//        ImageDecoder dec = ImageCodec.createImageDecoder("tiff", inputStream, param);
//        RenderedImage op = dec.decodeAsRenderedImage(0);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        PNGEncodeParam jpgparam = null;
//        ImageEncoder en = ImageCodec.createImageEncoder("png", outputStream, jpgparam);
//        en.encode(op);
//        outputStream = (ByteArrayOutputStream) en.getOutputStream();
//        out = outputStream.toByteArray();
//        outputStream.flush();
//        outputStream.close();
//
//        return out;
//
//    }
}
