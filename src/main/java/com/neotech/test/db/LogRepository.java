package com.neotech.test.db;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogModel, Long> {
}
