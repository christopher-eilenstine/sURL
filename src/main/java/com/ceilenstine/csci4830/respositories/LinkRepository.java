package com.ceilenstine.csci4830.respositories;

import com.ceilenstine.csci4830.models.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, Integer> {

    Optional<LinkEntity> findByLongUrl(String lUrl);
    Optional<LinkEntity> findByShortUrl(String sUrl);
    Optional<LinkEntity> findById(Integer id);

}
