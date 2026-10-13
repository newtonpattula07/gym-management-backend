package com.gym.management;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class SampleData {
    @Bean
    CommandLineRunner seedMembers(MemberRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                LocalDate today = LocalDate.now();
                repository.save(new Member("Aarav Sharma", "aarav@example.com", "9876543210", "Premium", today.minusDays(12), today.plusDays(78), "ACTIVE"));
                repository.save(new Member("Priya Reddy", "priya@example.com", "9876543211", "Standard", today.minusDays(35), today.plusDays(55), "ACTIVE"));
                repository.save(new Member("Rahul Kumar", "rahul@example.com", "9876543212", "Basic", today.minusDays(120), today.minusDays(30), "EXPIRED"));
            }
        };
    }
}
