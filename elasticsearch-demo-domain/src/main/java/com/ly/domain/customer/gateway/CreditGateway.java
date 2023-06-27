package com.ly.domain.customer.gateway;

import com.ly.domain.customer.Credit;

//Assume that the credit info is in another distributed Service
public interface CreditGateway {
    Credit getCredit(String customerId);
}
