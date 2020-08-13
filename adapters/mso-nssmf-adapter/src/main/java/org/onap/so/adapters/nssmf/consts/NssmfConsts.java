package org.onap.so.adapters.nssmf.consts;

import org.onap.so.adapters.nssmf.entity.NssmfUrlInfo;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.enums.HttpMethod;
import org.onap.so.beans.nsmf.NetworkType;

import java.util.HashMap;
import java.util.Map;

public class NssmfConsts {

    public final static String ONAP_INTERNAL_TAG = "ONAP_internal";

    public final static String CURRENT_INTERNAL_NSSMF_API_VERSION = "v1";

    public static Map<String, NssmfUrlInfo> urlInfoMap = new HashMap<>();

    public static String EXTERNAL_CN_ALLOCATE_URL = "/api/rest/provMns/{apiVersion}/NSS/SliceProfiles";

    public static String EXTERNAL_TN_ALLOCATE_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/SliceProfiles";

    public static String EXTERNAL_AN_ALLOCATE_URL = "/api/rest/provMns/{apiVersion}/an/NSS/SliceProfiles";

    public static String INTERNAL_ALLOCATE_URL = "/onap/so/infra/3gppservices/{apiVersion}/allocate";

    public static String EXTERNAL_CN_DEALLOCATE_URL = "/api/rest/provMns/{apiVersion}/NSS/SliceProfiles/{sliceProfileId}";

    public static String EXTERNAL_TN_DEALLOCATE_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/SliceProfiles/{sliceProfileId}";

    public static String EXTERNAL_AN_DEALLOCATE_URL = "/api/rest/provMns/{apiVersion}/an/NSS/SliceProfiles/{sliceProfileId}";

    public static String INTERNAL_DEALLOCATE_URL = "/onap/so/infra/3gppservices/{apiVersion}/deAllocate";

    public static String EXTERNAL_CN_ACTIVATE_URL = "/api/rest/provMns/{apiVersion}/NSS/{snssai}/activation";

    public static String EXTERNAL_TN_ACTIVATE_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/{snssai}/activation";

    public static String EXTERNAL_AN_ACTIVATE_URL = "/api/rest/provMns/{apiVersion}/an/NSS/{snssai}/activations";

    public static String INTERNAL_ACTIVATE_URL = "/onap/so/infra/3gppservices/{apiVersion}/activate";

    public static String EXTERNAL_CN_DEACTIVATE_URL = "/api/rest/provMns/{apiVersion}/NSS/{snssai}/deactivation";

    public static String EXTERNAL_TN_DEACTIVATE_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/{snssai}/deactivation";

    public static String EXTERNAL_AN_DEACTIVATE_URL = "/api/rest/provMns/{apiVersion}/an/NSS/{snssai}/deactivation";

    public static String INTERNAL_DEACTIVATE_URL = "/onap/so/infra/3gppservices/{apiVersion}/deActivate";

    //
    public static String EXTERNAL_CN_TERMINATE_URL = "/api/rest/provMns/{apiVersion}/NSS/SliceProfiles/%s";

    public static String EXTERNAL_TN_TERMINATE_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/SliceProfiles/%s";

    public static String EXTERNAL_AN_TERMINATE_URL = "/api/rest/provMns/{apiVersion}/an/NSS/SliceProfiles/%s";

    public static String INTERNAL_TERMINATE_URL = "/onap/so/infra/3gppservices/{apiVersion}/terminate";
    //

    public static String EXTERNAL_CN_MODIFY_URL = "/api/rest/provMns/{apiVersion}/NSS/SliceProfiles/%s";

    public static String EXTERNAL_TN_MODIFY_URL = "/api/rest/provMns/{apiVersion}/tn/NSS/SliceProfiles/%s";

    public static String EXTERNAL_AN_MODIFY_URL = "/api/rest/provMns/{apiVersion}/an/NSS/SliceProfiles/%s";

    public static String INTERNAL_MODIFY_URL = "/onap/so/infra/3gppservices/{apiVersion}/modify";

    //
    public static String EXTERNAL_QUERY_JOB_STATUS = "/api/rest/provMns/{apiVersion}/NSS/jobs/{jobId}?responseId={responseId}";

    /**
     * 写个方法获取 url 和 method
     */

    static {
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.ALLOCATE),
                new NssmfUrlInfo(EXTERNAL_AN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.ALLOCATE),
                new NssmfUrlInfo(EXTERNAL_TN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.ALLOCATE),
                new NssmfUrlInfo(EXTERNAL_CN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.ALLOCATE),
                new NssmfUrlInfo(INTERNAL_ALLOCATE_URL, HttpMethod.POST));

        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.DEALLOCATE),
                new NssmfUrlInfo(EXTERNAL_AN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.DEALLOCATE),
                new NssmfUrlInfo(EXTERNAL_TN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.DEALLOCATE),
                new NssmfUrlInfo(EXTERNAL_CN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.DEALLOCATE),
                new NssmfUrlInfo(INTERNAL_DEALLOCATE_URL, HttpMethod.DELETE));

        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.ACTIVATE),
                new NssmfUrlInfo(EXTERNAL_AN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.ACTIVATE),
                new NssmfUrlInfo(EXTERNAL_TN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.ACTIVATE),
                new NssmfUrlInfo(EXTERNAL_CN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.ACTIVATE),
                new NssmfUrlInfo(INTERNAL_ACTIVATE_URL, HttpMethod.PUT));

        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.DEACTIVATE),
                new NssmfUrlInfo(EXTERNAL_AN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.DEACTIVATE),
                new NssmfUrlInfo(EXTERNAL_TN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.DEACTIVATE),
                new NssmfUrlInfo(EXTERNAL_CN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.DEACTIVATE),
                new NssmfUrlInfo(INTERNAL_DEACTIVATE_URL, HttpMethod.PUT));

        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.TERMINATE),
                new NssmfUrlInfo(EXTERNAL_AN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.TERMINATE),
                new NssmfUrlInfo(EXTERNAL_TN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.TERMINATE),
                new NssmfUrlInfo(EXTERNAL_CN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.TERMINATE),
                new NssmfUrlInfo(INTERNAL_TERMINATE_URL, HttpMethod.DELETE));

        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.ACCESS, ActionType.MODIFY),
                new NssmfUrlInfo(EXTERNAL_AN_MODIFY_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.TRANSPORT, ActionType.MODIFY),
                new NssmfUrlInfo(EXTERNAL_TN_MODIFY_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, NetworkType.CORE ,ActionType.MODIFY),
                new NssmfUrlInfo(EXTERNAL_CN_MODIFY_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.INTERNAL, null, ActionType.MODIFY),
                new NssmfUrlInfo(INTERNAL_MODIFY_URL, HttpMethod.PUT));


        urlInfoMap.put(generateKey(ExecutorType.EXTERNAL, null, ActionType.QUERY_JOB_STATUS),
                new NssmfUrlInfo(EXTERNAL_QUERY_JOB_STATUS, HttpMethod.GET));
    }

    public static NssmfUrlInfo getNssmfUrlInfo(ExecutorType executorType, NetworkType networkType, ActionType actionType) {

        return urlInfoMap.get(generateKey(executorType, networkType, actionType));
    }

    private static String generateKey(ExecutorType executorType, NetworkType networkType, ActionType actionType) {
        if (ExecutorType.EXTERNAL.equals(executorType)) {
            return executorType.name() + "_" + networkType.name() + "_" + actionType.name();
        }
        return executorType.name() + "_" + actionType.name();
    }
}
