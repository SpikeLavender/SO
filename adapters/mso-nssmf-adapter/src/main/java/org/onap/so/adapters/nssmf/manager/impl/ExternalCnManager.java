package org.onap.so.adapters.nssmf.manager.impl;


import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.EsrInfo;
import org.onap.so.beans.nsmf.NssiAllocateRequest;

public class ExternalCnManager extends DefaultCnNssiManager {

    public ExternalCnManager(EsrInfo esrInfo) {
        super(esrInfo);
    }

    @Override
    protected String doAllocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException {
        //todo:自己的其他逻辑
        return super.doAllocateNssi(allocateRequest);
    }
}
