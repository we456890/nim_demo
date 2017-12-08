package com.netease.nim.demo.chatroom.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.DemoCache;
import com.netease.nim.demo.R;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by hzxuwen on 2016/1/19.
 */
public class ChatRoomHelper {
    private static final int[] imageRes = {R.drawable.apple_round_spinner_00, R.drawable.apple_round_spinner_00, R.drawable.apple_round_spinner_00,
            R.drawable.apple_round_spinner_00, R.drawable.apple_round_spinner_00, R.drawable.apple_round_spinner_00, R.drawable.apple_round_spinner_00,
            R.drawable.apple_round_spinner_00};

    private static Map<String, Integer> roomCoverMap = new HashMap<>();
    private static int index = 0;

    public static void init() {
        ChatRoomMemberCache.getInstance().clear();
        ChatRoomMemberCache.getInstance().registerObservers(true);
    }

    public static void logout() {
        ChatRoomMemberCache.getInstance().registerObservers(false);
        ChatRoomMemberCache.getInstance().clear();
    }

    public static void setCoverImage(String roomId, ImageView coverImage, boolean blur) {
        if (roomCoverMap.containsKey(roomId)) {
            blurCoverImage(blur, coverImage, roomCoverMap.get(roomId));
        } else {
            if (index > imageRes.length) {
                index = 0;
            }
            roomCoverMap.put(roomId, imageRes[index]);
            blurCoverImage(blur, coverImage, imageRes[index]);
            index++;
        }
    }

    private static void blurCoverImage(boolean blur, final ImageView imageView, final int resId) {
        final Context context = DemoCache.getContext();

        if (!blur) {
            Glide.with(context).load(resId).into(imageView);
        } else {
            Glide.with(context).load(resId)
                    .bitmapTransform(new BlurTransformation(context, 5))
                    .into(imageView);
        }
    }
}
