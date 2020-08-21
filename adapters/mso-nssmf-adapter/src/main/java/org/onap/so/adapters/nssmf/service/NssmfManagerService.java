package org.onap.so.adapters.nssmf.service;

import org.onap.so.beans.nsmf.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NssmfManagerService {
    ResponseEntity allocateNssi(NssmfAdapterNBIRequest allocateRequest);

    ResponseEntity deAllocateNssi(NssmfAdapterNBIRequest allocateRequest, String sliceId);

    ResponseEntity activateNssi(NssmfAdapterNBIRequest deActRequest, String snssai);

    ResponseEntity deActivateNssi(NssmfAdapterNBIRequest nssiDeActivate, String snssai);

    ResponseEntity modifyNssi(NssmfAdapterNBIRequest nssiUpdate, String sliceId);

    ResponseEntity queryJobStatus(JobStatusRequest jobReq, String jobId);

    ResponseEntity queryNSSISelectionCapability(NssmfAdapterNBIRequest nbiRequest);

    ResponseEntity querySubnetCapability(NssmfAdapterNBIRequest nbiRequest);
}
