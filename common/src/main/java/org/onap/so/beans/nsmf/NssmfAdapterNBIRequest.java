package org.onap.so.beans.nsmf;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class NssmfAdapterNBIRequest implements Serializable {

    private static final long serialVersionUID = -454145891489457960L;

    @NotNull
    private EsrInfo esrInfo;

    @NotNull
    private ServiceInfo serviceInfo;

    private String allocateCnNssi;

    private String allocateTnNssi;

    private String allocateAnNssi;

    private String nsiId;

    private ActDeActNssi actDeActNssi;

    private DeAllocateNssi deAllocateNssi;

    private ModifyAnNSSI modifyAnNSSI;
}
