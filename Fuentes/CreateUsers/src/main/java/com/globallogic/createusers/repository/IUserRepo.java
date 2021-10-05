package com.globallogic.createusers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globallogic.createusers.entity.Users;

@Repository
public interface IUserRepo extends JpaRepository<Users, Long> {

}
