package com.vasav.springmodulithlibrarymanagement.catalog.repository;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}