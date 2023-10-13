package com.hungdt.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;



@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    private static final String UPLOAD_DIRECTORY = "uploads";
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList("txt", "doc", "docx", "img", "pdf", "rar", "zip");

    @Override
    public void init() throws ServletException {
        System.out.println("Starting");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String saveFileName = req.getParameter("name");

        if (saveFileName == null || saveFileName.isEmpty()) {
            out.println("<h3>Error: Please provide a file name.</h3>");
            return;
        }

        // Get the file part from the request
        Part filePart = req.getPart("file");

        // Get the file extension
        String fileExtension = getFileExtension(filePart);

        // Check if the file extension is supported
        if (!isSupportedExtension(fileExtension)) {
            out.println("<h3>Error: Unsupported file extension.</h3>");
            return;
        }

        // Get the upload directory path
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        // Create the upload directory if it doesn't exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Get the file name without the extension
        String fileName = getFileNameWithoutExtension(saveFileName);

        // Check if the file already exists
        File existingFile = new File(uploadPath + File.separator + saveFileName);
        boolean fileExists = existingFile.exists();
        boolean overrideIfExists = req.getParameter("override") != null;

        if (fileExists && !overrideIfExists) {
            out.println("<h3>Error: File already exists.</h3>");
            return;
        }

        // Save the file to the upload directory
        filePart.write(uploadPath + File.separator + saveFileName);

        if (fileExists && overrideIfExists) {
            out.println("<h3>File has been overridden.</h3>");
        } else {
            out.println("<h3>File has been uploaded.</h3>");
            System.out.println(existingFile.getName());
            System.out.println(existingFile.getAbsolutePath());
        }

        String downloadUrl = req.getContextPath() + File.separator + UPLOAD_DIRECTORY + File.separator + saveFileName;

        // Display the download link
        out.println("<a href='" + downloadUrl + "'>Click here to visit file</a>");




    }

    private String getFileExtension(Part part) {
        String fileName = part.getSubmittedFileName();
        return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    }


    // Helper method to check if the file extension is supported
    private boolean isSupportedExtension(String extension) {
        return SUPPORTED_EXTENSIONS.contains(extension);
    }

    // Helper method to get the file name without the extension
    private String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(0, dotIndex);
        }
        return fileName;
    }
}
