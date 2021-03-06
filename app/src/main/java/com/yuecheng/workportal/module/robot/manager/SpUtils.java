package com.yuecheng.workportal.module.robot.manager;


import com.yuecheng.workportal.R;
import com.yuecheng.workportal.MainApplication;
import com.yuecheng.workportal.module.robot.manager.SpManager;

/**
 * SharedPreferences工具类
 */
public class SpUtils {





    public static void writeVersion(String version) {
        SpManager.getInstance().writeVersion(version);
    }

    public static String readVersion() {
        return SpManager.getInstance().readVersion();
    }

    public static void writeVoiceType(int position) {
        SpManager.getInstance().writeVoiceTypePosition(position);
    }


    public static String readVoiceTypeParam() {
        int voiceTypePosition = SpManager.getInstance().readVoiceTypePosition();
        try {
            String[] arr = MainApplication.getApplication().getResources().getStringArray(R.array.voice_type_params);
            return arr[voiceTypePosition];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void writeVoiceSpeed(int progress) {
        SpManager.getInstance().writeVoiceSpeed(progress);
    }

    public static int readVoiceSpeed() {
        int voiceSpeed = SpManager.getInstance().readVoiceSpeed();
        return voiceSpeed == -1 ? 50 : voiceSpeed;
    }

    public static String readVoiceSpeedStr() {
        int voiceSpeed = SpManager.getInstance().readVoiceSpeed();
        return (voiceSpeed == -1 ? 50 : voiceSpeed) + "%";
    }
}
