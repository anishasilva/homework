package edu.depaul.se.servlet;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.jpa.AccountManager;

public class DepositAccountServletTest extends MockServletInitializer {
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	private MockServletConfig config = new MockServletConfig();
	private DepositAccountServlet servlet = new DepositAccountServlet();

	@Before
	public void setup() throws Exception {
		servlet.init(config);
	}

	@Test
	public void invalidAmountEntry() throws Exception {
        request.setParameter("account", "hey jude");
        request.setParameter("amount", "hey jude");
		servlet.doGet(request, response);
		String result = response.getForwardedUrl();
		assertEquals("/InvalidDataEntry.jsp", result);
	}

	@Test
	public void invalidAccountEntry() throws Exception {
        request.setParameter("account", "100");
        request.setParameter("amount", "hey jude");
		servlet.doGet(request, response);
		String result = response.getForwardedUrl();
		assertEquals("/InvalidDataEntry.jsp", result);
	}
	
	@Test
	public void accountNotFound() throws Exception {
        request.setParameter("account", "hey jude");
		servlet.doGet(request, response);
		String result = response.getForwardedUrl();
		assertEquals("/InvalidDataEntry.jsp", result);
	}
	
	@Test
	public void validDepositAmount () throws Exception {
        AccountManager manager = new AccountManager();
        int accountNumber = manager.createAccount("George", 100);
        
        int depositAmt = 10;
        request.setParameter("account", ""+accountNumber);
        request.setParameter("amount", "" + depositAmt);
		servlet.doGet(request, response);
		String result = response.getForwardedUrl();
		assertEquals("/AccountInfo.jsp", result);
		assertEquals((float) 110.0, (Float) request.getAttribute("balance"), 0.0);
		assertEquals(""+accountNumber, ""+request.getAttribute("account"));
	}
	
}
