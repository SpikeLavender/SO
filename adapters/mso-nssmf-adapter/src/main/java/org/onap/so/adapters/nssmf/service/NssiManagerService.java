package org.onap.so.adapters.nssmf.service;

import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NssiManagerService {
    ResponseEntity allocateNssi(NssiAllocateRequest allocateRequest);

    ResponseEntity deAllocateNssi(NssiDeAllocateRequest allocateRequest, String sliceId);

    ResponseEntity terminateNssi(NssiTerminateRequest terminateRequest, String nssiId);

    ResponseEntity activateNssi(NssiActDeActRequest deActRequest, String snssai);

    ResponseEntity deActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai);

    ResponseEntity createNssi(NssiCreateRequest nssiCreate);

    ResponseEntity updateNssi(NssiUpdateRequest nssiUpdate, String sliceId);

    ResponseEntity updateNssiById(NssiUpdateRequestById nssiUpdateById, String nssiId);

    ResponseEntity queryJobStatus(JobStatusRequest jobReq, String jobId);
}
