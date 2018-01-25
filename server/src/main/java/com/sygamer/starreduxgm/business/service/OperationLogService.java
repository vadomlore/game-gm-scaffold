package com.sygamer.starreduxgm.business.service;

import com.sygamer.starreduxgm.business.model.GmResult;
import com.sygamer.starreduxgm.business.model.OperationLog;

import java.util.List;
import java.util.Map;

/**
 * Created by LF on 2017/12/4 0004.
 */
public interface OperationLogService {

    GmResult record(final OperationLog operationLog);

    GmResult findEmailList(Map<String, String[]> map);

    GmResult findObjectsList(Map<String, String[]> map);
}
