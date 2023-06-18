package com.kpo.springshaurma.repository;

import com.kpo.springshaurma.model.ShaurmaUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<ShaurmaUser, Long> {

    Optional<ShaurmaUser> findByUsername(String username);

    Optional<ShaurmaUser> findByActivationCode(String code);

    List<ShaurmaUser> findAll();

    Optional<ShaurmaUser> findByAccessTokenAndActive(String accessToken, Boolean active);

    Optional<ShaurmaUser> findByEmail(String email);
}
