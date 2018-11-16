package com.cpi.communication.repository;

import com.cpi.communication.domain.Country;
import com.cpi.communication.domain.Port;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Port entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortRepository extends JpaRepository<Port, Long>, JpaSpecificationExecutor<Port> {

    List<Port> findAllByCountryOrderByPortName(Country country);
}
