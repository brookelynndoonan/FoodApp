package com.kenzie.appserver.repositories;


import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {

    //We need to figure out how to define recipeId returning null if there is none.
    //Otherwise, this functions if there is one, or returns Optional#empty() if there's
    //none found.
// TODO - Fix this with the notes above in mind.
Optional<RecipeRecord> findById(String recipeId);
}

