package com.booleanuk.api.bagels;

import java.util.List;

public class BagelController {
    private BagelRepository repository;

    public BagelController(BagelRepository repository) {
        this.repository = repository;
    }

    public List<Bagel> getAll() {
        return this.repository.findAll();
    }
}
