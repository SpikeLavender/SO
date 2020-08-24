package org.onap.so.adapters.nssmf.manager.impl.external;

import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.enums.SelectionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.impl.ExternalNssmfManager;
import org.onap.so.beans.nsmf.DeAllocateNssi;
import org.onap.so.beans.nsmf.JobStatusResponse;
import org.onap.so.beans.nsmf.NssmfAdapterNBIRequest;
import org.onap.so.beans.nsmf.ResponseDescriptor;
import org.onap.so.db.request.beans.ResourceOperationStatus;


import static org.onap.so.adapters.nssmf.enums.JobStatus.*;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.QUERY_JOB_STATUS_FAILED;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.StatusDesc.QUERY_JOB_STATUS_SUCCESS;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.marshal;
import static org.onap.so.adapters.nssmf.util.NssmfAdapterUtil.unMarshal;

public class ExternalCnNssmfManager extends ExternalNssmfManager {

    @Override
    protected String getInitalStatus() {
        return "deactivated";
    }

    @Override
    protected String doWrapExtAllocateReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateCnNssi());
    }


    @Override
    protected RestResponse doResponseStatus(ResourceOperationStatus status) throws ApplicationException {
        RestResponse restResponse = sendRequest(null);
        ResponseDescriptor rspDesc =
                unMarshal(restResponse.getResponseContent(), JobStatusResponse.class).getResponseDescriptor();
        updateRequestDbJobStatus(rspDesc, status, restResponse);
        return restResponse;
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

    @Override
    protected String doWrapModifyReqBody(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException {
        return marshal(nbiRequest.getAllocateCnNssi());
    }

    @Override
    protected String doWrapDeAllocateReqBody(DeAllocateNssi deAllocateNssi) throws ApplicationException {
        return marshal(deAllocateNssi);
    }

    @Override
    protected SelectionType doQueryNSSISelectionCapability() {

        return SelectionType.NSMF;
    }
}
