package com.gome.upm.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.gome.upm.mongo.model.GtpTaskReport;

public interface GtpTaskReportRepository extends MongoRepository<GtpTaskReport, String> {
	
	@Query(value="{'_id' : ?0}",fields="{'details':{$slice:?1},'_id':1}")
	public GtpTaskReport getById(String id, int slice);
	


}
