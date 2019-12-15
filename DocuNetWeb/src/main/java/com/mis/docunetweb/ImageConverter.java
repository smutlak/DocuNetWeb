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
import com.sun.media.jai.codec.JPEGEncodeParam;
import com.sun.media.jai.codec.PNGEncodeParam;
import com.sun.media.jai.codec.SeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.SubsampleAverageDescriptor;

/**
 *
 * @author smutlak
 */
public class ImageConverter {

    public static InputStream convertTiffToPng(String filename, Integer screenWidth) throws Exception {
        SeekableStream s = new FileSeekableStream(filename);
        TIFFDecodeParam param = null;
        ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
        RenderedImage op = dec.decodeAsRenderedImage(0);
        System.out.println("imageWidth=" + op.getWidth() + "imageHight=" + op.getHeight());
        if (screenWidth != null && screenWidth > 0 && (screenWidth-100)<op.getWidth()) {
            Double dScale = screenWidth/(double)op.getWidth();
            op = scale(op, dScale);
            System.out.println("New *** imageWidth=" + op.getWidth() + "imageHight=" + op.getHeight());
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        PNGEncodeParam params = PNGEncodeParam.getDefaultEncodeParam(op);
        //params.setQuality(67);
        ImageEncoder en = ImageCodec.createImageEncoder("png", os, params);
        en.encode(op.getData(), op.getColorModel());
        os.flush();
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return is;

    }
    
    public static InputStream convertTiffToJpg(String filename, Integer screenWidth) throws Exception {
        SeekableStream s = new FileSeekableStream(filename);
        TIFFDecodeParam param = null;
        ImageDecoder dec = ImageCodec.createImageDecoder("tiff", s, param);
        RenderedImage op = dec.decodeAsRenderedImage(0);
//        System.out.println("imageWidth=" + op.getWidth() + "imageHight=" + op.getHeight());
//        if (screenWidth != null && screenWidth > 0 && (screenWidth-100)<op.getWidth()) {
//            Double dScale = screenWidth/(double)op.getWidth();
//            op = scale(op, dScale);
//            System.out.println("New *** imageWidth=" + op.getWidth() + "imageHight=" + op.getHeight());
//        }


        ByteArrayOutputStream os = new ByteArrayOutputStream();

        //JPEGEncodeParam params = JPEGEncodeParam.getDefaultEncodeParam(op);
        JPEGEncodeParam jpgParam = new JPEGEncodeParam();
        //jpgParam.setQuality(0.75f);
        ImageEncoder en = ImageCodec.createImageEncoder("jpeg", os, jpgParam);
        en.encode(op);
        os.flush();
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return is;
//JAI.create("filestore", op, "d://temp//t1.jpg", "JPEG");


    }

    private static RenderedImage scale(RenderedImage image, double scaleFactor) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        RenderedOp resizeOp = SubsampleAverageDescriptor.create(image,
                scaleFactor, scaleFactor, hints);

        BufferedImage bufferedResizedImage = resizeOp.getAsBufferedImage();

        return bufferedResizedImage;
    }
}
