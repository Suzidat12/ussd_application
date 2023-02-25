package com.zik.ussd_application.accountRepo;

import com.zik.ussd_application.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Accounts, Long> {
    @Query("select st from Accounts st where st.phoneNumber=?1")
    List<Accounts> isRecordExist(String phoneNumber);
    @Query("select st from Accounts st where st.phoneNumber=?1")
    Optional<Accounts> checkRecord(String phoneNumber);

    @Query("select st from Accounts st where st.phoneNumber=?1 and st.pin=?2")
    Optional<Accounts> checkPhoneNumberAndPin(String phoneNumber, String pin);
}
