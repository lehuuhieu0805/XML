/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;

/**
 *
 * @author lehuuhieu
 */
@WebServlet(name = "CreateAccountController", urlPatterns = {"/CreateAccountController"})
public class CreateAccountController extends HttpServlet {

    private final String STATUS = "break";
    private final String LOGIN_PAGE = "index.html";
    private final String INVALID_PAGE = "invalid.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID_PAGE;
        try {
            String id = request.getParameter("txtId");
            String sClass = request.getParameter("txtSClass");
            String lastName = request.getParameter("txtLastname");
            String middleName = request.getParameter("txtMiddlename");
            String firstName = request.getParameter("txtFirstname");
            String male = request.getParameter("txtMale");
            String address = request.getParameter("txtAddress");
            String password = request.getParameter("txtPassword");

            ServletContext context = this.getServletContext();
            Document document = (Document) context.getAttribute("DOM_TREE");
            if (document == null) {
                //call parse DOM agian
                //store context
            }// end document didnot exist
            //document has existed
            //1. call model dao
            String xmlFilePath = context.getInitParameter("STUDENT_DB_FILE");
            String webAppPath = context.getRealPath("/");
            String xmlFile = webAppPath + xmlFilePath;
            StudentDAO dao = new StudentDAO();
            boolean result = dao.createStudent(id, sClass, lastName, middleName, firstName, male, address, password, STATUS, document, xmlFile);
            //2. process result
            if (result) {
                url = LOGIN_PAGE;
            }
        } catch (TransformerException e) {
            log("Transformer: " + e.getMessage());
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
