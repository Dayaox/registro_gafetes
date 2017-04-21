package com.oplever.sioe.repository;

import com.oplever.sioe.domain.Personal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personal entity.
 */
@SuppressWarnings("unused")
public interface PersonalRepository extends JpaRepository<Personal,Long> {

}
