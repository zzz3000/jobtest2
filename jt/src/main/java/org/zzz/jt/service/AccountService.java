package org.zzz.jt.service;

import java.math.BigDecimal;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zzz.jt.data.Account;
import org.zzz.jt.data.User;
import org.zzz.jt.repository.AccountRepository;
import org.zzz.jt.repository.UserRepository;

@Service
public class AccountService {
	
	private static final BigDecimal INC_PROCENT = new BigDecimal(0.1);
	
	private static final BigDecimal MAX_VALUE = new BigDecimal(207);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Scheduled(cron = "*/30 * * * * *")
	@Transactional	
	public void addBalance() {		
		
		AccountService that = this;
		
		List<Account> list = accountRepository.findAll();
		
		list.stream().forEach(account -> {		
			int accId = account.getId();			
			that.addBalance(accId);			
		});
		
	}
	
	@Transactional	
	private void addBalance(int accountId) {
		
		synchronized (this) {					
			Account account = accountRepository.getById(accountId);
			
			BigDecimal accBalance = account.getBalance();
			BigDecimal percent =  accBalance.multiply(INC_PROCENT);
			
			if(accBalance.add(percent).compareTo(MAX_VALUE)<=0) {			
				account.setBalance(accBalance.add(percent));
			}			
			accountRepository.save(account);
		}
	}
		
	@Transactional
	public boolean transfer(int fromUserId, int toUserId, BigDecimal value) {	
		boolean result = false;
		User fromUser =  userRepository.getById(fromUserId);		
		User toUser = userRepository.getById(toUserId);
		
		synchronized (this) {
		
			Account fromAccount = accountRepository.getByUser(fromUser);		
			Account toAccount = accountRepository.getByUser(toUser);
			
			if(fromAccount.getBalance().compareTo(value) >=0) {				
				fromAccount.setBalance(fromAccount.getBalance().subtract(value));		
				toAccount.setBalance(toAccount.getBalance().add(value));
				result =  true;
			}
			
			accountRepository.save(fromAccount);
			accountRepository.save(toAccount);	
		}
		return result;
			
	}
	
	
	@Transactional
	public boolean transfer(int toUserId, BigDecimal value) {	
		int fromUserId = userService.getCurrentUserId();			
		return transfer(fromUserId, toUserId, value);
	}
	
}
