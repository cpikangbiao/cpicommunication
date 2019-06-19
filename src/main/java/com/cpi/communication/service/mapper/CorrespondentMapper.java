package com.cpi.communication.service.mapper;

import com.cpi.communication.domain.*;
import com.cpi.communication.service.dto.CorrespondentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Correspondent and its DTO CorrespondentDTO.
 */
@Mapper(componentModel = "spring", uses = {PortMapper.class})
public interface CorrespondentMapper extends EntityMapper<CorrespondentDTO, Correspondent> {

    @Mapping(source = "port.id", target = "portId")
    @Mapping(source = "port.portName", target = "portPortName")
    CorrespondentDTO toDto(Correspondent correspondent);

    @Mapping(source = "portId", target = "port")
    Correspondent toEntity(CorrespondentDTO correspondentDTO);

    default Correspondent fromId(Long id) {
        if (id == null) {
            return null;
        }
        Correspondent correspondent = new Correspondent();
        correspondent.setId(id);
        return correspondent;
    }
}
