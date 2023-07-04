package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.Enums;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {

    List<RecipeRecord> findByCuisine(Enums.Cuisine cuisine);

    List<RecipeRecord> findByDietaryRestrictions(Enums.DietaryRestrictions dietaryRestrictions);

    List<RecipeRecord> findAll();

}
