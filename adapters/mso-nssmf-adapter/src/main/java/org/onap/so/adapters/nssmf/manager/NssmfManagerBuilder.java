package org.onap.so.adapters.nssmf.manager;

import org.onap.so.adapters.nssmf.consts.NssmfConsts;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.*;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.EsrInfo;
import org.onap.so.beans.nsmf.NetworkType;
import org.onap.so.beans.nsmf.ServiceInfo;
import org.onap.so.db.request.data.repository.ResourceOperationStatusRepository;

public class NssmfManagerBuilder {

    private BaseNssmfManager nssmfManger;

    private RestUtil restUtil;

    private ActionType actionType;

    private ResourceOperationStatusRepository repository;

    private ServiceInfo serviceInfo;

    public NssmfManagerBuilder (EsrInfo esrInfo) throws ApplicationException {

        ExecutorType executorType = getExecutorType(esrInfo);
        NetworkType networkType = esrInfo.getNetworkType();

        if (ExecutorType.INTERNAL.equals(executorType) && NetworkType.CORE.equals(networkType)) {
            this.nssmfManger = new InternalCnNssmfManager().setEsrInfo(esrInfo).setExecutorType(executorType);
        }

        if (ExecutorType.INTERNAL.equals(executorType) && NetworkType.TRANSPORT.equals(networkType)) {
            this.nssmfManger = new InternalTnNssmfManager().setEsrInfo(esrInfo).setExecutorType(executorType);
        }

        if (ExecutorType.INTERNAL.equals(executorType) && NetworkType.ACCESS.equals(networkType)) {
            this.nssmfManger =  new InternalAnNssmfManager().setEsrInfo(esrInfo).setExecutorType(executorType);
        }

        if (ExecutorType.EXTERNAL.equals(executorType) && NetworkType.CORE.equals(networkType)) {
            this.nssmfManger =  new ExternalCnNssmfManager().setEsrInfo(esrInfo).setExecutorType(executorType);
        }

        if (ExecutorType.EXTERNAL.equals(executorType) && NetworkType.ACCESS.equals(networkType)) {
            this.nssmfManger =  new ExternalAnNssmfManager().setEsrInfo(esrInfo).setExecutorType(executorType);
        }

        throw new ApplicationException(404, "invalid domain and simulator");
    }

    private ExecutorType getExecutorType(EsrInfo esrInfo) {
        if (NssmfConsts.ONAP_INTERNAL_TAG.equals(esrInfo.getVendor())){
            return ExecutorType.INTERNAL;
        }
        return ExecutorType.EXTERNAL;
    }

    public NssmfManagerBuilder setRestUtil(RestUtil restUtil) {
        this.restUtil = restUtil;
        return this;
    }

    public NssmfManagerBuilder setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public NssmfManagerBuilder setRepository(ResourceOperationStatusRepository repository) {
        this.repository = repository;
        return this;
    }

    public NssmfManagerBuilder setServiceInfo(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
        return this;
    }

    public NssmfManager build() {
        return this.nssmfManger.setRestUtil(restUtil).setRepository(repository).setActionType(actionType).setServiceInfo(serviceInfo);
    }
}
