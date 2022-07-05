package org.zzz.jt.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	Optional<User> getByName(String name);
	
	//List<User> getAll();

	
}
