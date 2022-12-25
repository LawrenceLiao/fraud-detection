package com.lawrence.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Transaction(String accountNum, BigDecimal amount, LocalDateTime timeStamp) {
}
