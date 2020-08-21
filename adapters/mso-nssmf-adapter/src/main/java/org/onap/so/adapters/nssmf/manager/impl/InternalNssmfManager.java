package org.onap.so.adapters.nssmf.manager.impl;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.onap.so.adapters.nssmf.consts.NssmfAdapterConsts;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.SelectionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.beans.nsmf.NssmfRequest;
import org.onap.so.beans.nsmf.ResponseDescriptor;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static org.onap.so.adapters.nssmf.enums.JobStatus.PROCESSING;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public abstract class InternalNssmfManager extends BaseNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(InternalNssmfManager.class);

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return doWrapAllocateReqBody(nbiRequest);
    }

    protected abstract String doWrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    protected String wrapReqBody(Object object) throws ApplicationException {
        NssmfRequest nssmfRequest = new NssmfRequest(serviceInfo, esrInfo.getNetworkType(), object);
        return marshal(nssmfRequest);
    }

    @Override
    protected RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException {
        return handleInnerStatus(status);
    }

    @Override
    protected RestResponse sendRequest(String content) throws ApplicationException {
        // todo: read from config
        return sendInternalRequest(content);
    }

    @Override
    protected void afterQueryJobStatus() {
        // internal
    }

    private RestResponse handleInnerStatus(ResourceOperationStatus status) throws ApplicationException {
        ResponseDescriptor descriptor = new ResponseDescriptor();
        if (status == null) {
            descriptor.setProgress(0);
            descriptor.setStatus(PROCESSING.name());
            descriptor.setStatusDescription("Initiating Nssi Instance");
            return restUtil.createResponse(200, marshal(descriptor));
        }
        descriptor.setStatus(status.getStatus());
        descriptor.setStatusDescription(status.getStatusDescription());
        descriptor.setProgress(Integer.parseInt(status.getProgress()));
        // descriptor.setResponseId(status.getOperationId());
        return restUtil.createResponse(200, marshal(descriptor));
    }

    @Override
    protected void handleResponse(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        // 不用处理
    }

    // internal
    private RestResponse sendInternalRequest(String content) {
        // todo: read from config
        Header header = new BasicHeader("X-Auth-Token", "");
        this.nssmfUrl = "" + this.nssmfUrl;
        return restUtil.send(this.nssmfUrl, this.httpMethod, content, header);
    }

    @Override
    protected String getApiVersion() {
        return NssmfAdapterConsts.CURRENT_INTERNAL_NSSMF_API_VERSION;
    }


    @Override
    protected SelectionType doQueryNSSISelectionCapability() {
        return SelectionType.NSSMF;
    }

    @Override
    protected RestResponse doQuerySubnetCapability(String req) throws ApplicationException {
        //handler
        return sendRequest(req);
    }
}
