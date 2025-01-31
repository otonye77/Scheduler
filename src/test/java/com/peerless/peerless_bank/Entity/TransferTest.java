package com.peerless.peerless_bank.Entity;

import com.peerless.peerless_bank.Entities.Transfer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void testPrePersist() {
        Transfer transfer = new Transfer();
        transfer.prePersist();

        assertNotNull(transfer.getCreatedAt());
        assertNotNull(transfer.getUpdatedAt());
        assertEquals(transfer.getCreatedAt(), transfer.getUpdatedAt());
    }

    @Test
    void testPreUpdate() throws InterruptedException {
        Transfer transfer = new Transfer();
        transfer.prePersist();

        LocalDateTime beforeUpdate = transfer.getUpdatedAt();
        Thread.sleep(1000);

        transfer.preUpdate();

        assertTrue(transfer.getUpdatedAt().isAfter(beforeUpdate));
    }
}
