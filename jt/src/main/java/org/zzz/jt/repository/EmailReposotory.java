package org.zzz.jt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zzz.jt.data.UserEmail;

@Repository
public interface EmailReposotory extends JpaRepository<UserEmail, Integer>{
	
	Optional<UserEmail> getByEmail(String email);

}
