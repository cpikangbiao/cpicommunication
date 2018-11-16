package com.cpi.communication.repository;

import com.cpi.communication.domain.Correspondent;
import com.cpi.communication.domain.Port;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Correspondent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrespondentRepository extends JpaRepository<Correspondent, Long>, JpaSpecificationExecutor<Correspondent> {

    List<Correspondent> findAllByPortOrderByCorrespondentName(Port port);
}
