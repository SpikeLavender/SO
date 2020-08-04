package org.onap.so.adapters.nssmf.service.impl;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.NssiManagerBuilder;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.service.NssiManagerService;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NssiManagerServiceImpl implements NssiManagerService {

    @Autowired
    private RestUtil restUtil;

    @Override
    public ResponseEntity allocateNssi(NssiAllocateRequest allocateRequest) {
        EsrInfo esrInfo = allocateRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.ALLOCATE).build()
                    .allocateNssi(allocateRequest));

        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity deAllocateNssi(NssiDeAllocateRequest allocateRequest, String sliceId) {
        EsrInfo esrInfo = allocateRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.DEALLOCATE).build()
                    .deAllocateNssi(allocateRequest, sliceId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity terminateNssi(NssiTerminateRequest terminateRequest, String nssiId) {
        EsrInfo esrInfo = terminateRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.TERMINATE).build()
                    .terminateNssi(terminateRequest, nssiId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity activateNssi(NssiActDeActRequest deActRequest, String snssai) {
        EsrInfo esrInfo = deActRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.ACTIVATE).build()
                    .activateNssi(deActRequest, snssai));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity deActivateNssi(NssiActDeActRequest nssiDeActivate, String snssai) {
        EsrInfo esrInfo = nssiDeActivate.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.DEACTIVATE).build()
                    .deActivateNssi(nssiDeActivate, snssai));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity createNssi(NssiCreateRequest createRequest) {
        EsrInfo esrInfo = createRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.CREATE).build()
                    .createNssi(createRequest));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity updateNssi(NssiUpdateRequest updateRequest, String sliceId) {
        EsrInfo esrInfo = updateRequest.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.UPDATE).build()
                    .updateNssi(updateRequest, sliceId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity updateNssiById(NssiUpdateRequestById nssiUpdateById, String nssiId) {
        EsrInfo esrInfo = nssiUpdateById.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.UPDATE_BY_ID).build()
                    .updateNssiById(nssiUpdateById, nssiId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity queryJobStatus(JobStatusRequest jobReq, String jobId) {
        EsrInfo esrInfo = jobReq.getEsrInfo();
        try {
            return buildResponse(new NssiManagerBuilder(esrInfo).setRestUtil(restUtil)
                    .setActionType(ActionType.QUERY_JOB_STATUS).build()
                    .queryJobStatus(jobReq, jobId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    private ResponseEntity buildResponse(RestResponse rsp) {
        return ResponseEntity.status(rsp.getStatus()).body(rsp.getResponseContent());
    }

}
