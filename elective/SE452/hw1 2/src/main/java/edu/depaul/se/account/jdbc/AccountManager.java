package edu.depaul.se.account.jdbc;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of solution using JDBC
 */
public class AccountManager implements IAccountManager {

	public int createAccount(String name, float initialBalance) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float deposit(int accountNbr, float amount)
			throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<IAccount> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	public float withdraw(int accountNbr, float amount)
			throws AccountNotFoundException, InsufficientFundsException {
		// TODO Auto-generated method stub
		return 0;
	}

	public float closeAccount(int accountNbr) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IAccount findAccount(int accountNbr) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}