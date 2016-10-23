package com.appdirect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appdirect.entity.Subscription;

@Repository
public interface SubscriptionRepository<S> extends JpaRepository<Subscription, String> {

	//void save(Subscription subscription);

	void delete(String companyUuid);

	Subscription findOne(String companyUuid);

	void delete(Subscription subscription);

}
