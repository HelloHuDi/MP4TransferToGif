package com.hd.transfer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.Size;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hd on 2018/6/14 .
 * Because there are too many parameters to be set up and the actual use is complex,
 * you can customize commands by yourself {@link TransferGif#transfer(String[])},
 * the following is a simple example.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public final class TransferGif {

    static {
        System.loadLibrary("avutil-55");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("avformat-57");
        System.loadLibrary("avdevice-57");
        System.loadLibrary("swresample-2");
        System.loadLibrary("swscale-4");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("transfer");
    }

    public native int transferGif(String[] commands);

    private final static TransferGif transferGif = new TransferGif();

    private final static LinkedList<String> commandList = new LinkedList<>();

    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean transfer(@NonNull @Size(min = 1) String[] commands) {
        try {
            return transferGif.transferGif(commands) == 0;
        } catch (Exception e) {
            return false;
        } finally {
            commandList.clear();
        }
    }

    @SuppressLint({"Range", "MissingPermission"})
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean transfer(@NonNull @Size(min = 1) List<String> commandList) {
        String[] commands = new String[commandList.size()];
        for (int index = 0; index < commandList.size(); index++) {
            commands[index] = commandList.get(index);
        }
        return transfer(commands);
    }

    /**
     * transfer gif by default command
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean transfer(@NonNull String inputVideoPath, @NonNull String outputGifPath) {
        addHeadCommands(inputVideoPath);
        commandList.add("-filter:v");
        commandList.add("setpts=0.5*PTS");
        commandList.add("-s");
        commandList.add("480x720");
        commandList.add("-r");
        commandList.add("10");
        commandList.add("-b");
        commandList.add("200k");
        addEndCommands(outputGifPath);
        return transfer(commandList);
    }

    /**
     * transfer gif by optimize command, improve the quality of gif
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean transfer(@NonNull String inputVideoPath, @NonNull String outputGifPath, @NonNull String palettePath) {
        return transfer(inputVideoPath, outputGifPath, palettePath, 10, 270);
    }

    /**
     * transfer gif by optimize command, improve the quality of gif
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(allOf = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean transfer(@NonNull String inputVideoPath, @NonNull String outputGifPath, @NonNull String palettePath, int fps, int scaleWidth) {
        addHeadCommands(inputVideoPath);
        commandList.add("-vf");
        commandList.add("fps=" + fps + ",scale=" + scaleWidth + ":-1:flags=lanczos,palettegen");
        commandList.add("-y");
        commandList.add(palettePath);
        transfer(commandList);
        addHeadCommands(inputVideoPath);
        commandList.add("-i");
        commandList.add(palettePath);
        commandList.add("-r");
        commandList.add(String.valueOf(fps));
        commandList.add("-lavfi");
        commandList.add("fps=" + fps + ",scale=" + scaleWidth + ":-1:flags=lanczos [x]; [x][1:v] paletteuse");
        addEndCommands(outputGifPath);
        return transfer(commandList);
    }

    public static void addHeadCommands(@NonNull String inputVideoPath) {
        commandList.add("ffmpeg");
        commandList.add("-v");
        commandList.add("warning");
        commandList.add("-i");
        commandList.add(inputVideoPath);
    }

    public static void addEndCommands(@NonNull String outputGifPath) {
        commandList.add("-an");
        commandList.add("-y");
        commandList.add(outputGifPath);
    }
}
