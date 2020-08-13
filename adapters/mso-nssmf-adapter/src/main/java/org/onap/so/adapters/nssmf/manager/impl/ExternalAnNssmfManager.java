package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;


public class ExternalAnNssmfManager extends BaseNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalAnNssmfManager.class);

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateAnNssi());
    }

    @Override
    protected String wrapReqBody(Object object) throws ApplicationException {
        return marshal(object);
    }

    @Override
    protected RestResponse doSendRequest(String content) throws ApplicationException {
        return sendExternalRequest(content);
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
