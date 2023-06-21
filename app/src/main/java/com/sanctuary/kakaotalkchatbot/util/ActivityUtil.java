package com.sanctuary.kakaotalkchatbot.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class ActivityUtil {
    // 단일 엑티비티 실행 (여러번 터치시 하나만 실행)
    public static void startSingleActivity(Context context, Class<?> c) {
        Intent intent = new Intent(context, c);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    // 엑티비티 스텍을 모두 지우고 단일 엑티비티 실행
    public static void startNewActivity(Context context, Class<?> c) {
        Intent intent = new Intent(context, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    // Url 엑티비티 실행 (웹브라우저 호출)
    public static void startUrlActivity(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
