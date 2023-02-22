package com.zik.ussd_application.accountRepo;

import com.zik.ussd_application.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Accounts, Long> {
}
