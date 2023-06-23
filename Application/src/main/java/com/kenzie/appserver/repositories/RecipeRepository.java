package com.kenzie.appserver.repositories;


import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {
    List<RecipeRecord> findByRecipeId(String recipeId);
}
