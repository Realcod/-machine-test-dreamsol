import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteuserr")
public class deleteuser extends HttpServlet {

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
            PreparedStatement ps = con.prepareStatement("delete from users where email=?");

            ps.setString(1, em);
            int count = ps.executeUpdate();
            out.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
            if(count==1) {
                out.println("<h2 class='bg-danger text-light text-center'>Record Deleted Successfully</h2>");
            }else {
                out.println("<h2 class='bg-danger text-light text-center'>Record Not Deleted</h2>");
            }
           
        } catch (Exception e) {
            out.print("<h3>Error occurred: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
        out.println("<a href='index.jsp'><button class='btn btn-outline-success'>Home</button></a>");
        out.println("&nbsp; &nbsp;");
        out.println("</div>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Redirect to doGet() to handle GET requests
        doGet(req, resp);
    }
}
