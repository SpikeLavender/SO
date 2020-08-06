package org.onap.so.adapters.nssmf.manager.impl;

import org.onap.so.adapters.nssmf.consts.NssmfConsts;
import org.onap.so.adapters.nssmf.entity.NssmfUrlInfo;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.enums.HttpMethod;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.manager.NssiManger;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.*;

public abstract class BaseNssiManager implements NssiManger {

    private RestUtil restUtil;

    private ActionType actionType;

    protected EsrInfo esrInfo;

    private String nssmfUrl;

    private HttpMethod httpMethod;

    public BaseNssiManager(EsrInfo esrInfo) {
        this.esrInfo = esrInfo;
    }

    public RestResponse allocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException {

        this.urlHandler();
        String reqBody = doAllocateNssi(allocateRequest);

        return sendRequest(reqBody);
    }

    protected abstract String doAllocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException;

    @Override
    public RestResponse deAllocateNssi(NssiDeAllocateRequest deAllocateRequest, String sliceId) throws ApplicationException {
        //url处理
        this.urlHandler(sliceId);

        String reqBody = doDeAllocateNssi(deAllocateRequest, sliceId);

        return sendRequest(reqBody);
    }

    protected abstract String doDeAllocateNssi(NssiDeAllocateRequest deAllocateRequest, String sliceId) throws ApplicationException;

    @Override
    public RestResponse terminateNssi(NssiTerminateRequest terminateRequest, String nssiId) throws ApplicationException {
        this.urlHandler(nssiId);

        String reqBody = doTerminateNssi(terminateRequest, nssiId);

        return sendRequest(reqBody);
    }

    protected abstract String doTerminateNssi(NssiTerminateRequest terminateRequest, String nssiId) throws ApplicationException;

    @Override
    public RestResponse activateNssi(NssiActDeActRequest deActRequest, String snssai) throws ApplicationException {
        this.urlHandler(snssai);

        String reqBody = doActivateNssi(deActRequest, snssai);

        return sendRequest(reqBody);
    }

    protected abstract String doActivateNssi(NssiActDeActRequest deActRequest, String snssai) throws ApplicationException;

    @Override
    public RestResponse deActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai) throws ApplicationException {
        this.urlHandler(snssai);

        String reqBody = doDeActivateNssi(nssiDeActivate, snssai);

        return sendRequest(reqBody);
    }

    protected abstract String doDeActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai) throws ApplicationException;

    @Override
    public RestResponse queryJobStatus(JobStatusRequest jobReq, String jobId) {
        return null;
    }

    @Override
    public RestResponse createNssi(NssiCreateRequest nssiCreate) throws ApplicationException {
        return doCreateNssi(nssiCreate);
    }

    protected abstract RestResponse doCreateNssi(NssiCreateRequest nssiCreat) throws ApplicationException;

    @Override
    public RestResponse updateNssi(NssiUpdateRequest nssiUpdate, String sliceId) throws ApplicationException {
        this.urlHandler(sliceId);

        String reqBody = doUpdateNssi(nssiUpdate, sliceId);

        return sendRequest(reqBody);
    }

    protected abstract String doUpdateNssi(NssiUpdateRequest nssiUpdate, String sliceId) throws ApplicationException;

    @Override
    public RestResponse updateNssiById(NssiUpdateRequestById nssiUpdateById, String nssiId) {
        return null;
    }

    protected abstract <T> String doWrapReqBody(T t);

    //发送请求
    protected RestResponse sendRequest(String content) throws ApplicationException {

        return restUtil.sendRequest(this.nssmfUrl, this.httpMethod, content, this.esrInfo);
    }

    //获取url
    //protected abstract<T> String getUrlInfo(Class<T> clazz) throws Exception; //配置类，读配置文件吧      //todo：config类读url

    private void urlHandler(String... vars) {
        NssmfUrlInfo nssmfUrlInfo = NssmfConsts.getNssmfUrlInfo(getExecutorType(), this.esrInfo.getNetworkType(), actionType);
        this.nssmfUrl = nssmfUrlInfo.getUrl();
        this.httpMethod = nssmfUrlInfo.getHttpMethod();
        this.afterUrlHandler(vars);
    }

    private void afterUrlHandler(String... vars) {
        String url = this.nssmfUrl;
        for (String var : vars) {
            url = String.format(url, var);
        }
        this.nssmfUrl = url;
    }

    //包装请求体
    protected <T> String wrapReqBody(T t) throws ApplicationException {
        if (getExecutorType().equals(ExecutorType.THIRD_PARTY)) {
            return doWrapThirdPartyReqBody(t);
        }
        return doWrapReqBody(t);
    }

    private  <T> String doWrapThirdPartyReqBody(T t) throws ApplicationException {
        return marshal(t);
    }


    protected ExecutorType getExecutorType() {
        ExecutorType executorType;

        if (esrInfo.getVendor().equals("ONAP_SO_INNER")) {
            executorType = ExecutorType.INNER;
        } else  {
            executorType = ExecutorType.THIRD_PARTY;
        }
        return executorType;
    }


    @Override
    public NssiManger setRestUtil(RestUtil restUtil) {
        this.restUtil = restUtil;
        return this;
    }

    @Override
    public NssiManger setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }
}
