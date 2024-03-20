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

@WebServlet("/showdata")
public class showusers extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>User Data</title></head><body>");
        out.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
        out.println("<marquee><h2 class='text-primary'>User Data</h2></marquee>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg", "root", "Yashhjain@123");
            PreparedStatement ps = con.prepareStatement("select name, mobileno, email, dob from users");

            ResultSet rs = ps.executeQuery();
            out.println("<div style='margin:auto;width:900px;margin-top:100px;'>");
            out.println("<table class='table table-hover table-striped'>");
            out.println("<tr>");
            out.println("<th>Name</th>");
            out.println("<th>Mobile No</th>");
            out.println("<th>Email</th>");
            out.println("<th>DOB</th>");
            out.println("<th>Edit</th>");
            out.println("<th>Delete</th>");
            out.println("</tr>");
            while(rs.next()) {
                out.println("<tr>");
                out.println("<td>"+rs.getString(1)+"</td>");
                out.println("<td>"+rs.getString(2)+"</td>");
                out.println("<td>"+rs.getString(3)+"</td>");
                out.println("<td>"+rs.getString(4)+"</td>");
                out.println("<td><a href='edituserr?email=" + rs.getString("email") + "'>Edit</a></td>");
                out.println("<td><a href='javascript:void(0);' onclick='confirmDelete(\"" + rs.getString("email") + "\")'>Delete</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</div>");

            // JavaScript function for confirmation
            out.println("<script>");
            out.println("function confirmDelete(email) {");
            out.println("if(confirm('Are you sure you want to delete this user?')) {");
            out.println("window.location.href = 'deleteuserr?email=' + email;");
            out.println("}");
            out.println("}");
            out.println("</script>");

        } catch (Exception e) {
            out.print("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Redirect to doGet() to handle GET requests
        doGet(req, resp);
    }
}
