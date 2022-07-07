package org.zzz.jt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zzz.jt.data.Account;
import org.zzz.jt.data.User;

@Repository
public interface AccountRepository  extends JpaRepository<Account, Integer>{
	
	Account getByUser(User u);

}
