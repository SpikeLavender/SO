package org.onap.so.adapters.nssmf.consts;

import org.onap.so.adapters.nssmf.entity.NssmfUrlInfo;
import org.onap.so.adapters.nssmf.enums.ActionType;
import org.onap.so.adapters.nssmf.enums.ExecutorType;
import org.onap.so.adapters.nssmf.enums.HttpMethod;
import org.onap.so.beans.nsmf.NetworkType;

import java.util.HashMap;
import java.util.Map;

public class NssmfConsts {

    public static Map<String, NssmfUrlInfo> urlInfoMap = new HashMap<>();

    public static String THIRD_PARTY_TN_ALLOCATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles";

    public static String THIRD_PARTY_CN_ALLOCATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles";

    public static String THIRD_PARTY_AN_ALLOCATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles";

    public static String WORKFLOW_ALLOCATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_TN_DEALLOCATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_CN_DEALLOCATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_AN_DEALLOCATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles/%s";

    public static String WORKFLOW_DEALLOCATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_TN_ACTIVATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_CN_ACTIVATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_AN_ACTIVATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles/%s";

    public static String WORKFLOW_ACTIVATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_TN_DEACTIVATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_CN_DEACTIVATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_AN_DEACTIVATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles/%s";

    public static String WORKFLOW_DEACTIVATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_TN_TERMINATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_CN_TERMINATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_AN_TERMINATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles/%s";

    public static String WORKFLOW_TERMINATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_TN_UPDATE_URL = "/api/rest/provMns/v1/tn/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_CN_UPDATE_URL = "/api/rest/provMns/v1/NSS/SliceProfiles/%s";

    public static String THIRD_PARTY_AN_UPDATE_URL = "/api/rest/provMns/v1/an/NSS/SliceProfiles/%s";

    public static String WORKFLOW_UPDATE_URL = "/onap/so/infra/e2eServiceInstances/v3";

    public static String THIRD_PARTY_QUERY_JOB_STATUS = "/api/rest/provMns/v1/NSS/jobs/%s";

    /**
     * 写个方法获取 url 和 method
     */

    static {
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.ALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.ALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.ALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_ALLOCATE_URL, HttpMethod.POST));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.ALLOCATE),
                new NssmfUrlInfo(WORKFLOW_ALLOCATE_URL, HttpMethod.POST));

        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.DEALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.DEALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.DEALLOCATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_DEALLOCATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.DEALLOCATE),
                new NssmfUrlInfo(WORKFLOW_DEALLOCATE_URL, HttpMethod.DELETE));

        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.ACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.ACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.ACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_ACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.ACTIVATE),
                new NssmfUrlInfo(WORKFLOW_ACTIVATE_URL, HttpMethod.PUT));

        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.DEACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.DEACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.DEACTIVATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_DEACTIVATE_URL, HttpMethod.PUT));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.DEACTIVATE),
                new NssmfUrlInfo(WORKFLOW_DEACTIVATE_URL, HttpMethod.PUT));

        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.TERMINATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.TERMINATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.TERMINATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_TERMINATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.TERMINATE),
                new NssmfUrlInfo(WORKFLOW_TERMINATE_URL, HttpMethod.DELETE));

        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.ACCESS, ActionType.UPDATE),
                new NssmfUrlInfo(THIRD_PARTY_AN_UPDATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.TRANSPORT, ActionType.UPDATE),
                new NssmfUrlInfo(THIRD_PARTY_TN_UPDATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.THIRD_PARTY, NetworkType.CORE ,ActionType.UPDATE),
                new NssmfUrlInfo(THIRD_PARTY_CN_UPDATE_URL, HttpMethod.DELETE));
        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.UPDATE),
                new NssmfUrlInfo(WORKFLOW_UPDATE_URL, HttpMethod.DELETE));


        urlInfoMap.put(generateKey(ExecutorType.INNER, null, ActionType.QUERY_JOB_STATUS),
                new NssmfUrlInfo(THIRD_PARTY_QUERY_JOB_STATUS, HttpMethod.GET));
    }

    public static NssmfUrlInfo getNssmfUrlInfo(ExecutorType executorType, NetworkType networkType, ActionType actionType) {

        return urlInfoMap.get(generateKey(executorType, networkType, actionType));
    }

    private static String generateKey(ExecutorType executorType, NetworkType networkType, ActionType actionType) {
        if (ExecutorType.THIRD_PARTY.equals(executorType)) {
            return executorType.name() + "_" + networkType.name() + "_" + actionType.name();
        }
        return executorType.name() + "_" + actionType.name();
    }
}
