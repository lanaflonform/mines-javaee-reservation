package org.emn.javaee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.emn.javaee.crud.UserCrud;
import org.emn.javaee.models.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserCrud crud;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        this.crud = new UserCrud();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path.startsWith("/delete"))
		{
			handleDeletion(request);
		}
		handleFullOrFilteredList(request);
		request.setAttribute("page", "template/manager.jsp");
		request.setAttribute("entity", "users");
		request.setAttribute("title", "Utilisateurs");
		request.setAttribute("creationMode", "/new".equals(path));
		request.getRequestDispatcher("/pages/main.jsp").forward(request, response);
	}

	/**
	 * Deletes a user
	 * @param request
	 */
	private void handleDeletion(HttpServletRequest request) {
		try {
			int id = Integer.valueOf(request.getParameter("id"));
			this.crud.remove(id);
		}
		catch(Exception e)
		{
			
		}
	}
	
	/**
	 * Retrieve the full list of users or the filtered list of users based on parameters past as GET
	 * @param request
	 */
	private void handleFullOrFilteredList(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Boolean administrator = "on".equals(request.getParameter("administrator"));
		if( firstName != null || lastName != null || administrator)
		{
			request.setAttribute("users", crud.findMT(lastName, firstName, administrator));
		}
		else
		{
			request.setAttribute("users", crud.findAll());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleFormUser(request);
		// redirect after a post to avoid sending again the same data
		response.sendRedirect(getServletContext().getContextPath() + request.getServletPath());
	}
	
	/**
	 * If a form has been submitted to create a user, we handle it here
	 * @param request
	 */
	private void handleFormUser(HttpServletRequest request) {
		if(request.getParameterValues("firstName") != null)
		{
			System.out.println("form envoy�");
			User user = new User();
			user.setFirstName(request.getParameterValues("firstName")[0]);
			user.setLastName(request.getParameterValues("lastName")[0]);
			user.setPhone(request.getParameterValues("phone")[0]);
			user.setMail(request.getParameterValues("mail")[0]);
			user.setLogin(request.getParameterValues("login")[0]);
			user.setPassword(request.getParameterValues("password")[0]);
			user.setIsAdmin(false);
			this.crud.create(user);
		}
	}

}