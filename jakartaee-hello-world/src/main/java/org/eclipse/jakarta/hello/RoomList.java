package org.eclipse.jakarta.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class RoomList
 */
public class RoomList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // We can able to get the form data by means of the below ways. 
        // Form arguments should be matched and then only they are recognised
        // login.jsp component names should match and then only
          // by using request.getParameter, it is matched
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String name = request.getParameter("name");
        String roomType = request.getParameter("roomtype");
        // To verify whether entered data is printing correctly or not
        System.out.println("checkin" + checkin);
        System.out.println("checkout.." + checkout);
        System.out.println("name.." + name);
        System.out.println("roomType.." + roomType);
        // Here the business validations goes. As a sample, 
          // we can check against a hardcoded value or pass 
          // the values into a database which can be available in local/remote  db
        // For easier way, let us check against a hardcoded value
        if (checkin != null && checkout != null) {
            // We can redirect the page to a welcome page
            // Need to pass the values in session in order 
              // to carry forward that one to next pages
            HttpSession httpSession = request.getSession();
            // By setting the variable in session, it can be forwarded
            httpSession.setAttribute("name", name);
            httpSession.setAttribute("checkout", checkout);
            httpSession.setAttribute("checkin", checkin);
            httpSession.setAttribute("roomType", roomType);
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
        }
    }

}
