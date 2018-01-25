package com.sygamer.starreduxgm.business.service;

import com.sygamer.starreduxgm.business.model.GmResult;

import java.util.Map;

/**
 * Created by LF on 2017/12/11 0011.
 */
public interface RechargeLogService {

    GmResult findRechargeList(Map<String, String[]> map);
}
