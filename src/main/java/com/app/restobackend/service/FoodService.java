package com.app.restobackend.service;

import com.app.restobackend.commons.GenericSpecification;
import com.app.restobackend.commons.PagingRequest;
import com.app.restobackend.commons.PagingResult;
import com.app.restobackend.domain.Food;
import com.app.restobackend.domain.MyFood;
import com.app.restobackend.repository.FoodRepository;
import com.app.restobackend.repository.MyFoodRepository;
import com.app.restobackend.service.dto.FoodDTO;
import com.app.restobackend.service.mapper.FoodMapper;
import java.util.Optional;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Food}.
 */
@Service
@Transactional
public class FoodService {


    private final Logger log = LoggerFactory.getLogger(FoodService.class);

    private final FoodRepository foodRepository;

    private final MyFoodRepository myFoodRepository;

    private final FoodMapper foodMapper;


    public FoodService(FoodRepository foodRepository, FoodMapper foodMapper, MyFoodRepository myFoodRepository) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
        this.myFoodRepository = myFoodRepository;
    }

    /**
     * Save a food.
     *
     * @param foodDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodDTO save(FoodDTO foodDTO) {
        log.debug("Request to save Food : {}", foodDTO);
        Food food = foodMapper.toEntity(foodDTO);
        food = foodRepository.save(food);
        return foodMapper.toDto(food);
    }

    /**
     * Update a food.
     *
     * @param foodDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodDTO update(FoodDTO foodDTO) {
        log.debug("Request to update Food : {}", foodDTO);
        Food food = foodMapper.toEntity(foodDTO);
        food = foodRepository.save(food);
        return foodMapper.toDto(food);
    }

    /**
     * Partially update a food.
     *
     * @param foodDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FoodDTO> partialUpdate(FoodDTO foodDTO) {
        log.debug("Request to partially update Food : {}", foodDTO);

        return foodRepository
            .findById(foodDTO.getId())
            .map(existingFood -> {
                foodMapper.partialUpdate(existingFood, foodDTO);

                return existingFood;
            })
            .map(foodRepository::save)
            .map(foodMapper::toDto);
    }

    /**
     * Get all the foods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FoodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Foods");
        return foodRepository.findAll(pageable).map(foodMapper::toDto);
    }

    public Page<Food> getPage(PagingRequest pagingRequest) {
        final GenericSpecification<Food> specification = new GenericSpecification<>(pagingRequest);
        final Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLength());
        Page<Food> thePage = foodRepository.findAll(specification, pageable);
        return thePage;
    }

    /**
     * Get one food by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoodDTO> findOne(Long id) {
        log.debug("Request to get Food : {}", id);
        return foodRepository.findById(id).map(foodMapper::toDto);
    }

    /**
     * Delete the food by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Food : {}", id);
        foodRepository.deleteById(id);
    }

  
}
