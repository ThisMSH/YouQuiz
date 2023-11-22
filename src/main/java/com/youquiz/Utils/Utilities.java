package com.youquiz.Utils;

import com.youquiz.Exceptions.ResourceBadRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Utilities {
    public static Pageable managePagination(int page, int size, String sortBy, String sortOrder) {
        if (!sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) && !sortOrder.equalsIgnoreCase(Sort.Direction.DESC.name())) {
            throw new ResourceBadRequest("Please make sure to choose either ascending or descending order.");
        }
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }
}
