package org.onap.so.adapters.nssmf.manager;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.DefaultAnNssiManager;
import org.onap.so.adapters.nssmf.manager.impl.DefaultCnNssiManager;
import org.onap.so.adapters.nssmf.manager.impl.DefaultTnNssiManager;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.EsrInfo;

public class NssiManagerBuilder {

    private NssiManger nssiManger;

    private RestUtil restUtil;

    private ActionType actionType;

    public NssiManagerBuilder(EsrInfo esrInfo) throws ApplicationException {
        switch (esrInfo.getNetworkType()) {
            case ACCESS:
                this.nssiManger = new DefaultAnNssiManager(esrInfo);
                break;
            case CORE:
                this.nssiManger = new DefaultCnNssiManager(esrInfo);
                break;
            case TRANSPORT:
                this.nssiManger = new DefaultTnNssiManager(esrInfo);
                break;
            default:
                throw new ApplicationException(501, "invalid domain type: " + esrInfo.getNetworkType().name());
        }
    }


    public NssiManagerBuilder setRestUtil(RestUtil restUtil) {

        this.restUtil = restUtil;
        return this;
    }

    public NssiManagerBuilder setActionType(ActionType actionType) {

        this.actionType = actionType;

        return this;
    }


    public NssiManger build() throws ApplicationException {
        return this.nssiManger.create().setRestUtil(restUtil).setActionType(actionType);
    }
}
