package com.hornsnhuffs.schedule.modules.furniture;

import com.hornsnhuffs.schedule.core.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/furniture")
public class FurnitureController extends BaseCrudController<Furniture> {

    private final FurnitureRepository furnitureRepository;

    @Autowired
    public FurnitureController(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    @Override
    protected CrudRepository<Furniture, Integer> getRepository() {
        return furnitureRepository;
    }
}
