package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.manager.NssiManger;
import org.onap.so.beans.nsmf.*;

public class DefaultCnNssiManager extends BaseNssiManager {

    public DefaultCnNssiManager(EsrInfo esrInfo) {
        super(esrInfo);
    }

    @Override
    protected <T> String doWrapReqBody(T t) {
        return null;
    }

    @Override
    protected String doAllocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException {
        return wrapReqBody(allocateRequest.getAllocateCnNssi());
    }

    @Override
    protected String doDeAllocateNssi(NssiDeAllocateRequest deAllocateRequest, String sliceId) throws ApplicationException {
        return null;
    }

    @Override
    protected String doTerminateNssi(NssiTerminateRequest terminateRequest, String nssiId) throws ApplicationException {
        return null;
    }

    @Override
    protected String doActivateNssi(NssiActDeActRequest deActRequest, String snssai) throws ApplicationException {
        return null;
    }

    @Override
    protected String doDeActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai) throws ApplicationException {
        return null;
    }

    @Override
    protected RestResponse doCreateNssi(NssiCreateRequest nssiCreat) throws ApplicationException {
        return sendRequest(wrapReqBody(nssiCreat.getCreateCnNssi()));
    }

    @Override
    protected String doUpdateNssi(NssiUpdateRequest nssiUpdate, String sliceId) throws ApplicationException {
        return null;
    }

    @Override
    public NssiManger create() {
        if (ExecutorType.INNER.equals(getExecutorType())) {
            return this;
        }
        return new ExternalCnManager(esrInfo);
    }
}
