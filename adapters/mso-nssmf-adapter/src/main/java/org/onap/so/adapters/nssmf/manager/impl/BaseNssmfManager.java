package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.consts.NssmfAdapterConsts;
import org.onap.so.adapters.nssmf.entity.NssmfUrlInfo;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.enums.SelectionType;
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

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public abstract class BaseNssmfManager implements NssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(BaseNssmfManager.class);

    protected RestUtil restUtil;

    protected ResourceOperationStatusRepository repository;

    private ActionType actionType;

    protected EsrInfo esrInfo;

    protected String nssmfUrl;

    protected HttpMethod httpMethod;

    private ExecutorType executorType = ExecutorType.INTERNAL;

    protected ServiceInfo serviceInfo;

    private Map<String, String> params = new HashMap<>(); // request params

    @Override
    public RestResponse allocateNssi(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        this.params.clear();
        this.urlHandler();

        String requestBody = wrapAllocateReqBody(nbiRequest);
        RestResponse restResponse = sendRequest(requestBody);
        handleResponse(restResponse, serviceInfo.getNsiId(), null);

        return restResponse;
    }

    protected abstract String wrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    @Override
    public RestResponse deAllocateNssi(NssmfAdapterNBIRequest nbiRequest, String sliceId) throws ApplicationException {
        // url处理
        this.params.clear();
        this.params.put("sliceId", sliceId);

        this.urlHandler();

        String reqBody = wrapReqBody(nbiRequest.getDeAllocateNssi());
        RestResponse restResponse = sendRequest(reqBody);
        handleResponse(restResponse, nbiRequest.getDeAllocateNssi().getNsiId(),
                nbiRequest.getDeAllocateNssi().getNssiId());
        return restResponse;
    }

    protected abstract String wrapReqBody(Object object) throws ApplicationException;

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

    protected abstract void handleResponse(RestResponse restResponse, String nsiId, String nssiId)
            throws ApplicationException;

    @Override
    public RestResponse queryJobStatus(JobStatusRequest jobReq, String jobId) throws ApplicationException {
        this.params.clear();
        this.params.put("jobId", jobId);
        this.params.put("responseId", jobReq.getResponseId());
        this.urlHandler();

        ResourceOperationStatus status = getOperationStatus(serviceInfo.getNssiId(), jobId, serviceInfo.getNsiId());
        /**
         * 内部的则查询状态，如果成功，没有查到就返回 “0” 外部的则查询并更新，在 aai 创建实例，没有查到就返回 “0” 如果失败的话 jobid
         */
        RestResponse res = doQueryJobStatus(status);
        afterQueryJobStatus();
        return res;
    }

    protected abstract RestResponse doQueryJobStatus(ResourceOperationStatus status) throws ApplicationException;


    protected abstract void afterQueryJobStatus();

    private ResourceOperationStatus getOperationStatus(String nssiId, String jobId, String nsiId)
            throws ApplicationException {
        ResourceOperationStatus status = new ResourceOperationStatus(nssiId, jobId, nsiId);
        return repository.findOne(Example.of(status))
                .orElseThrow(() -> new ApplicationException(404, "Cannot Find Operation Status"));
    }


    @Override
    public RestResponse queryNSSISelectionCapability(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        SelectionType res = doQueryNSSISelectionCapability();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("selection", res.name());
        RestResponse restResponse = new RestResponse();
        restResponse.setStatus(200);
        restResponse.setResponseContent(marshal(hashMap));
        return restResponse;
    }

    protected abstract SelectionType doQueryNSSISelectionCapability();

    @Override
    public RestResponse querySubnetCapability(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return doQuerySubnetCapability();
    }

    protected abstract RestResponse doQuerySubnetCapability() throws ApplicationException;

    // 发送请求
    protected abstract RestResponse sendRequest(String content) throws ApplicationException;

    private void urlHandler() {
        NssmfUrlInfo nssmfUrlInfo =
                NssmfAdapterConsts.getNssmfUrlInfo(this.executorType, this.esrInfo.getNetworkType(), actionType);
        this.nssmfUrl = nssmfUrlInfo.getUrl();
        this.httpMethod = nssmfUrlInfo.getHttpMethod();
        this.nssmfUrl = nssmfUrl.replaceAll("\\{apiVersion}", getApiVersion());
        this.params.forEach((k, v) -> this.nssmfUrl = this.nssmfUrl.replaceAll("\\{" + k + "}", v));
    }

    protected abstract String getApiVersion();

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
