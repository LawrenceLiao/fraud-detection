package com.lawrence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

@Data
@NoArgsConstructor
public class DetectionWindow {

    private String accountNum;
    private BigDecimal sumOfWindow = BigDecimal.ZERO;
    private Queue<Transaction> window = new LinkedList<>();

    public DetectionWindow(String accountNum) {
        this.accountNum = accountNum;
    }

    public Transaction getFirst() {
        if (window.isEmpty()) {
            return null;
        }
        return window.peek();
    }

    public Transaction popFirst() {
        if (window.isEmpty()) {
            return null;
        }
        Transaction transaction = window.poll();
        sumOfWindow = sumOfWindow.subtract(transaction.amount());
        return window.poll();
    }

    public void popBefore(LocalDateTime dateTime) {
        while (!window.isEmpty() && window.peek().timeStamp().isBefore(dateTime)) {
            popFirst();
        }
    }

    public void add(Transaction transaction) {
        window.offer(transaction);
        sumOfWindow = sumOfWindow.add(transaction.amount());
    }
}
