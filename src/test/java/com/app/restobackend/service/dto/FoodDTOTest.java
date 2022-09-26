package com.app.restobackend.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.app.restobackend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodDTO.class);
        FoodDTO foodDTO1 = new FoodDTO();
        foodDTO1.setId(1L);
        FoodDTO foodDTO2 = new FoodDTO();
        assertThat(foodDTO1).isNotEqualTo(foodDTO2);
        foodDTO2.setId(foodDTO1.getId());
        assertThat(foodDTO1).isEqualTo(foodDTO2);
        foodDTO2.setId(2L);
        assertThat(foodDTO1).isNotEqualTo(foodDTO2);
        foodDTO1.setId(null);
        assertThat(foodDTO1).isNotEqualTo(foodDTO2);
    }
}
