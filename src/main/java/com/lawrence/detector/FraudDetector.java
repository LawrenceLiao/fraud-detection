package com.lawrence.detector;

import com.lawrence.model.FraudRecord;
import com.lawrence.model.DetectionWindow;
import com.lawrence.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FraudDetector {

    private static final int WINDOW_SIZE = 24;

    private final Map<String, DetectionWindow> memo = new HashMap<>();

    public FraudRecord detectByTransaction(Transaction transaction, BigDecimal threshold) {
        DetectionWindow window = getOrCreateWindow(transaction.accountNum());

        BigDecimal totalSpent = updateWindow(window, transaction);
        return totalSpent.compareTo(threshold) > 0 ?
                FraudRecord.builder()
                        .accountNum(transaction.accountNum())
                        .totalSpend(window.getSumOfWindow())
                        .timeStamp(transaction.timeStamp())
                        .build()
                :
                null;
    }

    private BigDecimal updateWindow(DetectionWindow window, Transaction transaction) {
        LocalDateTime startLimit = getStartLimit(transaction.timeStamp());
        window.popBefore(startLimit);
        window.add(transaction);
        return window.getSumOfWindow();
    }

    private LocalDateTime getStartLimit(LocalDateTime timeStamp) {
        return timeStamp.minusHours(WINDOW_SIZE);
    }

    private DetectionWindow getOrCreateWindow(String account) {
        memo.computeIfAbsent(account, key -> new DetectionWindow(account));
        return memo.get(account);
    }
}
