package com.app.restobackend.repository;

import com.app.restobackend.commons.GenericRepository;
import com.app.restobackend.domain.MyFood;


import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Food entity.
 */
@Repository
public interface MyFoodRepository extends GenericRepository<MyFood> {
}
