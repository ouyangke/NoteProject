#include <jni.h>
#include <string>
#include "opencv2/imgproc/types_c.h"
#include "opencv2/imgproc.hpp"
#include "opencv2/calib3d.hpp"

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_example_opencvdemo_NativeLib_bitmap2Grey(JNIEnv *env, jobject thiz, jintArray pixels,jint w, jint h) {

    jint *cbuf;
    jboolean ptfalse = false;
    cbuf = env->GetIntArrayElements(pixels, &ptfalse);
    if(cbuf == NULL){
        return 0;
    }

    cv::Mat imgData(h, w, CV_8UC4, (unsigned char*)cbuf);
    // 注意，Android的Bitmap是ARGB四通道,而不是RGB三通道
    cvtColor(imgData,imgData,CV_BGRA2GRAY);
    cvtColor(imgData,imgData,CV_GRAY2BGRA);

    int size=w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, (jint*)imgData.data);
    env->ReleaseIntArrayElements(pixels, cbuf, 0);
    return result;
}