package com.ly.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.ly.dto.CustomerAddCmd;
import com.ly.dto.CustomerListByNameQry;
import com.ly.dto.data.CustomerDTO;

public interface CustomerServiceI {

    Response addCustomer(CustomerAddCmd customerAddCmd);

    MultiResponse<CustomerDTO> listByName(CustomerListByNameQry customerListByNameQry);
}
