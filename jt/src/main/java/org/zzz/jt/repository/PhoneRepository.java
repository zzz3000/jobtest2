package org.zzz.jt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zzz.jt.data.UserPhone;

@Repository
public interface PhoneRepository  extends JpaRepository<UserPhone, Integer>{

	Optional<UserPhone> getByPhone(String phone);
}
