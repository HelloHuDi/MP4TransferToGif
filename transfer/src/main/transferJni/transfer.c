#include <jni.h>
#include "ffmpeg.h"

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNICALL
Java_com_hd_transfer_TransferGif_transferGif(JNIEnv *env, jobject instance, jobjectArray commands) {
    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char *) (*env)->GetStringUTFChars(env, js, 0);
    }
    return main(argc, argv);

}

#ifdef __cplusplus
}
#endif