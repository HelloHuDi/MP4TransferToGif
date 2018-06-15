# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_VERBOSE_MAKEFILE on)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/${ANDROID_ABI})

set(LINK_DIR ${CMAKE_CURRENT_LIST_DIR}/../lib/${ANDROID_ABI})
link_directories(${LINK_DIR})

set(INCLUDE_DIR "/Users/hd/androidWorkspace/androidStudio/about_video_audio/ffmpeg-3.4.2")
include_directories(${CMAKE_CURRENT_LIST_DIR} ${INCLUDE_DIR})

aux_source_directory(${CMAKE_CURRENT_LIST_DIR} SOURCE_DIR)

add_library( # Sets the name of the library.
             transfer

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             ${SOURCE_DIR} )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       transfer

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                        avcodec-57
                        avdevice-57
                        avfilter-6
                        avformat-57
                        avutil-55
                        swresample-2
                        swscale-4
                        )