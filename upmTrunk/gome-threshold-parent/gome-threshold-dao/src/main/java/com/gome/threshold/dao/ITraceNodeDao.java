package com.gome.threshold.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.gome.threshold.domain.TraceNodeInfo;
import com.gome.threshold.domain.TraceNodesResult;
import com.gome.threshold.domain.TraceSearchInfo;

public interface ITraceNodeDao {
	TraceNodesResult queryTraceNodesByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	Collection<TraceNodeInfo> queryEntranceNodeByTraceId(String traceId)
			throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	public TraceSearchInfo getTracesByBussinessKey(String businessKey, int from, int size);
}
