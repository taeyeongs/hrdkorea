package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MemberDAO;

@WebServlet("/")
public class MemberConstoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberConstoller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}

	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String context = request.getContextPath(); //톰켓에서 context path 를 가져온다.(-Servers 의 톰캣의 server.xml 의 context path)  웹어플리케이션(웹프로젝트) 하나당 하나의 servletContext 가 생성된다. 자원관리, 
		String command = request.getServletPath(); //url 가장 뒤의 파일명만 가져온다.
		String site = null;
		
		System.out.println(context + ", " + command);
		
		MemberDAO member = new MemberDAO();
		
		switch(command) {
			case "/home": site = "index.jsp"; break;
			case "/insert": site = member.insert(request, response); break;
			case "/list": site = member.selectAll(request, response); break;
			case "/add": site = member.nextCustno(request, response); break;
			case "/modify": site = member.modify(request, response); break;
			case "/update": 
				int result1 = member.update(request, response);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				if (result1 == 1) { //업데이트 성공
					out.println("<script>" //location.href='/HRD_1234'
							+ "alert('회원수정이 완료 되었습니다.'); "
							+ "location.href='" + context + "'"
							+ "</script>");
					out.flush();
				} else {
					out.println("<script>" //location.href='/HRD_1234'
							+ "alert('수정실패'); "
							+ "location.href='" + context + "'"
							+ "</script>");
					out.flush();
				}
			break;
			case "/delete": site = member.nextCustno(request, response); break;
		}
		
		getServletContext().getRequestDispatcher("/" + site).forward(request, response);
	}
}
