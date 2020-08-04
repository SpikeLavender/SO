package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.beans.nsmf.*;

public class AnNssiManager extends BaseNssiManager {

    @Override
    protected <T> String doWrapReqBody(T t) {
        //
        return null;
    }

    @Override
    protected String doAllocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException {
        return wrapReqBody(allocateRequest.getAllocateTnNssi());
    }

    @Override
    protected String doDeAllocateNssi(NssiDeAllocateRequest deAllocateRequest, String sliceId) throws ApplicationException {
        return wrapReqBody(deAllocateRequest.getDeAllocateNssi());
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
        return null;
    }

    @Override
    protected String doUpdateNssi(NssiUpdateRequest nssiUpdate, String sliceId) throws ApplicationException {
        return null;
    }
}
