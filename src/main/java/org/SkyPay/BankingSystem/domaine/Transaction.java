package org.SkyPay.BankingSystem.domaine;


import java.time.LocalDate;

public record Transaction(LocalDate date, int amount, int balance) {}