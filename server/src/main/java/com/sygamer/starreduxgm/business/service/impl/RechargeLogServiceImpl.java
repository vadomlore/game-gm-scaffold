package com.sygamer.starreduxgm.business.service.impl;

import com.sygamer.starreduxgm.business.model.GmResult;
import com.sygamer.starreduxgm.business.model.RechargeLog;
import com.sygamer.starreduxgm.business.repository.RechargeLogRepository;
import com.sygamer.starreduxgm.business.service.RechargeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by LF on 2017/12/11 0011.
 */
@Service("rechargeLog")
public class RechargeLogServiceImpl implements RechargeLogService {
    @Autowired
    private final RechargeLogRepository rechargeLogRepository;

    public RechargeLogServiceImpl(RechargeLogRepository rechargeLogRepository) {
        this.rechargeLogRepository = rechargeLogRepository;
    }

    @Override
    public GmResult findRechargeList(Map<String, String[]> map) {
        GmResult result = new GmResult();
        List<RechargeLog> list = rechargeLogRepository.findRechargeList(map);
        Integer total = rechargeLogRepository.getTotal("order_croods_1001_201712");
        if(list == null || list.size()<= 0){
            result.setStatus(2);
            result.setMsg("查询失败");
            return result;
        }
        result.setStatus(total);
        result.setMsg("查询成功");
        result.setData(list);
        return result;
    }
}
