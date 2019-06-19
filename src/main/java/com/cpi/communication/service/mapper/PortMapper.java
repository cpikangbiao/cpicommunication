package com.cpi.communication.service.mapper;

import com.cpi.communication.domain.*;
import com.cpi.communication.service.dto.PortDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Port} and its DTO {@link PortDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class})
public interface PortMapper extends EntityMapper<PortDTO, Port> {

    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.countryName", target = "countryCountryName")
    PortDTO toDto(Port port);

    @Mapping(source = "countryId", target = "country")
    Port toEntity(PortDTO portDTO);

    default Port fromId(Long id) {
        if (id == null) {
            return null;
        }
        Port port = new Port();
        port.setId(id);
        return port;
    }
}
