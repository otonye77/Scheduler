package com.peerless.peerless_bank.Service;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Repository.TransferRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testScheduleTransfer_Success() {
        Transfer transfer = new Transfer();
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("1000"));
        transfer.setTransferDate(LocalDateTime.now().plusDays(1));

        transferService.scheduleTransfer(transfer);
        verify(transferRepository, times(1)).save(transfer);
    }

    @Test
    void testScheduleTransfer_InvalidDate() {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDateTime.now().minusDays(1));

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transferService.scheduleTransfer(transfer);
        });

        Assertions.assertEquals("Transfer Date must be a future date", exception.getMessage());
    }

    @Test
    void testScheduleTransfer_InvalidAmount() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(BigDecimal.ZERO);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            transferService.scheduleTransfer(transfer);
        });

        Assertions.assertEquals("Transfer amount must be positive.", exception.getMessage());
    }
}
