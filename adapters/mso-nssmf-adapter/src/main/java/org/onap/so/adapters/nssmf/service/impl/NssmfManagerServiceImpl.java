package org.onap.so.adapters.nssmf.service.impl;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.NssmfManagerBuilder;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.manager.NssmfManager;
import org.onap.so.adapters.nssmf.service.NssmfManagerService;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.data.repository.ResourceOperationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class NssmfManagerServiceImpl implements NssmfManagerService {

    @Autowired
    private RestUtil restUtil;

    @Autowired
    private ResourceOperationStatusRepository repository;

    @Override
    public ResponseEntity allocateNssi(NssmfAdapterNBIRequest request) {
        try {
            return buildResponse(buildNssmfManager(request, ActionType.ALLOCATE).allocateNssi(request));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity deAllocateNssi(NssmfAdapterNBIRequest request, String sliceId) {
        try {
            return buildResponse(buildNssmfManager(request, ActionType.DEALLOCATE).deAllocateNssi(request, sliceId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity activateNssi(NssmfAdapterNBIRequest request, String snssai) {
        try {
            return buildResponse(buildNssmfManager(request, ActionType.ACTIVATE).activateNssi(request, snssai));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override
    public ResponseEntity deActivateNssi(NssmfAdapterNBIRequest request, String snssai) {
        try {
            return buildResponse(buildNssmfManager(request, ActionType.DEACTIVATE).deActivateNssi(request, snssai));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    @Override //wripro
    public ResponseEntity modifyNssi(NssmfAdapterNBIRequest nssiUpdate, String sliceId) {
        return null;
    }

    @Override
    public ResponseEntity queryJobStatus(JobStatusRequest jobReq, String jobId) {
        EsrInfo esrInfo = jobReq.getEsrInfo();
        try {
            return buildResponse(buildNssmfManager(esrInfo, ActionType.QUERY_JOB_STATUS, null).queryJobStatus(jobReq, jobId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    private ResponseEntity buildResponse(RestResponse rsp) {
        return ResponseEntity.status(rsp.getStatus()).body(rsp.getResponseContent());
    }


    private NssmfManager buildNssmfManager(NssmfAdapterNBIRequest request, ActionType actionType) throws ApplicationException {
        return buildNssmfManager(request.getEsrInfo(), actionType, request.getServiceInfo());
    }

    private NssmfManager buildNssmfManager(EsrInfo esrInfo, ActionType actionType, ServiceInfo serviceInfo) throws ApplicationException {
        return new NssmfManagerBuilder(esrInfo)
                .setActionType(actionType)
                .setRepository(repository)
                .setRestUtil(restUtil)
                .setServiceInfo(serviceInfo)
                .build();
    }
}
