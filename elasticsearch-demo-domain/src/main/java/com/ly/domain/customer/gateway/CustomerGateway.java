package com.ly.domain.customer.gateway;

import com.ly.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
