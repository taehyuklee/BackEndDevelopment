package com.ppt.converter.makeslides.functions;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PutImage {

    public void putImage() throws Exception {

        //새로운 Powerpoint 인스턴스를 만든다. Create a new PowerPoint presentation
        XMLSlideShow ppt = new XMLSlideShow();

        //Slide를 만든다. Create a slide
        XSLFSlide slide = ppt.createSlide();

        //이미지 경로파일을 가져온다, Specify the image file path
        String imagePath = "C:/Users/11464/Desktop/jmp pdf/jml기능.JPG";

        // Load the image into a byte array
        byte[] pictureData = loadImage(imagePath);

        // Add the picture to the presentation
        XSLFPictureData pd = ppt.addPicture(pictureData, XSLFPictureData.PictureType.JPEG);


        // Create a picture shape
        XSLFPictureShape picture = slide.createPicture(pd);

        // Set position and size of the picture
        picture.setAnchor(new java.awt.Rectangle(100, 100, 400, 300));

        // Save the presentation
        FileOutputStream out = new FileOutputStream("C:/Users/11464/Desktop/output.pptx");
        ppt.write(out);
        out.close();

        System.out.println("Presentation created successfully");

    }

    // Load an image from file path
    private static byte[] loadImage(String imagePath) throws Exception {
        FileInputStream fis = new FileInputStream(imagePath);
        byte[] pictureData = new byte[fis.available()];
        fis.read(pictureData);
        fis.close();
        return pictureData;
    }
}
