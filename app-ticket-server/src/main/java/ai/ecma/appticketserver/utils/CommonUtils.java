package ai.ecma.appticketserver.utils;

import java.util.UUID;

public class CommonUtils {

    public static String urlBuilder(UUID photoId) {
        return AppConstant.DOMAIN + AppConstant.ATTACHMENT_CONTROLLER + "/download/" + photoId;
    }


}
