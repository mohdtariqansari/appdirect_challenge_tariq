package com.appdirect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appdirect.entity.SubscriptionUser;

@Repository
public interface UserRepository<S> extends JpaRepository<SubscriptionUser, String> {

	SubscriptionUser findByUuidAndSubscription_companyUuid(String uuid, String companyUuid);

	//void save(SubscriptionUser creator);

	void delete(SubscriptionUser subscriptionUser);

	SubscriptionUser findOne(String uuid);
}
