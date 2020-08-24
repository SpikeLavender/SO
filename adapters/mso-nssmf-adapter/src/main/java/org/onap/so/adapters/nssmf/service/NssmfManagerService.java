package org.onap.so.adapters.nssmf.service;

import aaf.v2_0.Nss;
import org.onap.so.beans.nsmf.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NssmfManagerService {
    ResponseEntity allocateNssi(NssmfAdapterNBIRequest allocateRequest);

    ResponseEntity deAllocateNssi(NssmfAdapterNBIRequest allocateRequest, String sliceProfileId);

    ResponseEntity activateNssi(NssmfAdapterNBIRequest deActRequest, String snssai);

    ResponseEntity deActivateNssi(NssmfAdapterNBIRequest nssiDeActivate, String snssai);

    ResponseEntity queryJobStatus(NssmfAdapterNBIRequest jobReq, String jobId);

    ResponseEntity queryNSSISelectionCapability(NssmfAdapterNBIRequest nbiRequest);

    ResponseEntity querySubnetCapability(NssmfAdapterNBIRequest nbiRequest);

}
