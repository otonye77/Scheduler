package com.peerless.peerless_bank.Controller;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    void testScheduleTransfer_Success() throws Exception {
        Transfer transfer = new Transfer();
        transfer.setSenderAccountId(1L);
        transfer.setRecipientAccountId(2L);
        transfer.setTransferAmount(new BigDecimal("1000"));
        transfer.setTransferDate(LocalDateTime.now().plusDays(1));

        doNothing().when(transferService).scheduleTransfer(any(Transfer.class));

        mockMvc.perform(post("/api/transfers/schedule")
                        .contentType("application/json")
                        .content("{\"senderAccountId\":1, \"recipientAccountId\":2, \"transferAmount\":1000, \"transferDate\":\"2025-01-01T10:00:00\"}"))
                .andExpect(status().isCreated());

        verify(transferService, times(1)).scheduleTransfer(any(Transfer.class));
    }

    @Test
    void testScheduleTransfer_InvalidDate() throws Exception {
        Transfer transfer = new Transfer();
        transfer.setTransferDate(LocalDateTime.now().minusDays(1)); // invalid date

        doThrow(new IllegalArgumentException("Transfer Date must be a future date")).when(transferService).scheduleTransfer(any(Transfer.class));

        mockMvc.perform(post("/api/transfers/schedule")
                        .contentType("application/json")
                        .content("{\"senderAccountId\":1, \"recipientAccountId\":2, \"transferAmount\":1000, \"transferDate\":\"2023-01-01T10:00:00\"}"))
                .andExpect(status().isBadRequest());
    }
}
