package org.zzz.jt.data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "users")
public class User { // implements UserDetails

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	Integer id;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "date_of_birth")
	Date dateOfBirth;
	
	@Column(name = "password")
	String password;
	
	@OneToMany(mappedBy="user")
	@Cascade({CascadeType.ALL})
    private Set<UserEmail> emails;
	
	@OneToMany(mappedBy="user")
	@Cascade({CascadeType.ALL})
    private Set<UserPhone> phones;
	
	
	@OneToOne(mappedBy="user",fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
    private Account account;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserEmail> getEmails() {
		return emails;
	}

	public void setEmails(Set<UserEmail> emails) {
		this.emails = emails;
	}
	
	

	public Set<UserPhone> getPhones() {
		return phones;
	}

	public void setPhones(Set<UserPhone> phones) {
		this.phones = phones;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User) ){
			return false;
		}		
		User other = (User) obj;
		if (getId() == null) {
			if (other.getId()!= null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", password=" + password + "]";
	}
	
	
	
}
