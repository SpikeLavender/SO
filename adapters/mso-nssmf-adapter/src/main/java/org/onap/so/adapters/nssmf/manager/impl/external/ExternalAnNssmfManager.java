package org.onap.so.adapters.nssmf.manager.impl.external;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.ExternalNssmfManager;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;


public class ExternalAnNssmfManager extends ExternalNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalAnNssmfManager.class);

    @Override
    protected String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateAnNssi());
    }
}
