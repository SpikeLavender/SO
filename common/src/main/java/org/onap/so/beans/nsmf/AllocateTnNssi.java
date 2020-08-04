/*-
 * ============LICENSE_START=======================================================
 * ONAP - SO
 * ================================================================================
 * Copyright (C) 2020 Huawei Technologies Co., Ltd. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.so.beans.nsmf;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AllocateTnNssi implements Serializable {

    private static final long serialVersionUID = -7069801712339914746L;

    public final static String URL = "/api/rest/provMns/v1/tn/NSS" + "/SliceProfiles";

    public final static String WORKFLOW_URL = "/onap/so/infra/e2eServiceInstances/v3";

    private String nsstId;

    private String flavorId;

    private String nssiId;

    private String nssiName;

    @Deprecated
    private TnSliceProfile sliceProfile;

    private String scriptName;

    private Object extension;

    private NsiInfo nsiInfo;

    @NotBlank
    private String serviceInvariantUuid;

    @NotBlank
    private String serviceUuid;

    @NotNull
    private List<NetworkSliceInfo> networkSliceInfos;

    @NotNull
    private List<TransportSliceNetwork> transportSliceNetworks;
}
