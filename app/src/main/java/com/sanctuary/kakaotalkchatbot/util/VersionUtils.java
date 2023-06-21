package com.sanctuary.kakaotalkchatbot.util;

import android.content.Context;
import android.content.pm.PackageInfo;

public class VersionUtils {
    // 버전 비교 결과 코드
    public static final int VERSION_LATEST = 0; // 최신버전
    public static final int VERSION_LOW_MAJOR = 1;  // 메이저 버전이 낮다.
    public static final int VERSION_LOW_MINOR = 2;  // 마이너 버전이 낮다.
    public static final int VERSION_LOW_PATCH = 3;  // 패치 버전이 낮다.
    public static final int VERSION_ERROR_SPLIT = -1;   // 버전 자르기 에러
    public static final int VERSION_ERROR_LENGTH = -2;  // 버전 개수 에러
    public static final int VERSION_ERROR_CONVERT = -3; // 버전 변환 에러

    // 패키지명
    public static String getPackageName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
            return null;
        }
    }

    // 버전명
    public static String getVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
            return "";
        }
    }

    // 버전코드
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            return -1;
        }
    }

    // 버전 비교(클라이언트 버전이 서버버전보다 더 크더라도 최신버전으로 인식)
    public static int compareVersionName(String serverVersion, String clientVersion) {
        String arrServerVersion[];
        String arrClientVersion[];

        // 1. 점(.)을 기준으로 버전명 자르기
        try {
            arrServerVersion = serverVersion.split("\\.", -1);
            arrClientVersion = clientVersion.split("\\.", -1);
        } catch (Exception e) {
            return VERSION_ERROR_SPLIT;
        }

        // 2. 버전명 길이 체크(둘다 3이어야함)
        if ((arrServerVersion.length != 3) || (arrClientVersion.length != 3)) {
            return VERSION_ERROR_LENGTH;
        }

        // 3. 문자열 버전명을 숫자로 변환
        int serverMajor, serverMinor, serverPatch;
        int clientMajor, clientMinor, clientPatch;

        try {
            // 서버 버전 변환
            serverMajor = Integer.parseInt(arrServerVersion[0]);
            serverMinor = Integer.parseInt(arrServerVersion[1]);
            serverPatch = Integer.parseInt(arrServerVersion[2]);

            // 클라이언트 버전 변환
            clientMajor = Integer.parseInt(arrClientVersion[0]);
            clientMinor = Integer.parseInt(arrClientVersion[1]);
            clientPatch = Integer.parseInt(arrClientVersion[2]);
        } catch (Exception e) {
            return VERSION_ERROR_CONVERT;
        }

        // 4. 메이저버전 비교
        if (serverMajor > clientMajor) {
            return VERSION_LOW_MAJOR;
        } else if (serverMajor == clientMajor) {
            // 5. 마이너버전 비교
            if (serverMinor > clientMinor) {
                return VERSION_LOW_MINOR;
            } else if (serverMinor == clientMinor) {
                // 6. 패치버전 비교
                if (serverPatch > clientPatch) {
                    return VERSION_LOW_PATCH;
                }
            }
        }

        // 7. 최신버전
        return VERSION_LATEST;
    }
}