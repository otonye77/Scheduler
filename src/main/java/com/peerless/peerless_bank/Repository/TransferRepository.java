package com.peerless.peerless_bank.Repository;

import com.peerless.peerless_bank.Entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findBySenderId(Long senderAccountId);

    List<Transfer> findByTransferDateBefore(LocalDateTime now);
}
