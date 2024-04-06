package org.eclipse.jakarta.hello;

import java.io.IOException;
import java.io.PrintWriter;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // From login.jsp, as a post method only the credentials are passed
    // Hence the parameters should match both in jsp and servlet and 
      // then only values are retrieved properly
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // We can able to get the form data by means of the below ways. 
        // Form arguments should be matched and then only they are recognised
        // login.jsp component names should match and then onl
    	String roomType = request.getParameter("roomtype");
          // by using request.getParameter, it is matched
    	HttpSession httpSession = request.getSession();
    	httpSession.setAttribute("roomType", roomType);
            request.getRequestDispatcher("Confirmation.jsp").forward(request, response);
        }

}