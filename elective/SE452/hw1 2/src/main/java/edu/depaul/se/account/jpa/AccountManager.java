package edu.depaul.se.account.jpa;

import edu.depaul.se.account.AccountNotFoundException;
import edu.depaul.se.account.IAccount;
import edu.depaul.se.account.IAccountManager;
import edu.depaul.se.account.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Implementation of requirements using JPA
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
