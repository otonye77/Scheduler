package com.peerless.peerless_bank.Service;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransferService implements  ITransferService {
    private final TransferRepository transferRepository;

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public void scheduleTransfer(Transfer transfer) {
        if(transfer.getTransferDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Transfer Date must be a future date");
        }
        if(transfer.getTransferAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getScheduledTransfers(Long senderAccountId) {
        return transferRepository.findBySenderAccountId(senderAccountId);
    }

    @Override
    @Transactional
    public void processDueTransfer() {
        List<Transfer> dueTransfers = transferRepository.findByTransferDateBefore(LocalDateTime.now());
        for (Transfer transfer : dueTransfers) {
            System.out.println("Processing transfer ID: " + transfer.getTransferId());
            System.out.println("Sender Account: " + transfer.getSenderAccountId());
            System.out.println("Recipient Account: " + transfer.getRecipientAccountId());
            System.out.println("Amount: " + transfer.getTransferAmount());
        }
    }

    @Override
    public void cancelScheduledTransfer(Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new IllegalArgumentException("Transfer not found"));

        if (transfer.getTransferDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot cancel transfer, it has already been processed.");
        }

        transferRepository.delete(transfer);
    }
}