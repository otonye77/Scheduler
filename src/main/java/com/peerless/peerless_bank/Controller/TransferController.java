package com.peerless.peerless_bank.Controller;

import com.peerless.peerless_bank.Entities.Transfer;
import com.peerless.peerless_bank.Service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleTransfer(@RequestBody Transfer transfer) {
        try {
            transferService.scheduleTransfer(transfer);
            return new ResponseEntity<>("Transfer scheduled successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/scheduled/{senderAccountId}")
    public ResponseEntity<List<Transfer>> getScheduledTransfers(@PathVariable Long senderAccountId) {
        List<Transfer> transfers = transferService.getScheduledTransfers(senderAccountId);
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @DeleteMapping("/cancel/{transferId}")
    public ResponseEntity<String> cancelScheduledTransfer(@PathVariable Long transferId) {
        try {
            transferService.cancelScheduledTransfer(transferId);
            return new ResponseEntity<>("Transfer cancelled successfully", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
