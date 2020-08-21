package org.onap.so.adapters.nssmf.manager.impl.external;

import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.SelectionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.ExternalNssmfManager;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;


import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public class ExternalCnNssmfManager extends ExternalNssmfManager {

    @Override
    protected String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateCnNssi());
    }

    @Override
    protected SelectionType doQueryNSSISelectionCapability() {

        return SelectionType.NSMF;
    }
}
