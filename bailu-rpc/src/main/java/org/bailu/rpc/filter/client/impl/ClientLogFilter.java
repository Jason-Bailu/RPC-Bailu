package org.bailu.rpc.filter.client.impl;

import org.bailu.rpc.filter.FilterData;
import org.bailu.rpc.filter.client.ClientBeforeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientLogFilter implements ClientBeforeFilter {
    private Logger logger = LoggerFactory.getLogger(ClientBeforeFilter.class);

    @Override
    public void doFilter(FilterData filterData) {
        logger.info(filterData.toString());
    }
}
