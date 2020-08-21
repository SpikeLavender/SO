package org.onap.so.beans.nsmf;


import lombok.Data;
import java.io.Serializable;

@Data
public class SlaPolicy implements Serializable {
    private static final long serialVersionUID = -3477820625726849354L;

    private Integer latency;

    private Integer jitter;

    private Integer maxBandwidth;

    private String reliability;
}
