package com.peerless.peerless_bank.Service;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private TransferRepository transferRepository;

    @InjectMocks
    private TransferService transferService;

    private Transfer transfer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transfer = new Transfer();
        transfer.setTransferId(1L);
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("1000.00"));
        transfer.setTransferDate(LocalDateTime.now());
    }

    @Test
    void testScheduleTransfer() {
        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        transferService.scheduleTransfer(transfer);
        verify(transferRepository, times(1)).save(transfer);
    }

    @Test
    void testGetScheduledTransfers() {
        when(transferRepository.findBySenderAccountId(1L)).thenReturn(List.of(transfer));

        List<Transfer> result = transferService.getScheduledTransfers(1L);
        assertEquals(1, result.size());
        verify(transferRepository, times(1)).findBySenderAccountId(1L);
    }

    @Test
    void testCancelScheduledTransfer() {
        when(transferRepository.findById(1L)).thenReturn(Optional.of(transfer));
        doNothing().when(transferRepository).delete(transfer);

        transferService.cancelScheduledTransfer(1L);
        verify(transferRepository, times(1)).delete(transfer);
    }

    @Test
    void testCancelScheduledTransferNotFound() {
        when(transferRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transferService.cancelScheduledTransfer(1L));
    }
}
