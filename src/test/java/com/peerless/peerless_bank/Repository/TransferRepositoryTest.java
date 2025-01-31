package com.peerless.peerless_bank.Repository;

import com.peerless.peerless_bank.Entities.Transfer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransferRepositoryTest {

    @Autowired
    private TransferRepository transferRepository;

    @Test
    void testFindBySenderAccountId() {
        Transfer transfer = new Transfer();
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("500.00"));
        transfer.setTransferDate(LocalDateTime.now());
        transferRepository.save(transfer);

        List<Transfer> result = transferRepository.findBySenderAccountId(1L);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testFindByTransferDateBefore() {
        Transfer transfer = new Transfer();
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("500.00"));
        transfer.setTransferDate(LocalDateTime.now().minusDays(2)); // Past date
        transferRepository.save(transfer);

        List<Transfer> result = transferRepository.findByTransferDateBefore(LocalDateTime.now());
        assertFalse(result.isEmpty());
    }
}
