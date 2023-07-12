package com.kenzie.appserver.repositories;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {

    List<RecipeRecord> findByCuisine(String cuisine);

    List<RecipeRecord> findByDietaryRestrictions(String dietaryRestrictions);

    List<RecipeRecord> findByCuisineAndDietaryRestrictions(String cuisine, String dietaryRestrictions);

    List<RecipeRecord> findAll();

    List<RecipeRecord> findByCuisineAndDietaryRestrictionsAndTitleContains(String cuisine, String dietaryRestrictions, String query);

    List<RecipeRecord> findByCuisineAndTitleContaining(String cuisine, String query);

    List<RecipeRecord> findByDietaryRestrictionsAndTitleContaining(String dietaryRestrictions, String query);

    List<RecipeRecord> findByTitleContaining(String query);
}
