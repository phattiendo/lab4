package com.hungdt.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/Download")
public class DownloadServlet extends HttpServlet {
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB
    private static final int DEFAULT_SPEED_LIMIT = 1024; // 1KB/s
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file");
        if (fileName == null) {
            response.getWriter().println("File not found");
            return;
        }

        String filePath = "D:\\JAVA TECHNOLOGY\\Lab Resolve\\Lab4\\Bai2\\src\\main\\resources\\" + fileName;
        String speedParam = request.getParameter("speed");
        int speed = 0;
        if(speedParam != null)
            speed = Integer.parseInt(speedParam);

        // Set the content type and attachment header for file download
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

        // Set the buffer size based on the speed limit
        int bufferSize = Math.min(DEFAULT_BUFFER_SIZE, speed);

        try (
                InputStream inputStream = new BufferedInputStream(getServletContext().getResourceAsStream(filePath));
                OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        ) {
            byte[] buffer = new byte[bufferSize];
            int bytesRead;

            long startTime = System.currentTimeMillis();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);

                // Calculate the elapsed time
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                // Calculate the sleep time based on the speed limit and elapsed time
                long sleepTime = (1000 * bytesRead) / speed - elapsedTime;

                if (sleepTime > 0) {
                    try {
                        // Sleep for the required time to limit the download speed
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                startTime = System.currentTimeMillis();
            }
        }
    }
}
