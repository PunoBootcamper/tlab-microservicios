package com.tecylab.product_api.repository;

import com.tecylab.product_api.model.OperationLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationLogRepository extends MongoRepository<OperationLogEntity, String> {
}
