package org.onap.so.beans.nsmf;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConnectionLink implements Serializable {
    private static final long serialVersionUID = -1834584960407180427L;

    private String transportEndpointA;

    private String transportEndpointB;
}
