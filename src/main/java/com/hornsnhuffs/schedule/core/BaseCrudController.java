package com.hornsnhuffs.schedule.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hornsnhuffs.schedule.web.UnableToProcessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseCrudController<T extends BaseEntity> {

    protected abstract CrudRepository<T, Integer> getRepository();

    @RequestMapping
    public List<T> index() {
        return StreamSupport.stream(getRepository().findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}")
    public T get(@PathVariable Integer id) {
        return getRepository().findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public T create(@RequestBody T entity) throws UnableToProcessException {
        return getRepository().save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public T update(@PathVariable Integer id, @RequestBody T entity) throws UnableToProcessException {
        // TODO if (entity.getId() != id)
        return getRepository().save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Integer id) throws UnableToProcessException {
        getRepository().delete(id);
    }

    @ExceptionHandler(UnableToProcessException.class)
    public @ResponseBody String handleUnableToProcessException(UnableToProcessException e, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(e));
        return null;
    }
}
