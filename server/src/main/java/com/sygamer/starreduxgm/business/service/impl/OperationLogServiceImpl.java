package com.sygamer.starreduxgm.business.service.impl;

import com.sygamer.starreduxgm.business.model.GmResult;
import com.sygamer.starreduxgm.business.model.OperationLog;
import com.sygamer.starreduxgm.business.repository.OperationLogRepository;
import com.sygamer.starreduxgm.business.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by LF on 2017/12/4 0004.
 */
@Service("operationLog")
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    private final OperationLogRepository operationLogRepository;

    public OperationLogServiceImpl(OperationLogRepository operationLogRepository) {
        this.operationLogRepository = operationLogRepository;
    }

    @Override
    public GmResult record(OperationLog operationLog) {
        GmResult result = new GmResult();
        Integer logNums = operationLogRepository.record(operationLog);
        if (logNums == null || logNums <= 0) {
            result.setStatus(1);
            result.setMsg("发送失败");
            return result;
        }
        result.setStatus(2);
        result.setMsg("发送成功");
        return result;
    }

    @Override
    public GmResult findEmailList(Map<String, String[]> map) {
        GmResult result = new GmResult();
        List<OperationLog> list = operationLogRepository.findEmailList(map);
        Integer total = operationLogRepository.getTotalByType("operationlog", "emailLog");
        if (list == null || list.size() <= 0) {
            result.setStatus(2);
            result.setMsg("查询失败");
            return result;
        }
        result.setStatus(total);
        result.setMsg("查询成功");
        result.setData(list);
        return result;
    }

    @Override
    public GmResult findObjectsList(Map<String, String[]> map) {
        GmResult result = new GmResult();
        List<OperationLog> list = operationLogRepository.findRecordList(map);
        Integer total = operationLogRepository.getTotalByType("operationlog",map.get("optype")[0]);
        if (list == null || list.size() <= 0) {
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
