package com.zik.ussd_application.accountRepo;

import com.zik.ussd_application.model.Accounts;
import org.springframework.data.domain.Pageable;
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

    @Query("select st from Accounts st where  st.pin=?1")
    Optional<Accounts> checkWithdrawal(String pin);

    @Query("select st from Accounts st")
    List<Accounts> getByPage(Pageable pageable);

    @Query("select st from Accounts st where YEAR(st.datecreated)=?1")
    List<Accounts> findByYear(Integer year);

}
