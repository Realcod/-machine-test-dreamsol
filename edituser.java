import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/edituserr")
public class edituser extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String em = req.getParameter("email");
        out.println("<html><head><title>User Data</title></head><body>");
        out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        out.println("<marquee><h2 class='text-primary'>User Data</h2></marquee>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg", "root", "Yashhjain@123");
            PreparedStatement ps = con.prepareStatement("select name, mobileno, email, dob from users where email=? ");
            ps.setString(1, em);
            ResultSet rs = ps.executeQuery();
            rs.next();
            out.println("<div style='margin:auto;width:500px;margin-top:100px;'>");
            out.println("<form id='editForm' action='edituserr' method='post'>");
            out.println("<table class='table table-hover table-striped'>");
            out.println("<tr>");
            out.println("<td>Name</td>");
            out.println("<td><input type='text' name='name' value='" + rs.getString(1) + "'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Mobile No</td>");
            out.println("<td><input type='text' name='mobileno' value='" + rs.getString(2) + "'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Email</td>");
            out.println("<td><input type='email' name='email' value='" + rs.getString(3) + "'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Date of Birth</td>");
            out.println("<td><input type='date' name='dob' value='" + rs.getString(4) + "'></td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td><button type='button' onclick='confirmEdit()' class='btn btn-outline-success'>Edit</button></td>");
            out.println("<td><button type='reset' class='btn btn-outline-danger'>Cancel</button></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");

            // JavaScript function for confirmation
            out.println("<script>");
            out.println("function confirmEdit() {");
            out.println("if(confirm('Are you sure you want to edit this user?')) {");
            out.println("document.getElementById('editForm').submit();");
            out.println("}");
            out.println("}");
            out.println("</script>");

        } catch (Exception e) {
            out.print("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
        out.println("<a href='index.jsp'><button class='btn btn-outline-success'>Home</button></a>");
        out.println("</div>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");
        String mobileno = req.getParameter("mobileno");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");

        // Validation checks
        if (!isValidMobileNumber(mobileno) || !isValidName(name)) {
            out.println("<h3>Invalid input provided. Please check your inputs.</h3>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg", "root", "Yashhjain@123");
            PreparedStatement ps = con.prepareStatement("update users set name=?, mobileno=?, dob=? where email=?");
            ps.setString(1, name);
            ps.setString(2, mobileno);
            ps.setString(3, dob);
            ps.setString(4, email);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                resp.sendRedirect("showdata"); // Redirect to showdata servlet
            } else {
                out.println("<h3>Failed to update user data.</h3>");
            }

        } catch (Exception e) {
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }

    // Method to validate mobile number
    private boolean isValidMobileNumber(String mobileno) {
        // Check if mobile number is 10 digits and starts with 9, 8, 7, or 6
        return mobileno.matches("[6-9]\\d{9}");
    }

    // Method to validate name
    private boolean isValidName(String name) {
        // Check if name contains only alphabets and spaces
        return name.matches("[a-zA-Z ]+");
    }
}
