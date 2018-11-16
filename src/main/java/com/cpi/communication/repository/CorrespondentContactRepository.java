package com.cpi.communication.repository;

import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.domain.CorrespondentContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the CorrespondentContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentContactRepository extends JpaRepository<CorrespondentContact, Long>, JpaSpecificationExecutor<CorrespondentContact> {


    List<CorrespondentContact> findAllByCorrespondentOrderByCorrespondentContactName(Correspondent correspondent);
}
