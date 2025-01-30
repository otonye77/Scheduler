package com.peerless.peerless_bank.Service;

import com.peerless.peerless_bank.Entities.Transfer;

import java.util.List;

public interface ITransferService {
    void scheduleTransfer (Transfer transfer);
    List<Transfer> getScheduledTransfers(Long senderAccountId);
    void processDueTransfer();
}
