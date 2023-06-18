package com.kpo.springshaurma.service;

import com.kpo.springshaurma.model.ShaurmaOrder;
import org.springframework.ui.Model;

public interface ServiceSample {

    void modifyModel(Model model);
    void addShaurmaToOrder(ShaurmaOrder shaurmaOrder);
}
