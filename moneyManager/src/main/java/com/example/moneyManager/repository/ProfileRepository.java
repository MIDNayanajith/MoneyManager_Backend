package com.example.moneyManager.repository;

import com.example.moneyManager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository <ProfileEntity,Long>{

    //select * from tbl-profiles where email = ?
    Optional<ProfileEntity> findByEmail(String email);
}
