package com.sporecomerce.api.demo.star;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRepository extends CrudRepository<Star, Long> {

}
