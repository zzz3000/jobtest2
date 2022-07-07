package org.zzz.jt.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.zzz.jt.data.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User getById(int id);
	
	Optional<User> getByName(String name);
	

    List<User> findAllByNameStartsWith(String name, Pageable pageable);
	
	//List<User> getAll();

    
    /*
    @Query(value = "SELECT e FROM EmployeeProjectView as e WHERE e.employeeId=:employeeId and (:inputString is null or e.lastName like %:inputString% ) and " +
			"(:inputString is null or e.firstName like %:inputString%) and (:inputString is null or concat(e.projectId,'') like %:inputString%) and " +
			" (:inputString is null or e.projectName like %:inputString%) and  (:inputString is null or concat(e.projectBudget,'') like %:inputString%) and "+
			" (:inputString is null or e.projectLocation like %:inputString%)"
	)*/
    //JOIN u.emails email
    @Query(value = "SELECT DISTINCT u FROM User u  LEFT JOIN u.emails email LEFT JOIN u.phones phone "
    		+ "where  (:userName is null or u.name like :userName%) "
    		+ "and (:emailF is null or email.email=:emailF) "
    		+ "and ( cast(:birthParam as date) is null or u.dateOfBirth > :birthParam) "
    		+ "and (:phoneParam is null or phone.phone=:phoneParam)")
	Page<User> findPageByParams(@Param("userName")  String userName,@Param("emailF")  String emailF,
			@Param("phoneParam")  String phoneParam,  @Param("birthParam")  Date birthParam,Pageable pageable);
    
	
}
