package org.onap.so.adapters.nssmf.service.impl;

import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.exceptions.ApplicationException;
import org.onap.so.adapters.nssmf.manager.NssmfManagerBuilder;
import org.onap.so.adapters.nssmf.entity.RestResponse;
import org.onap.so.adapters.nssmf.manager.NssmfManager;
import org.onap.so.adapters.nssmf.service.NssmfManagerService;
import org.onap.so.adapters.nssmf.util.RestUtil;
import org.onap.so.beans.nsmf.*;
import org.onap.so.db.request.beans.ResourceOperationStatus;
import org.onap.so.db.request.data.repository.ResourceOperationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            ResourceOperationStatus status = getOperationStatus(jobReq.getNssiId(), jobId, jobReq.getNsiId());

            return buildResponse(buildNssmfManager(esrInfo, ActionType.QUERY_JOB_STATUS, null).queryJobStatus(jobReq, jobId));
        } catch (ApplicationException e) {
            return e.buildErrorResponse();
        }
    }

    private ResponseEntity buildResponse(RestResponse rsp) {
        return ResponseEntity.status(rsp.getStatus()).body(rsp.getResponseContent());
    }


    private ResourceOperationStatus getOperationStatus(String nssiId, String jobId, String nsiId) throws ApplicationException {
        ResourceOperationStatus status = new ResourceOperationStatus(nssiId, jobId, nsiId);
        return repository.findOne(Example.of(status))
                .orElseThrow(() -> new ApplicationException(404, "Cannot Find Operation Status"));
    }

    public ResourceOperationStatus updateOperationStatus(String jobId) {
        ResourceOperationStatus operationStatus = new ResourceOperationStatus();
        operationStatus.setJobId(jobId);
        Example<ResourceOperationStatus> statusExample = Example.of(operationStatus);

        Optional<ResourceOperationStatus> status = repository.findOne(statusExample);
        if (status.isPresent()) {
            return status.get();
        }
        throw new RuntimeException("the job id is not exist");
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
