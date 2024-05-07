package com.entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/regForm")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String fullName = request.getParameter("uname");
        String branch = request.getParameter("branch");
        String year = request.getParameter("year");
        String phoneNumber = request.getParameter("phoneno");
        String email = request.getParameter("emailid");
        String eventName = request.getParameter("eventname");
        String gender = request.getParameter("gender");
        String photo = request.getParameter("pic");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Socities", "root", "root");

            PreparedStatement statement = conn.prepareStatement("INSERT INTO arrohan_event_registration (uname, branch, year, phoneno, emailid, eventname, gender, pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
          
            statement.setString(1, fullName);
            statement.setString(2, branch);
            statement.setString(3, year);
            statement.setString(4, phoneNumber);
            statement.setString(5, email);
            statement.setString(6, eventName);
            statement.setString(7, gender);
            statement.setString(8, photo);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                response.setContentType("text/html");
                out.print("<h3 style='color:green'>User Registration Successfully</h3>");
            } else {
                response.setContentType("text/html");
                out.print("<h3 style='color:red'>User Registration Failed</h3>");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                // Handle duplicate entry error
                response.setContentType("text/html");
                out.print("<h3 style='color:red'>Duplicate entry found. Please provide unique data.</h3>");
            } else {
                // Handle other integrity constraint violations
                response.setContentType("text/html");
                out.print("<h3 style='color:red'>Integrity constraint violation occurred.</h3>");
            }
        } catch (SQLException e) {
            // Handle other SQL exceptions
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            out.print("<h3 style='color:red'>Exception Occurred: " + e.getMessage() + "</h3>");
        }
    }
}
