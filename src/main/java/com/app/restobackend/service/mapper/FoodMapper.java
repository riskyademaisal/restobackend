package com.app.restobackend.service.mapper;

import com.app.restobackend.domain.Food;
import com.app.restobackend.service.dto.FoodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Food} and its DTO {@link FoodDTO}.
 */
@Mapper(componentModel = "spring")
public interface FoodMapper extends EntityMapper<FoodDTO, Food> {}
