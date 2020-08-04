package org.onap.so.adapters.nssmf.controller;

import org.onap.so.adapters.nssmf.service.NssiManagerService;
import org.onap.so.beans.nsmf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping(value = "/api/rest/provMns/v1", produces = {APPLICATION_JSON}, consumes = {APPLICATION_JSON})
public class NssiController {

    private static final Logger logger = LoggerFactory.getLogger(NssiController.class);

    @Autowired
    private NssiManagerService nssiManagerService;

    @PostMapping(value = "/NSS/SliceProfiles")
    public ResponseEntity allocateNssi(@RequestBody NssiAllocateRequest allocateRequest) {
        return nssiManagerService.allocateNssi(allocateRequest);
    }

    @PostMapping(value = "/NSS/nssi")
    public ResponseEntity createNssi(@RequestBody NssiCreateRequest createRequest) {
        return nssiManagerService.createNssi(createRequest);
    }

    @PostMapping(value = "/NSS/SliceProfiles/{sliceProfileId}")
    public ResponseEntity deAllocateNssi(@RequestBody NssiDeAllocateRequest deAllocateRequest,
                                         @PathVariable("sliceProfileId") final String sliceId) {
        return nssiManagerService.deAllocateNssi(deAllocateRequest, sliceId);
    }

    @PostMapping(value = "/NSS/nssi/{nssiId}")
    public ResponseEntity terminateNssi(@RequestBody NssiTerminateRequest terminateRequest,
                                        @PathVariable("nssiId") String nssiId) {
        return nssiManagerService.terminateNssi(terminateRequest, nssiId);
    }

    @PutMapping(value = "/NSS/SliceProfiles/{sliceProfileId}")
    public ResponseEntity modifyNssi(@RequestBody NssiUpdateRequest updateRequest,
                                     @PathVariable("sliceProfileId") String sliceId) {
        return nssiManagerService.updateNssi(updateRequest, sliceId);
    }

    @PutMapping(value = "/NSS/nssi/{nssiId}")
    public ResponseEntity modifyNssiById(@RequestBody NssiUpdateRequestById updateById,
                                         @PathVariable("nssiId") String nssiId) {
        return nssiManagerService.updateNssiById(updateById, nssiId);
    }

    @PostMapping(value = "/NSS/{snssai}/activation")
    public ResponseEntity activateNssi(@RequestBody NssiActDeActRequest actDeActRequest,
                                       @PathVariable("snssai") String snssai) {
        return nssiManagerService.activateNssi(actDeActRequest, snssai);
    }

    @PostMapping(value = "/NSS/{snssai}/deactivation")
    public ResponseEntity deactivateNssi(@RequestBody NssiActDeActRequest deActRequest,
                                         @PathVariable("snssai") String snssai) {
       return nssiManagerService.deActivateNssi(deActRequest, snssai);
    }

    @PostMapping(value = "/NSS/jobs/{jobId}")
    public ResponseEntity queryJobStatus(@RequestBody JobStatusRequest jobStatusReq,
                                         @PathVariable("jobId") String jobId) {
        return nssiManagerService.queryJobStatus(jobStatusReq, jobId);
    }

}
