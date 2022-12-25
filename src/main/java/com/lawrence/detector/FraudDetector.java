package com.lawrence.detector;

import com.lawrence.model.AnalysisResult;
import com.lawrence.model.DetectionWindow;
import com.lawrence.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FraudDetector {

    private static final int WINDOW_SIZE = 24;

    private Map<String, DetectionWindow> memo = new HashMap<>();

    public AnalysisResult detectByTransaction(Transaction transaction, BigDecimal threshold) {
        DetectionWindow window = getOrCreateWindow(transaction.accountNum());

        BigDecimal totalSpent = updateWindow(window, transaction);
        return AnalysisResult.builder()
                .accountNum(transaction.accountNum())
                .totalSpend(window.getSumOfWindow())
                .isFraudulent(totalSpent.compareTo(threshold) > 0)
                .timeStamp(transaction.timeStamp())
                .build();
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
