package com.peerless.peerless_bank.Repository;

import com.peerless.peerless_bank.Entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
