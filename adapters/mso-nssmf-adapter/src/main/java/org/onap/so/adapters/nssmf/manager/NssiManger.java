package org.onap.so.adapters.nssmf.manager;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;

public interface NssiManger {

    RestResponse allocateNssi(NssiAllocateRequest allocateRequest) throws ApplicationException;

    RestResponse deAllocateNssi(NssiDeAllocateRequest allocateRequest, String sliceId) throws ApplicationException;

    RestResponse terminateNssi(NssiTerminateRequest terminateRequest, String nssiId) throws ApplicationException;

    RestResponse activateNssi(NssiActDeActRequest deActRequest, String snssai) throws ApplicationException;

    RestResponse deActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai) throws ApplicationException;

    RestResponse queryJobStatus(JobStatusRequest jobReq, String jobId) throws ApplicationException;

    RestResponse createNssi(NssiCreateRequest nssiCreate) throws ApplicationException;

    RestResponse updateNssi(NssiUpdateRequest nssiUpdate, String sliceId) throws ApplicationException;

    RestResponse updateNssiById(NssiUpdateRequestById nssiUpdateById, String nssiId) throws ApplicationException;

    void setRestUtil(RestUtil restUtil);

    void setEsrInfo(EsrInfo esrInfo);

    void setActionType(ActionType actionType);

}
