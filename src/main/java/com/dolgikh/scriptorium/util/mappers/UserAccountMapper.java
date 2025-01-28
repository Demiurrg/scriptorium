package com.dolgikh.scriptorium.util.mappers;

import com.dolgikh.scriptorium.dto.users.UserAccountRequestDTO;
import com.dolgikh.scriptorium.dto.users.UserAccountResponseDTO;
import com.dolgikh.scriptorium.models.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    UserAccount toEntity(UserAccountRequestDTO userAccountRequestDTO);

    UserAccountResponseDTO toDTO(UserAccount userAccount);
}
