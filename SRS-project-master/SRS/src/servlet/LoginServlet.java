package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Professor;
import model.Student;
import service.ProfessorService;
import service.StudentService;
import service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO check-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO check-generated method stub
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int role = 0;
		if(request.getParameter("role")!=null){
			role = Integer.parseInt(request.getParameter("role"));
		}
		Student student = null;Professor professor = null;
		boolean flag = false;
		UserService us = new UserService();
		
		
		switch (role) {
		case 1:
			student= us.getStudent(username);
			flag = password.equals(student.getPassword());
			break;
		case 2:
			professor = us.getProfessor(username);
			flag = password.equals(professor.getPassword());
			break;
		case  3:
			flag = username.equals("admin") && password.equals("admin");
			break;
		}
		System.out.println("hahah");
		if (flag) {
			HttpSession session = request.getSession();
			session.setAttribute("role", role);
			session.setAttribute("isLogined", 1);
			String url = null ;
			switch (role) {
			case 1:
				session.setAttribute("Student", student);
				System.out.print("========="+student.getPassword()+student.getName());
				url="Index.html";
				break;
			case 2:
				session.setAttribute("Professor", professor);
				url="Index.html";
				break;
			case 3:
				session.setAttribute("Admin", 1);
				url="CourseIndex.html";
				break;
			}

			response.sendRedirect(url);
		} else {
			HttpSession session = request.getSession();
			session = request.getSession();
			session.setAttribute("isLogined", 0);
			response.sendRedirect("login.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO check-generated method stub
		doGet(request, response);
	}

}
