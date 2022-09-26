package com.app.restobackend.commons;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import com.app.restobackend.utils.QueryBuilder;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;


public abstract class GenericService<T extends GenericEntity<T>> {

    
    private final GenericRepository<T> repository;

    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public Page<T> getPage(PagingRequest pagingRequest) {
        final GenericSpecification<T> specification = new GenericSpecification<>(pagingRequest);
        final Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLength());
        Page<T> thePage = repository.findAll(specification, pageable);
        return thePage;
    }

    public T get(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException()
        );
    }

    @Transactional
    public T update(Long id, T updated) throws ObjectNotFoundException {
        T dbDomain = get(id);
        dbDomain.update(updated);

        return repository.save(dbDomain);
    }

    @Transactional
    public T create(T newDomain) {
        T dbDomain = newDomain.createNewInstance();
        return repository.save(dbDomain);
    }

    @Transactional
    public void delete(Long id) throws ObjectNotFoundException {
        //check if object with this id exists
        get(id);
        repository.deleteById(id);
    }

    public void deleteBulk(List<Long> ids) {
        repository.deleteAllById(ids);
    }
}

