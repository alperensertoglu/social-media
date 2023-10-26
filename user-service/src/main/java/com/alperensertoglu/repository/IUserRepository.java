package com.alperensertoglu.repository;

import com.alperensertoglu.repository.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserProfile,Long> {

    boolean  existsByUsername(String username);

    Optional<UserProfile> findByAuthId(Long authId);

}
