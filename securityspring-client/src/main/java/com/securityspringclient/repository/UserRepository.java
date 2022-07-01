package com.securityspringclient.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securityspringclient.entity.User;


@Repository

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
