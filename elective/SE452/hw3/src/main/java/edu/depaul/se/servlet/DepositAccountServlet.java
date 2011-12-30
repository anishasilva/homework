package edu.depaul.se.servlet;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.jpa.AccountManager;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DepositAccountServlet")
public class DepositAccountServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	/**********************************************************************
    	*** Make it work ****
    	**********************************************************************/
    	AccountManager am = new AccountManager();
    	RequestDispatcher dispatcher = null;
    	try {
    		int accId = Integer.parseInt(request.getParameter("account"));
    		int amount = Integer.parseInt(request.getParameter("amount"));
    		float balance = am.deposit(accId, amount);
    		//maybe an error here.
    		request.setAttribute("balance", balance);
    		request.setAttribute("account", accId);
    		request.setAttribute("amount", amount);
    		dispatcher = getServletContext().getRequestDispatcher("/AccountInfo.jsp");
    	}catch (Exception e){
    		dispatcher = getServletContext().getRequestDispatcher("/InvalidDataEntry.jsp");
    	}finally{
    		dispatcher.forward(request, response);
    	}
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
