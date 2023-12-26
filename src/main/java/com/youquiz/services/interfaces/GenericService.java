package com.youquiz.services.interfaces;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface GenericService<T, R, ID> {
    T create(R request);
    T update(R request);
    T delete(ID id);
    T get(ID id);
    Page<T> getAll(Map<String, Object> params);
}
