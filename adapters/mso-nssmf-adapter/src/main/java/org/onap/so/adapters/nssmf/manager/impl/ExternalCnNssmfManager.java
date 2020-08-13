package org.onap.so.adapters.nssmf.manager.impl;


import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.db.request.beans.ResourceOperationStatus;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public class ExternalCnNssmfManager extends BaseNssmfManager {

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateCnNssi());
    }

    @Override
    protected String wrapReqBody(Object object) throws ApplicationException {
        return marshal(object);
    }

    @Override
    protected RestResponse doSendRequest(String content) throws ApplicationException {
        return super.sendExternalRequest(content);
    }

    @Override
    protected String getApiVersion() {
        return getExtApiVersion();
    }

    @Override
    protected void handleResponse(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        super.createStatus(restResponse, nsiId, nssiId);
    }

    @Override
    protected RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException {
        return super.handleExtStatus(status);
    }
}
