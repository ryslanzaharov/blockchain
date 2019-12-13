package com.chain.demo.repository;

import com.chain.demo.models.Block;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlockDataRepository extends CrudRepository<Block, Long> {

    @Query("SELECT b FROM Block as b ORDER BY id")
    List<Block> getAllBlock();
}
