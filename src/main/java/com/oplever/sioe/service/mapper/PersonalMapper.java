package com.oplever.sioe.service.mapper;

import com.oplever.sioe.domain.*;
import com.oplever.sioe.service.dto.PersonalDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Personal and its DTO PersonalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonalMapper {

    PersonalDTO personalToPersonalDTO(Personal personal);

    List<PersonalDTO> personalsToPersonalDTOs(List<Personal> personals);

    Personal personalDTOToPersonal(PersonalDTO personalDTO);

    List<Personal> personalDTOsToPersonals(List<PersonalDTO> personalDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Personal personalFromId(Long id) {
        if (id == null) {
            return null;
        }
        Personal personal = new Personal();
        personal.setId(id);
        return personal;
    }
    

}
