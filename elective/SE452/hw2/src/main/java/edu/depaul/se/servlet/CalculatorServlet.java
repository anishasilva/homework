package edu.depaul.se.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import edu.depaul.se.calculator.Calculator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles the URL request and exception handling associated with Calculator
 */
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	/*********************************************************************
    	 *********************		PUT YOUR CODE HERE					  ***
    	 *********************************************************************/
    	//set the content type of response to text/html
    	response.setContentType("text/html");
    	
    	//grab the parameters sent in the request
    	String rhs = request.getParameter("rhs");
    	String lhs = request.getParameter("lhs");
    	String operator = request.getParameter("operator");
    	
    	//prepare a response
    	PrintWriter out = response.getWriter();
    	out.print("<h1>Calculator servlet</h1>");
    	out.print("<p>");
    	//try block to catch any invalid input
    	try {
    		//case logic to find the operation the user needs
    		if (operator.equals("+")) {
    			out.print(lhs + " + " + rhs + " =" + Calculator.add(Float.parseFloat(lhs), Float.parseFloat(rhs)));
    		}else if (operator.equals("-")) {
    			out.print(lhs + " - " + rhs + " =" + Calculator.subtract(Float.parseFloat(lhs), Float.parseFloat(rhs)));
    		}else if(operator.equals("/")) {
    			out.print(lhs + " / " + rhs + " =" + Calculator.divide(Float.parseFloat(lhs), Float.parseFloat(rhs)));
    		}else if (operator.equals("*")) {
    			out.print(lhs + " * " + rhs + " =" + Calculator.multiply(Float.parseFloat(lhs), Float.parseFloat(rhs)));
    		}
    		//close out our paragraph
    		out.print("</p>");
    		
    		//handle the case where the input is bad.
    	}catch (NumberFormatException e){
    		out.print("<h4>One or more of the input numbers is wrong. please enter only numeric values</h4></p>");
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
