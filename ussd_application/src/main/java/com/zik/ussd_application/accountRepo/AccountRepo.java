package com.zik.ussd_application.accountRepo;

import com.zik.ussd_application.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Accounts, Long> {
    @Query("select st from Accounts st where st.phoneNumber=?1 or st.accountNumber=?2")
    List<Accounts> isRecordExist(String phoneNumber, String accountNUmber);
    @Query("select st from Accounts st where st.phoneNumber=?1")
    Optional<Accounts> checkRecord(String phoneNumber);
}
