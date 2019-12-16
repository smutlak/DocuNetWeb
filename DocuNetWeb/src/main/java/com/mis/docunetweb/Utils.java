/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mis.docunetweb;

import com.itextpdf.text.DocumentException;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFDecodeParam;


/**
 *
 * @author smutlak
 */
public class Utils {
    
    public static void TiffToJpg(String tiff, String output)
            throws IOException, DocumentException {
        // PDF conversation starts here
        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(output);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        writer.open();
        document.open();
        String fileName = tiff;
        Iterator readers = javax.imageio.ImageIO
                .getImageReadersBySuffix("tiff");
        if (readers.hasNext()) {
            File fi = new File(fileName);
            ImageInputStream iis = javax.imageio.ImageIO
                    .createImageInputStream(fi);
            TIFFDecodeParam param = null;
            ImageDecoder dec = ImageCodec.createImageDecoder("tiff", fi, param);
            int pageCount = dec.getNumPages();
            ImageReader _imageReader = (ImageReader) (readers.next());
            if (_imageReader != null) {
                _imageReader.setInput(iis, true);
                int count = 1;
                for (int i = 0; i < pageCount; i++) {
                    BufferedImage bufferedImage = _imageReader.read(i);
                    BufferedImage img2 = new BufferedImage(
                            bufferedImage.getWidth(),
                            bufferedImage.getHeight(),
                            BufferedImage.TYPE_INT_RGB);
                    // Set the RGB values for converted image (jpg)
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        for (int x = 0; x < bufferedImage.getWidth(); x++) {
                            img2.setRGB(x, y, bufferedImage.getRGB(x, y));
                        }
                    }
                    System.out.println("Page: " + (i + 1));
                    // Set the RGB values for converted image (jpg)
                   //To image
                      /*  String s = "C:/Users/TIFF/tiff"+i+".jpg";
                    ImageIO.write(img2, "jpg", new File(s));*/
                    //To pdf
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(img2, "jpg", baos);
                    baos.flush();
                    // Convert byteArrayoutputSteam to ByteArray
                    byte[] imageInByte = baos.toByteArray();
                    document.add(Image.getInstance(imageInByte));
                    baos.close();
                }
                document.close();
            }
        }
    }
}
