package org.onap.so.adapters.nssmf.manager.impl.internal;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.InternalNssmfManager;
import org.onap.so.beans.nsmf.*;

import java.util.HashMap;
import java.util.Map;

import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;

public class InternalCnNssmfManager extends InternalNssmfManager {

    @Override
    protected String doWrapAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        ServiceInfo serviceInfo = nbiRequest.getServiceInfo();
        NssmfRequest request = new NssmfRequest(serviceInfo, nbiRequest.getEsrInfo().getNetworkType(),
                nbiRequest.getAllocateCnNssi());
        request.setName(nbiRequest.getAllocateCnNssi().getNssiName());
        return marshal(request);
    }

    @Override
    protected String doWrapModifyReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        ServiceInfo serviceInfo = nbiRequest.getServiceInfo();
        Map<String,Object> additional = new HashMap<>();
        additional.put("modifyAction", "allocate");
        additional.put("snssaiList", nbiRequest.getAllocateCnNssi().getSliceProfile().getSnssaiList());
        additional.put("sliceProfileId", nbiRequest.getAllocateCnNssi().getSliceProfile().getSliceProfileId());
        additional.put("nsiInfo",nbiRequest.getAllocateCnNssi().getNsiInfo());
        additional.put("scriptName",nbiRequest.getAllocateCnNssi().getScriptName());
        NssmfRequest request = new NssmfRequest(serviceInfo, nbiRequest.getEsrInfo().getNetworkType(),
                additional);
        request.setName(nbiRequest.getAllocateCnNssi().getNssiName());
        request.setServiceInstanceId(nbiRequest.getServiceInfo().getNssiId());
        return marshal(request);
    }

}
