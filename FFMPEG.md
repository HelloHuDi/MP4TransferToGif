## 记录ffmpeg mp4=>gif 常用指令

```
//加速 16帧 分辨率 200比特率 禁止音频 覆盖
ffmpeg -v warning -i input.mp4 -filter:v "setpts=0.2*PTS" -s 1080x1920 -r 15 -b 200k -an -y output.gif
```

## 下面是基本操作

### 缩放视频尺寸,注意 sacle 值必须是偶数，这里的 -1 表示保持长宽比，根据宽度值自适应高度。如果要求压缩出来的视频尺寸长宽都保持为偶数，可以使用 -2
```
ffmpeg -i input.mp4 -vf scale=360:-1 output.mov
```

### 调整倍速播放视频(0.5可调整，值越大播放倍速越慢，越小越快)
```
ffmpeg -i input.mp4 -filter:v "setpts=0.5*PTS" output.gif
```

### 调整帧率 16fps:
```
ffmpeg -i input.mp4 -r 16 output.gif
```

### 调整GIF转换质量(2048可调整，越小质量越低)
```
ffmpeg -i input.mp4 -b 2048k output.gif
```

### 转化视频中的一部分为 GIF (从视频中第二秒开始，截取时长为3秒的片段转化为 gif)
```
ffmpeg -ss 00:00:02 -t 3 -i input.mp4 output.gif
```

### 静音视频（移除视频中的音频）,适用于转换成其他视频文件
```
ffmpeg -i input.mp4 -an output.mp4
```

### 将 GIF 转化为 MP4 (也可以将 gif 转为其他视频格式)
```
ffmpeg -f gif -i input.gif output.mp4

ffmpeg -f gif -i input.gif output.mpeg

ffmpeg -f gif -i input.gif output.webm
```

### GIF 转出来的 MP4 播放不了,需要添加 pixel formal 参数 (使用 yunv420p 需要保证长宽为偶数，这里同时使用了 scale=420:-2)
```
ffmpeg -i input.gif -vf scale=420:-2,format=yuv420p output.mp4
```

## imagemagick 指令

### 使用 imagemagick 对gif进行压缩 (15% 根据实际情况调整 ，此方法对于从视频转换来的gif作用不大,[参考][0])
```
convert input.gif -fuzz 15% -layers Optimize output.gif
```

### 提取第一帧
```
convert 'input.gif[0]' output.gif
```

# **参考资料**

[FFmpeg CompilationGuide/MacOSX][1]

[Convert Video to GIF or GIF to Video][2]

[Get the First Frame of an Animated GIF with ImageMagick][3]

[Create an Image Preview from a Video][4]

[How to speed up / slow down a video][5]

[ffmpeg useful commands (FFMPEG 命令大全)][6]

[ffmpeg 文档][7]

[0]: https://segmentfault.com/a/1190000000436384
[1]: https://trac.ffmpeg.org/wiki/CompilationGuide/MacOSX
[2]: http://davidwalsh.name/convert-video-gif
[3]: http://davidwalsh.name/first-frame-animated-gif
[4]: http://davidwalsh.name/create-image-preview-video
[5]: https://trac.ffmpeg.org/wiki/How%20to%20speed%20up%20/%20slow%20down%20a%20video
[6]: http://siwei.me/blog/posts/ffmpeg-useful-commands
[7]: http://siwei.me/blog/posts/ffmpeg-useful-commands