package org.onap.so.beans.nsmf;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TransportSliceNetwork implements Serializable {
    private static final long serialVersionUID = 809947462399806990L;

    private String type;

    private SlaPolicy slaPolicy;

    private List<ConnectionLink> connectionLinks;
}
