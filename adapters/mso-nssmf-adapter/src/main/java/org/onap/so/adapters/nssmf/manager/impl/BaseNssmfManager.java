package org.onap.so.adapters.nssmf.manager.impl;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.onap.so.adapters.nssmf.consts.NssmfAdapterConsts;
import org.onap.so.adapters.nssmf.entity.NssmfInfo;
import org.onap.so.adapters.nssmf.entity.NssmfUrlInfo;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.enums.JobStatus;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.enums.HttpMethod;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.manager.NssmfManager;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.onap.so.db.request.data.repository.ResourceOperationStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.onap.so.adapters.nssmf.enums.JobStatus.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.QUERY_JOB_STATUS_SUCCESS;

public abstract class BaseNssmfManager implements NssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(BaseNssmfManager.class);

    private RestUtil restUtil;

    private ResourceOperationStatusRepository repository;

    private ActionType actionType;

    private EsrInfo esrInfo;

    private String nssmfUrl;

    private HttpMethod httpMethod;

    private ExecutorType executorType = ExecutorType.INTERNAL;

    private ServiceInfo serviceInfo;

    private Map<String, String> params = new HashMap<>();   //request params

    @Override
    public RestResponse allocateNssi(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        this.params.clear();
        this.urlHandler();

        String requestBody = wrapAllocateReqBody(nbiRequest);
        RestResponse restResponse = sendRequest(requestBody);
        handleResponse(restResponse, nbiRequest.getNsiId(), null);

        return restResponse;
    }

    protected abstract String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    public RestResponse deAllocateNssi(NssmfAdapterNBIRequest nbiRequest, String sliceId) throws ApplicationException {
        //url处理
        this.params.clear();
        this.params.put("sliceId", sliceId);

        this.urlHandler();

        String reqBody = wrapReqBody(nbiRequest.getDeAllocateNssi());
        RestResponse restResponse = sendRequest(reqBody);
        handleResponse(restResponse, nbiRequest.getDeAllocateNssi().getNsiId(), nbiRequest.getDeAllocateNssi().getNssiId());
        return restResponse;
    }

    protected String wrapReqBody(Object object) throws ApplicationException {
        NssmfRequest nssmfRequest = new NssmfRequest(serviceInfo, esrInfo.getNetworkType(), object);
        return marshal(nssmfRequest);
    }


    @Override
    public RestResponse activateNssi(NssmfAdapterNBIRequest nbiRequest, String snssai) throws ApplicationException {
        this.params.clear();
        this.params.put("snssai", snssai);

        this.urlHandler();

        String reqBody = wrapReqBody(nbiRequest.getActDeActNssi());
        RestResponse restResponse = sendRequest(reqBody);

        handleResponse(restResponse, nbiRequest.getActDeActNssi().getNsiId(), nbiRequest.getActDeActNssi().getNssiId());
        return restResponse;
    }

    @Override
    public RestResponse deActivateNssi(NssmfAdapterNBIRequest nbiRequest, String snssai) throws ApplicationException {
        return activateNssi(nbiRequest, snssai);
    }

    protected void createStatus(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        if (valueOf(restResponse.getStatus()).startsWith("2")) {
            NssiResponse response = unMarshal(restResponse.getResponseContent(), NssiResponse.class);
            nssiId = nssiId == null ? response.getNssiId() : nssiId;
            ResourceOperationStatus status = new ResourceOperationStatus(nssiId, response.getJobId(), nsiId);
            logger.info("save segment and operaton info -> begin");

            updateDbStatus(status, restResponse.getStatus(), STARTED, ALLOCATE_NSS_SUCCESS);
            logger.info("save segment and operaton info -> end");
        }
    }

    protected void handleResponse(RestResponse restResponse, String nsiId, String nssiId) throws ApplicationException {
        //内部的时候不走
    }

    @Override
    public RestResponse queryJobStatus(JobStatusRequest jobReq, String jobId) throws ApplicationException {
        this.params.clear();
        this.params.put("jobId", jobId);
        this.params.put("responseId", jobReq.getResponseId());
        this.urlHandler();

        ResourceOperationStatus status = getOperationStatus(jobReq.getNssiId(), jobId, jobReq.getNsiId());
        /**
         * 内部的则查询状态，如果成功，没有查到就返回 “0”
         * 外部的则查询并更新，在 aai 创建实例，没有查到就返回 “0”
         * 如果失败的话 jobid
         */
        RestResponse res = doQueryJobStatus(status);
        afterQueryJobStatus();
        return res;
    }

    protected abstract RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException;

    protected RestResponse handleInnerStatus(ResourceOperationStatus status) throws ApplicationException {
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
        //descriptor.setResponseId(status.getOperationId());
        return restUtil.createResponse(200, marshal(descriptor));
    }

    protected RestResponse handleExtStatus(ResourceOperationStatus status) throws ApplicationException {
        RestResponse restResponse = sendRequest(null);
        ResponseDescriptor rspDesc =
                unMarshal(restResponse.getResponseContent(), JobStatusResponse.class).getResponseDescriptor();
        updateRequestDbJobStatus(rspDesc, status, restResponse);
        return restResponse;
    }

    private void afterQueryJobStatus() {
        //需要就去实现
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

    private ResourceOperationStatus getOperationStatus(String nssiId, String jobId, String nsiId) throws ApplicationException {
        ResourceOperationStatus status = new ResourceOperationStatus(nssiId, jobId, nsiId);
        return repository.findOne(Example.of(status))
                .orElseThrow(() -> new ApplicationException(404, "Cannot Find Operation Status"));
    }


    //发送请求
    protected RestResponse sendRequest(String content) throws ApplicationException {

        return doSendRequest(content);
    }

    protected abstract RestResponse doSendRequest(String content) throws ApplicationException;

    //internal
    protected RestResponse sendInternalRequest(String content) {
        //todo: read from config
        Header header = new BasicHeader("X-Auth-Token", "");
        this.nssmfUrl = "" + this.nssmfUrl;
        return restUtil.send(this.nssmfUrl, this.httpMethod, content, header);
    }

    //external
    protected RestResponse sendExternalRequest(String content) throws ApplicationException {
        NssmfInfo nssmfInfo = restUtil.getNssmfHost(esrInfo);
        Header header = new BasicHeader("X-Auth-Token", restUtil.getToken(nssmfInfo));
        String nssmfUrl = nssmfInfo.getUrl() + this.nssmfUrl;
        return restUtil.send(nssmfUrl, this.httpMethod, content, header);
    }


    private void urlHandler() {
        NssmfUrlInfo nssmfUrlInfo = NssmfAdapterConsts.getNssmfUrlInfo(this.executorType, this.esrInfo.getNetworkType(), actionType);
        this.nssmfUrl = nssmfUrlInfo.getUrl();
        this.httpMethod = nssmfUrlInfo.getHttpMethod();
        this.nssmfUrl = nssmfUrl.replaceAll("\\{apiVersion}", getApiVersion());
        this.params.forEach((k, v) -> this.nssmfUrl = this.nssmfUrl.replaceAll("\\{" + k + "}", v));
    }

    protected abstract String getApiVersion();

    protected String getExtApiVersion() {
        //todo: get from esrInfo
        return "v1";
    }

    public RestUtil getRestUtil() {
        return restUtil;
    }

    public BaseNssmfManager setEsrInfo(EsrInfo esrInfo) {
        this.esrInfo = esrInfo;
        return this;
    }

    public BaseNssmfManager setExecutorType(ExecutorType executorType) {
        this.executorType = executorType;
        return this;
    }

    public BaseNssmfManager setRestUtil(RestUtil restUtil) {
        this.restUtil = restUtil;
        return this;
    }

    public BaseNssmfManager setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public BaseNssmfManager setRepository(ResourceOperationStatusRepository repository) {
        this.repository = repository;
        return this;
    }

    public BaseNssmfManager setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
        return this;
    }
}
