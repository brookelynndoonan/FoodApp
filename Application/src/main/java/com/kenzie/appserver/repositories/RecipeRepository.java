package com.kenzie.appserver.repositories;


import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {

    @Query(fields = "SELECT * FROM Recipes WHERE cuisine = :cuisine")
    List<RecipeRecord> findByCuisine(@Param("cuisine") String cuisine);

    @Query(fields = "SELECT * FROM Recipes WHERE dietaryRestrictions = :dietaryRestrictions")
    List<RecipeRecord> findByDietaryRestrictions(@Param("dietaryRestrictions") String dietaryRestrictions);
}
