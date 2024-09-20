package com.sboard.repository;

import com.sboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUidAndPass(String uid, String pass);

    int countByUid(String value);
    int countByNick(String value);
    int countByEmail(String value);
    int countByHp(String value);

}
