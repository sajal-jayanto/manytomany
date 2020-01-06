package com.cramstack.manytomany.repositories;

import com.cramstack.manytomany.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface PublisherRepository extends JpaRepository<Publisher,Integer> {


}
