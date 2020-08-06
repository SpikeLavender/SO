package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.EsrInfo;
import org.onap.so.beans.nsmf.NssiAllocateRequest;

public class ExternalAnManager extends DefaultAnNssiManager {
    public ExternalAnManager(EsrInfo esrInfo) {
        super(esrInfo);
    }

    @Override
    protected String doAllocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException {

        return super.doAllocateNssi(allocateRequest);
    }

}
