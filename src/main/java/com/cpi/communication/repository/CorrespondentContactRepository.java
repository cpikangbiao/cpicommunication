package com.cpi.communication.repository;

import com.cpi.communication.domain.CorrespondentContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CorrespondentContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentContactRepository extends JpaRepository<CorrespondentContact, Long>, JpaSpecificationExecutor<CorrespondentContact> {

}
