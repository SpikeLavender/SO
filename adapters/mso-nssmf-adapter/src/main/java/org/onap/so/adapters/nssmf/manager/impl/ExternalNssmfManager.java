package org.onap.so.adapters.nssmf.manager.impl;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.onap.aai.domain.yang.ServiceInstance;
import org.onap.so.adapters.nssmf.entity.NssmfInfo;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.JobStatus;
import org.onap.so.adapters.nssmf.enums.SelectionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.extclients.aai.AaiServiceProvider;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.onap.so.adapters.nssmf.enums.JobStatus.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.unMarshal;

public abstract class ExternalNssmfManager extends BaseNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalNssmfManager.class);

    @Autowired
    private AaiServiceProvider aaiSvcProv;

    @Override
    protected String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return doWrapExtAllocateReqBody(nbiRequest);
    }

    @Override
    protected String wrapModifyReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return doWrapModifyReqBody(nbiRequest);
    }

    protected abstract String doWrapModifyReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    protected String wrapDeAllocateReqBody(DeAllocateNssi deAllocateNssi) throws ApplicationException{
        return doWrapDeAllocateReqBody(deAllocateNssi);
    }

    protected abstract String doWrapDeAllocateReqBody(DeAllocateNssi deAllocateNssi) throws ApplicationException;

    @Override
    protected void afterQueryJobStatus(NssmfAdapterNBIRequest jobReq, ResourceOperationStatus status) {
        if(Integer.getInteger(status.getProgress())== 100){
            ServiceInfo serviceInfo = jobReq.getServiceInfo();
            EsrInfo esrInfo = jobReq.getEsrInfo();
            org.onap.aai.domain.yang.ServiceInstance nssiInstance = new ServiceInstance();
            nssiInstance.setServiceInstanceId(serviceInfo.getNssiId());
            nssiInstance.setServiceInstanceName(serviceInfo.getNssiName());
            nssiInstance.setServiceType(serviceInfo.getSST());
            nssiInstance.setOrchestrationStatus(getInitalStatus());
            nssiInstance.setModelInvariantId(serviceInfo.getServiceInvariantUuid());
            nssiInstance.setModelVersionId(serviceInfo.getServiceUuid());
            nssiInstance.setServiceInstanceLocationId(serviceInfo.getPLMNIdList());
            nssiInstance.setEnvironmentContext(esrInfo.getNetworkType().getNetworkType());
            nssiInstance.setServiceRole("nssi");

            aaiSvcProv.invokeCreateServiceInstance(nssiInstance, serviceInfo.getGlobalSubscriberId(), serviceInfo.getSubscriptionServiceType(), serviceInfo.getNssiId());
        }

    }

    protected abstract String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    protected String wrapActDeActReqBody(ActDeActNssi actDeActNssi) throws ApplicationException {
        return marshal(actDeActNssi);
    }

    protected RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException{
        return doResponseStatus(status);
    }
    protected abstract RestResponse doResponseStatus(ResourceOperationStatus status) throws ApplicationException;

    @Override
    protected String wrapReqBody(Object object) throws ApplicationException {
        return marshal(object);
    }

    @Override
    protected RestResponse sendRequest(String content) throws ApplicationException {
        return sendExternalRequest(content);
    }

    @Override
    protected void handleResponse(RestResponse restResponse, String nsiId, String nsstId) throws ApplicationException {
        createStatus(restResponse, nsiId, nsstId);
    }

    private void createStatus(RestResponse restResponse, String nsiId, String nsstId) throws ApplicationException {
        if (valueOf(restResponse.getStatus()).startsWith("2")) {
            NssiResponse response = unMarshal(restResponse.getResponseContent(), NssiResponse.class);
            ResourceOperationStatus status = new ResourceOperationStatus(nsiId, response.getJobId(), nsstId);
            status.setResourceInstanceID(response.getNssiId());
            logger.info("save segment and operaton info -> begin");

            updateDbStatus(status, restResponse.getStatus(), STARTED, ALLOCATE_NSS_SUCCESS);
            logger.info("save segment and operaton info -> end");
        }
    }

    @Override
    protected String getApiVersion() {
        return "v1";
    }


    // external
    protected RestResponse sendExternalRequest(String content) throws ApplicationException {
        NssmfInfo nssmfInfo = restUtil.getNssmfHost(esrInfo);
        Header header = new BasicHeader("X-Auth-Token", restUtil.getToken(nssmfInfo));
        String nssmfUrl = nssmfInfo.getUrl() + this.nssmfUrl;
        return restUtil.send(nssmfUrl, this.httpMethod, content, header);
    }

    protected void updateDbStatus(ResourceOperationStatus status, int rspStatus, JobStatus jobStatus,
            String description) {
        status.setErrorCode(valueOf(rspStatus));
        status.setStatus(jobStatus.toString());
        status.setStatusDescription(description);
        logger.info("Updating DB status");
        repository.save(status);
        logger.info("Updating successful");
    }

    @Override
    protected RestResponse doQuerySubnetCapability(String req) throws ApplicationException {
        RestResponse response = new RestResponse();
        response.setStatus(200);
        response.setResponseContent(null);
        return response;
    }
}
