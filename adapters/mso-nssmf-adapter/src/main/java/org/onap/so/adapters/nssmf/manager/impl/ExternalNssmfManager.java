package org.onap.so.adapters.nssmf.manager.impl;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.onap.so.adapters.nssmf.entity.NssmfInfo;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.JobStatus;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.beans.nsmf.JobStatusResponse;
import org.onap.so.beans.nsmf.NssiResponse;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.beans.nsmf.ResponseDescriptor;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.valueOf;
import static org.onap.so.adapters.nssmf.enums.JobStatus.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.unMarshal;

public abstract class ExternalNssmfManager extends BaseNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalNssmfManager.class);

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return doWrapExtAllocateReqBody(nbiRequest);
    }

    @Override
    protected void afterQueryJobStatus() {

    }

    protected abstract String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    protected RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException {
        return handleExtStatus(status);
    }

    @Override
    protected String wrapReqBody(Object object) throws ApplicationException {
        return marshal(object);
    }

    @Override
    protected RestResponse sendRequest(String content) throws ApplicationException {
        return sendExternalRequest(content);
    }

    @Override
    protected void handleResponse(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        createStatus(restResponse, nsiId, nssiId);
    }

    private void createStatus(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        if (valueOf(restResponse.getStatus()).startsWith("2")) {
            NssiResponse response = unMarshal(restResponse.getResponseContent(), NssiResponse.class);
            nssiId = nssiId == null ? response.getNssiId() : nssiId;
            ResourceOperationStatus status = new ResourceOperationStatus(nssiId, response.getJobId(), nsiId);
            logger.info("save segment and operaton info -> begin");

            updateDbStatus(status, restResponse.getStatus(), STARTED, ALLOCATE_NSS_SUCCESS);
            logger.info("save segment and operaton info -> end");
        }
    }

    @Override
    protected String getApiVersion() {
        return "v1";
    }

    private RestResponse handleExtStatus(ResourceOperationStatus status) throws ApplicationException {
        RestResponse restResponse = sendRequest(null);
        ResponseDescriptor rspDesc =
                unMarshal(restResponse.getResponseContent(), JobStatusResponse.class).getResponseDescriptor();
        updateRequestDbJobStatus(rspDesc, status, restResponse);
        return restResponse;
    }

    //external
    private RestResponse sendExternalRequest(String content) throws ApplicationException {
        NssmfInfo nssmfInfo = restUtil.getNssmfHost(esrInfo);
        Header header = new BasicHeader("X-Auth-Token", restUtil.getToken(nssmfInfo));
        String nssmfUrl = nssmfInfo.getUrl() + this.nssmfUrl;
        return restUtil.send(nssmfUrl, this.httpMethod, content, header);
    }

    private void updateRequestDbJobStatus(ResponseDescriptor rspDesc, ResourceOperationStatus status, RestResponse rsp)
            throws ApplicationException {

        switch (fromString(rspDesc.getStatus())) {
            case STARTED:
                updateDbStatus(status, rsp.getStatus(), STARTED, QUERY_JOB_STATUS_SUCCESS);
                break;
            case PROCESSING:
                updateDbStatus(status, rsp.getStatus(), PROCESSING, QUERY_JOB_STATUS_SUCCESS);
                break;
            case FINISHED:
                if (rspDesc.getProgress() == 100) {
                    updateDbStatus(status, rsp.getStatus(), FINISHED, QUERY_JOB_STATUS_SUCCESS);
                }
                break;
            case ERROR:
                updateDbStatus(status, rsp.getStatus(), ERROR, QUERY_JOB_STATUS_FAILED);
                throw new ApplicationException(500, QUERY_JOB_STATUS_FAILED);
        }
    }

    private void updateDbStatus(ResourceOperationStatus status, int rspStatus, JobStatus jobStatus,
                                String description) {
        status.setErrorCode(valueOf(rspStatus));
        status.setStatus(jobStatus.toString());
        status.setStatusDescription(description);
        logger.info("Updating DB status");
        repository.save(status);
        logger.info("Updating successful");
    }
}
