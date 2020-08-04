package org.onap.so.adapters.nssmf.manager;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.AnNssiManager;
import org.onap.so.adapters.nssmf.manager.impl.CnNssiManager;
import org.onap.so.adapters.nssmf.manager.impl.TnNssiManager;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.EsrInfo;

public class NssiManagerBuilder {

    private EsrInfo esrInfo;

    private NssiManger nssiManger;

    public NssiManagerBuilder(EsrInfo esrInfo) throws ApplicationException {
        this.esrInfo = esrInfo;
        creatNssiManager();
        this.nssiManger.setEsrInfo(esrInfo);
    }

    public NssiManagerBuilder setRestUtil(RestUtil restUtil) {

        this.nssiManger.setRestUtil(restUtil);

        return this;
    }

    public NssiManagerBuilder setActionType(ActionType actionType) {

        this.nssiManger.setActionType(actionType);

        return this;
    }

    private void creatNssiManager() throws ApplicationException {

        switch (this.esrInfo.getNetworkType()) {
            case ACCESS:
                this.nssiManger = new AnNssiManager();
                break;
            case CORE:
                this.nssiManger = new CnNssiManager();
                break;
            case TRANSPORT:
                this.nssiManger = new TnNssiManager();
                break;
            default:
                throw new ApplicationException(501, "invalid domain type: " + esrInfo.getNetworkType().name());
        }
    }

    public NssiManger build() {
        return this.nssiManger;
    }
}
