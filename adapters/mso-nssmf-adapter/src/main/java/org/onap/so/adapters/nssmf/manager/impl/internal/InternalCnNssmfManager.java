package org.onap.so.adapters.nssmf.manager.impl.internal;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.InternalNssmfManager;
import org.onap.so.beans.nsmf.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public class InternalCnNssmfManager extends InternalNssmfManager {

    @Override
    protected String doWrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        ServiceInfo serviceInfo = nbiRequest.getServiceInfo();
        return marshal(new NssmfRequest(serviceInfo, nbiRequest.getEsrInfo().getNetworkType(),
                nbiRequest.getAllocateCnNssi()));
    }

}
