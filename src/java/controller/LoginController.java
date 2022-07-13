/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import dto.StudentDTO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;

/**
 *
 * @author lehuuhieu
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    private final String INVALID_PAGE = "invalid.html";
    private final String SEARCH_PAGE = "search.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String url = INVALID_PAGE;

        try {
            ServletContext context = this.getServletContext();
            Document document = (Document) context.getAttribute("DOM_TREE");
            if (document == null) {
                //call parse DOM agian
                //store context
            }// end document didnot exist
            //document has existed
            //1. call model dao
            StudentDAO dao = new StudentDAO();
            StudentDTO dto = dao.checkLogin(username, password, document);
            //2. process result
            if (dto != null) {
                url = SEARCH_PAGE;
                HttpSession session = request.getSession();
                session.setAttribute("USERNAME", username);
                session.setAttribute("STUDENTINFO", dto);
            }
        } catch (XPathExpressionException e) {
            log("Xpath: " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
