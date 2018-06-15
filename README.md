### 基于FFmpeg提供一个能将视频文件转换为gif图片的库

#### [关于使用FFmpeg转换gif的相关指令](FFMPEG.md)

#### [Mac环境下编译及移植FFmpeg so库到Android](https://github.com/HelloHuDi/NDK_Learn/tree/master/Third_Learn)

#### dependencies :

```
dependencies {
    //...
    implementation 'com.hd:transfer:1.0'
}
```

#### code :

```
TransferGif.transfer(String[] commands);
```

### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
