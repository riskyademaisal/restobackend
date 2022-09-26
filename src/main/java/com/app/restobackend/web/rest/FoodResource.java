package com.app.restobackend.web.rest;

import com.app.restobackend.commons.MessageResponse;
import com.app.restobackend.commons.PagingRequest;
import com.app.restobackend.commons.PagingResult;
import com.app.restobackend.domain.Food;
import com.app.restobackend.domain.MyFood;
import com.app.restobackend.repository.FoodRepository;
import com.app.restobackend.service.FoodService;
import com.app.restobackend.service.dto.FoodDTO;
import com.app.restobackend.service.dto.LazyEvent;
import com.app.restobackend.web.rest.errors.BadRequestAlertException;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.app.restobackend.domain.Food}.
 */
@RestController
@RequestMapping("/api")
public class FoodResource {

    private final Logger log = LoggerFactory.getLogger(FoodResource.class);

    private static final String ENTITY_NAME = "restobackendFood";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodService foodService;

    private final FoodRepository foodRepository;

    public FoodResource(FoodService foodService, FoodRepository foodRepository) {
        this.foodService = foodService;
        this.foodRepository = foodRepository;
    }

    /**
     * {@code POST  /foods} : Create a new food.
     *
     * @param foodDTO the foodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodDTO, or with status {@code 400 (Bad Request)} if the food has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foods")
    public ResponseEntity<FoodDTO> createFood(@Valid @RequestBody FoodDTO foodDTO) throws URISyntaxException {
        log.debug("REST request to save Food : {}", foodDTO);
        if (foodDTO.getId() != null) {
            throw new BadRequestAlertException("A new food cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodDTO result = foodService.save(foodDTO);
        return ResponseEntity
            .created(new URI("/api/foods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foods/:id} : Updates an existing food.
     *
     * @param id the id of the foodDTO to save.
     * @param foodDTO the foodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodDTO,
     * or with status {@code 400 (Bad Request)} if the foodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foods/{id}")
    public ResponseEntity<FoodDTO> updateFood(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FoodDTO foodDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Food : {}, {}", id, foodDTO);
        if (foodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoodDTO result = foodService.update(foodDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foods/:id} : Partial updates given fields of an existing food, field will ignore if it is null
     *
     * @param id the id of the foodDTO to save.
     * @param foodDTO the foodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodDTO,
     * or with status {@code 400 (Bad Request)} if the foodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the foodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the foodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FoodDTO> partialUpdateFood(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FoodDTO foodDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Food partially : {}, {}", id, foodDTO);
        if (foodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoodDTO> result = foodService.partialUpdate(foodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /foods} : get all the foods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foods in body.
     */
    @GetMapping("/foods")
    public ResponseEntity<List<FoodDTO>> getAllFoods(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Foods");
        Page<FoodDTO> page = foodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/datatable")
    public MessageResponse<Page<Food>> getPage(@RequestParam(name = "lazyEvent") String lazyEvent) {
        
        PagingRequest data = new Gson().fromJson(lazyEvent, PagingRequest.class);
        return new MessageResponse<>(foodService.getPage(data));
    }


    /**
     * {@code GET  /foods/:id} : get the "id" food.
     *
     * @param id the id of the foodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foods/{id}")
    public ResponseEntity<FoodDTO> getFood(@PathVariable Long id) {
        log.debug("REST request to get Food : {}", id);
        Optional<FoodDTO> foodDTO = foodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodDTO);
    }

    /**
     * {@code DELETE  /foods/:id} : delete the "id" food.
     *
     * @param id the id of the foodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        log.debug("REST request to delete Food : {}", id);
        foodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

}
