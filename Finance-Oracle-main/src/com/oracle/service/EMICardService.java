package com.oracle.service;

import com.oracle.beans.EMICard;

public interface EMICardService {
    EMICard getCardByUserId(Long userId);
}
