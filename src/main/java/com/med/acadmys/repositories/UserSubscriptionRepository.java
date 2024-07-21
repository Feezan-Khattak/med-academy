package com.med.acadmys.repositories;

import com.med.acadmys.models.User;
import com.med.acadmys.models.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    Optional<UserSubscription> findByUserAndActive(User user, boolean active);
}
