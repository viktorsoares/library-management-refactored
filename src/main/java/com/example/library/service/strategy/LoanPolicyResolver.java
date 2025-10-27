package com.example.library.service.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoanPolicyResolver {
    private final ApplicationContext ctx;
    public LoanPolicyResolver(ApplicationContext ctx) { this.ctx = ctx; }

    public LoanPolicy resolve(String role) {
        try {
            return ctx.getBean(role.toUpperCase(), LoanPolicy.class);
        } catch (Exception e) {
            return ctx.getBean("STUDENT", LoanPolicy.class); // fallback
        }
    }
}
