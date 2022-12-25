package com.lawrence.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record FraudRecord(String accountNum, BigDecimal totalSpend, LocalDateTime timeStamp) {
}
