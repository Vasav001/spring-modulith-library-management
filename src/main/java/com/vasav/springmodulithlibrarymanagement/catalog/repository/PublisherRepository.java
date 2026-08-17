package com.vasav.springmodulithlibrarymanagement.catalog.repository;

import com.vasav.springmodulithlibrarymanagement.catalog.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}