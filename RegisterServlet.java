import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String nm = req.getParameter("name");
        String em = req.getParameter("email");
        String mob = req.getParameter("mobileno");
        String dob = req.getParameter("dob");
        
        // Validation checks
        if (!isValidName(nm)) {
            out.print("<h3>Error: Name cannot contain special characters</h3>");
            return;
        }
        if (!isValidMobileNumber(mob)) {
            out.print("<h3>Error: Invalid mobile number</h3>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/reg", "root", "Yashhjain@123");
            PreparedStatement ps = con.prepareStatement("insert into users (name, mobileno, email, dob) values(?,?,?,?)");
            ps.setString(1, nm);
            ps.setString(2, mob);
            ps.setString(3, em);
            ps.setString(4, dob);
            int count = ps.executeUpdate();
            if (count > 0) {
                resp.setContentType("text/html");
                out.print("<h3>User Registered Successfully </h3>");
                RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
        	out.print("<h3>User Registered Successfully </h3>");
            e.printStackTrace();
        }
    }
    // Validation method for name
    private boolean isValidName(String name) {
        // Regular expression to allow only alphabets, spaces, and hyphens in the name
        String regex = "^[A-Za-z\\s\\-]+$";
        return Pattern.matches(regex, name);
    }

    // Validation method for mobile number
    private boolean isValidMobileNumber(String mobileNumber) {
        // Regular expression to check mobile number format (10 digits starting with 6, 7, 8, or 9)
        String regex = "^[6-9]\\d{9}$";
        return Pattern.matches(regex, mobileNumber);
    }
}
