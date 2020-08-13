package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.consts.NssmfAdapterConsts;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.beans.ResourceOperationStatus;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public class InternalTnNssmfManager extends BaseNssmfManager {

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        ServiceInfo serviceInfo = nbiRequest.getServiceInfo();
        return marshal(new NssmfRequest(serviceInfo, nbiRequest.getEsrInfo().getNetworkType(), nbiRequest.getAllocateTnNssi()));
    }

    @Override
    protected RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException {
        return super.handleInnerStatus(status);
    }

    @Override
    protected RestResponse doSendRequest(String content) {
        return super.sendInternalRequest(content);
    }

    @Override
    protected String getApiVersion() {
        return NssmfAdapterConsts.CURRENT_INTERNAL_NSSMF_API_VERSION;
    }


}
