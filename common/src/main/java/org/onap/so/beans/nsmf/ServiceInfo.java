package org.onap.so.beans.nsmf;

import lombok.Data;
import java.io.Serializable;

@Data
public class ServiceInfo implements Serializable {

    private static final long serialVersionUID = 7895110339097615695L;

    private String serviceInvariantUuid;

    private String serviceUuid;

    private String globalSubscriberId;

    private String serviceType;

    private String nsiId;

    private String nssiId;
}
