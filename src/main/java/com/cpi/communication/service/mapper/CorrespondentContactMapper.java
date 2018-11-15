package com.cpi.communication.service.mapper;

import com.cpi.communication.domain.*;
import com.cpi.communication.service.dto.CorrespondentContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CorrespondentContact and its DTO CorrespondentContactDTO.
 */
@Mapper(componentModel = "spring", uses = {CorrespondentMapper.class})
public interface CorrespondentContactMapper extends EntityMapper<CorrespondentContactDTO, CorrespondentContact> {

    @Mapping(source = "correspondent.id", target = "correspondentId")
    @Mapping(source = "correspondent.correspondentName", target = "correspondentCorrespondentName")
    CorrespondentContactDTO toDto(CorrespondentContact correspondentContact);

    @Mapping(source = "correspondentId", target = "correspondent")
    CorrespondentContact toEntity(CorrespondentContactDTO correspondentContactDTO);

    default CorrespondentContact fromId(Long id) {
        if (id == null) {
            return null;
        }
        CorrespondentContact correspondentContact = new CorrespondentContact();
        correspondentContact.setId(id);
        return correspondentContact;
    }
}
