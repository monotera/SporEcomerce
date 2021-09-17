package com.sporecomerce.api.demo.crewmembers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewmembersRepository extends CrudRepository<Crewmembers, Long> {

}
