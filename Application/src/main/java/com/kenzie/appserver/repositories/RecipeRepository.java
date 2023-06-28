package com.kenzie.appserver.repositories;


import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {

}
