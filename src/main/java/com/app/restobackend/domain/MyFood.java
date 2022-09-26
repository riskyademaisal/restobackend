package com.app.restobackend.domain;
import lombok.Data;
import com.app.restobackend.commons.GenericEntity;
import com.app.restobackend.domain.enumeration.Size;



import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "myfood")
@Data
public class MyFood implements Serializable, GenericEntity<MyFood> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;

    @NotBlank
    private String name;


    @Override
    public void update(MyFood source) {
        this.name = source.getName();
    }

    @Override
    public Long getId() {
        return this.id;
    }

    

    @Override
    public MyFood createNewInstance() {
        MyFood newInstance = new MyFood();
        newInstance.update(this);

        return newInstance;
    }


}
