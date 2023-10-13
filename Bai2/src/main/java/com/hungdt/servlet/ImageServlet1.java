package com.hungdt.servlet;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


import javax.imageio.ImageIO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Image1")
public class ImageServlet1 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đọc ảnh từ resources
        String imagePath = "D:\\JAVA TECHNOLOGY\\Lab Resolve\\Lab4\\Bai2\\src\\main\\resources\\hoang.png"; // Provide the path to the desired image file
        response.setContentType("image/png");
        InputStream inputStream = Files.newInputStream(Paths.get(imagePath));
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            response.getOutputStream().write(buffer, 0, bytesRead);
        }
        inputStream.close();

    }
}
