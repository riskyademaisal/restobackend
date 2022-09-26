package com.app.restobackend.web.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.app.restobackend.commons.GenericController;
import com.app.restobackend.domain.MyFood;
import com.app.restobackend.repository.MyFoodRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/foodsprimereact")
public class FoodPrimeReactResource extends GenericController<MyFood> {
    
    public FoodPrimeReactResource(MyFoodRepository repository) {
        super(repository);
    }
}
