package com.hornsnhuffs.schedule.modules.furniture;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnitureRepository extends CrudRepository<Furniture, Integer> {
}
