package com.peerless.peerless_bank.Service;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements  ITransferService {
    private final TransferRepository transferRepository;

    @Override
    public void scheduleTransfer(Transfer transfer) {

    }

    @Override
    public List<Transfer> getScheduledTransfers(Long senderAccountId) {
        return List.of();
    }

    @Override
    public void processDueTransfer() {

    }
}
