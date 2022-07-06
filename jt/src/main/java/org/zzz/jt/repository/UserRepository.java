package org.zzz.jt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.zzz.jt.data.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User getById(int id);
	
	Optional<User> getByName(String name);
	

    List<User> findAllByNameStartsWith(String name, Pageable pageable);
	
	//List<User> getAll();

	
}
