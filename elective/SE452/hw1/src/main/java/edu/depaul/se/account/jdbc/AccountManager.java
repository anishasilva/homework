package edu.depaul.se.account.jdbc;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of solution using JDBC
 */
public class AccountManager implements IAccountManager{
	
	@Override
	public int createAccount(String name, float initialBalance) {
		//insert (name, initial_balance) into accounts values (name, initialBalance);
		//int accountNumber = select accountNbr from accounts where name = name and initialBalance = initialBalance;
		//return account.getId();
		Account account = new Account();
		account.setBalance(initialBalance);
		account.setName(name);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String insertString = "INSERT INTO accounts(name, balance) VALUES ('" + name + "'," + initialBalance + ")";
			
			statement.executeUpdate(insertString);
			String selectString = "SELECT id FROM accounts ORDER BY id DESC LIMIT 1";
			ResultSet rs = statement.executeQuery(selectString);
			rs.next();
			account.setId(rs.getInt(1));
			connection.close();
		} catch (Exception e) {
			System.out.println("ERROR:connecting to database in createAccount method failed.");
			e.printStackTrace();
		}
		
		return account.getId();
	}

	@Override
	public float deposit(int accountNbr, float amount)
			throws AccountNotFoundException {
		//select * into Account from accounts where accountNbr = accountNumber
		//if (nothing returned from query) throw new AccountNotFoundException;
		//newBalance = newBalance + amount
		//update balance = newBalance from accounts where accountNbr = accountNumber 
		//return account.getBalance();
		Account account = new Account();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			
			
			String sql = "SELECT id, name, balance FROM accounts WHERE id = " + accountNbr;
			
			
			ResultSet rs = statement.executeQuery(sql);
			boolean hasRows = false;
			while (rs.next()){
				hasRows = true;
				account = new Account(rs.getInt(1), rs.getString(2), rs.getFloat(3));
				
				account.setBalance(amount + account.getBalance());
			}
			if (hasRows == false) throw new AccountNotFoundException(String.valueOf(accountNbr));
			
			Statement update = connection.createStatement();
			update.executeUpdate("UPDATE accounts SET balance = " 
					+ account.getBalance() + " WHERE id = " + account.getId());
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("error connecting to database.");
			e.printStackTrace();
		}
		return account.getBalance();
		
	}

	@Override
	public List<IAccount> getAllAccounts() {
		//select * from accounts;
		List<IAccount> accounts = new ArrayList<IAccount>();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT id, name, balance FROM accounts";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()){
				Account account = new Account(rs.getInt(1), rs.getString(2), rs.getFloat(3));
				accounts.add(account);
			}
			connection.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		//for each account in accounts result set cursor
		//  turn it into an account object
		//	add it to the account List
		
		return accounts;
	}

	@Override
	public float withdraw(int accountNbr, float amount)
			throws AccountNotFoundException, InsufficientFundsException {
		//select balance into newBalance from accounts where accountNbr = accountNumber
		//if (nothing returned from query) throw new AccountNotFoundException;
		//if amount > newBalance throw new InsufficientFundsException;
		//newBalance = newBalance - amount
		//update balance = newBalance from accounts where accountNbr = accountNumber 
		//return account.balance() 
		Account account = new Account();
		account.setId(accountNbr);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT name, balance FROM accounts WHERE id = " + account.getId();
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) throw new AccountNotFoundException(String.valueOf(accountNbr));
			account.setName(rs.getString(1));
			account.setBalance(rs.getFloat(2));
			if (account.getBalance() < amount) throw new InsufficientFundsException();
			account.setBalance(account.getBalance() - amount);
			String update = "UPDATE accounts SET balance = " + account.getBalance() + " WHERE id = " + account.getId();
			statement.executeUpdate(update);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account.getBalance();
	}

	@Override
	public float closeAccount(int accountNbr) throws AccountNotFoundException {
		//select balance into int balance from accounts where accountNbr = accountNbr
		//if (nothing returned from query) throw new AccountNotFoundException;
		//delete * from balance where accountNbr = accountNbr
		//return account.balance();
		Account account = new Account();
		account.setId(accountNbr);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT balance, name FROM accounts WHERE id = " + account.getId();
			ResultSet rs = statement.executeQuery(sql);
			
			if(!rs.next()) throw new AccountNotFoundException(String.valueOf(accountNbr));
			account.setBalance(rs.getFloat(1));
			account.setName(rs.getString(2));
			String delete = "DELETE FROM accounts WHERE id = " + account.getId();
			statement.executeUpdate(delete);
		}catch (SQLException e) {
			System.out.println("error connecting to database.");
			e.printStackTrace();
		}
		return account.getBalance();
	}
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:mem:.", "sa", "");
	}

	@Override
	public IAccount findAccount(int accountNbr) throws AccountNotFoundException {
		Account account = new Account();
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String sql = "SELECT id, name, balance FROM accounts WHERE id = " + accountNbr;
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) throw new AccountNotFoundException(String.valueOf(accountNbr));
			account = new Account(rs.getInt(1), rs.getString(2), rs.getFloat(3));
 		} catch(SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

}