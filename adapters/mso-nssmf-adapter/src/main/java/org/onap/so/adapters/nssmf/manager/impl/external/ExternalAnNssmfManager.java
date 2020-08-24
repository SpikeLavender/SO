package org.onap.so.adapters.nssmf.manager.impl.external;

import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.SelectionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.ExternalNssmfManager;
import org.onap.so.beans.nsmf.DeAllocateNssi;
import org.onap.so.beans.nsmf.NssiResponse;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

import static java.lang.String.valueOf;
import static org.onap.so.adapters.nssmf.enums.JobStatus.FINISHED;
import static org.onap.so.adapters.nssmf.enums.JobStatus.STARTED;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.ALLOCATE_NSS_SUCCESS;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.unMarshal;


public class ExternalAnNssmfManager extends ExternalNssmfManager {

    private static final Logger logger = LoggerFactory.getLogger(ExternalAnNssmfManager.class);
	
    @Override
    protected String getInitalStatus() {
        return "activated";
    }
   
    @Override
    protected String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateAnNssi().getSliceProfile());
    }

    @Override
    protected RestResponse sendRequest(String content) throws ApplicationException {
        RestResponse restResponse = sendExternalRequest(content);
        Map<String,String> response = unMarshal(restResponse.getResponseContent(), Map.class);

        RestResponse returnRsp = new RestResponse();
        Map<String,String> rsp = new HashMap<>();
        rsp.put("nSSId",response.get("nSSId"));
        rsp.put("jobId",response.get("nSSId"));
        returnRsp.setStatus(200);
        returnRsp.setResponseContent(marshal(rsp));
        return returnRsp;
    }


    @Override
    protected void handleResponse(RestResponse restResponse, String nsiId, String nsstId) throws ApplicationException {
        if (valueOf(restResponse.getStatus()).startsWith("2")) {
            Map<String,String> response = unMarshal(restResponse.getResponseContent(), Map.class);
            String nssiId = response.get("nSSId");
            ResourceOperationStatus status = new ResourceOperationStatus(nsiId, response.get("nSSId"), nsstId);
            status.setResourceInstanceID(nssiId);
            logger.info("save segment and operaton info -> begin");

            updateDbStatus(status, restResponse.getStatus(), FINISHED, ALLOCATE_NSS_SUCCESS);
            logger.info("save segment and operaton info -> end");
        }
    }


    @Override
    protected String doWrapModifyReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        //TODO
        return null;
    }

    @Override
    protected String doWrapDeAllocateReqBody(DeAllocateNssi deAllocateNssi) throws ApplicationException{
        Map<String,String> request = new HashMap<>();
        request.put("nSSId",deAllocateNssi.getNssiId());
        return marshal(request);
    }


    @Override
    public RestResponse modifyNssi(NssmfAdapterNBIRequest modifyRequest) throws ApplicationException{
        //TODO
        return null;
    }
    @Override
    public RestResponse activateNssi(NssmfAdapterNBIRequest nbiRequest, String snssai) throws ApplicationException {
        //TODO
        return null;
    }

    @Override
    protected RestResponse doResponseStatus(ResourceOperationStatus status) throws ApplicationException {
        return responseDBStatus(status);
    }

    @Override
    protected SelectionType doQueryNSSISelectionCapability() {
        return SelectionType.NSSMF;
    }
}
