package org.onap.so.adapters.nssmf.manager;

import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.beans.nsmf.*;

public interface NssmfManager {

    RestResponse allocateNssi(NssmfAdapterNBIRequest nssmfRequest) throws ApplicationException;

    RestResponse deAllocateNssi(NssmfAdapterNBIRequest nssmfRequest, String sliceId) throws ApplicationException;

    RestResponse activateNssi(NssmfAdapterNBIRequest nssmfRequest, String snssai) throws ApplicationException;

    RestResponse deActivateNssi(NssmfAdapterNBIRequest nssmfRequest, String snssai) throws ApplicationException;

    RestResponse queryJobStatus(JobStatusRequest jobReq, String jobId) throws ApplicationException;

    RestResponse queryNSSISelectionCapability(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;

    RestResponse querySubnetCapability(NssmfAdapterNBIRequest nbiRequest) throws ApplicationException;
}
