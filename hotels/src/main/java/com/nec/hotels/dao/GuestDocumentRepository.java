package com.nec.hotels.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nec.hotels.entity.GuestDocuments;

@Repository
public interface GuestDocumentRepository extends JpaRepository<GuestDocuments, UUID> {
		

}
