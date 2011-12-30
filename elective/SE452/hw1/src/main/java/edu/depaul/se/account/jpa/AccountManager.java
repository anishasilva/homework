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
import javax.persistence.Query;
import javax.persistence.TypedQuery;



/**
 * Implementation of requirements using JPA
 */
public class AccountManager implements IAccountManager {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AccountsPU");
	private EntityManager em = emf.createEntityManager();

	private Account getAccountById(int accountNbr) throws AccountNotFoundException{
		Query userNameQuery = em.createNamedQuery("Account.findById", Account.class);
		userNameQuery.setParameter("id", accountNbr);
		Account account;
		try {
			account = (Account) userNameQuery.getSingleResult();
		} catch (javax.persistence.NoResultException e){
			throw new AccountNotFoundException("find method");
		}
		return account;
	}
	public int createAccount(String name, float initialBalance) {
		Account account = new Account();
		account.setName(name);
		account.setBalance(initialBalance);
		
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();
		
		return account.getId();
	}

	public float deposit(int accountNbr, float amount)
			throws AccountNotFoundException {
		Account account = getAccountById(accountNbr);
		account.setBalance(account.getBalance() + amount);
		
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

		return account.getBalance();
	}

	public List<IAccount> getAllAccounts() {
		Query query = em.createNamedQuery("Account.findAll", Account.class);
        return (List<IAccount>)query.getResultList();
	}

	public float withdraw(int accountNbr, float amount)
			throws AccountNotFoundException, InsufficientFundsException {
		Account account = getAccountById(accountNbr);
		
		if(account.getBalance() < amount) throw new InsufficientFundsException();
		account.setBalance(account.getBalance() - amount);
		
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

		return account.getBalance();
	}

	public float closeAccount(int accountNbr) throws AccountNotFoundException {
		Account account = getAccountById(accountNbr);
		
		em.getTransaction().begin();
		em.remove(account);
		em.getTransaction().commit();
		
		return account.getBalance();
	}

	@Override
	public IAccount findAccount(int accountNbr) throws AccountNotFoundException {
		return getAccountById(accountNbr);
	}

}
