#include <jni.h>
#include <android/log.h>
#include <android/bitmap.h>

#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>
#include <opencv2/imgproc/types_c.h>

#define TAG    "OpenCVDemo"
#define AndroidLOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

/**
 * 创建Bitmap
 * @param env
 * @param width
 * @param height
 * @param config
 * @return
 */
jobject createBitmap(JNIEnv *env, int width, int height, std::string config) {
    jclass bitmapConfig = env->FindClass("android/graphics/Bitmap$Config");
    jfieldID configFieldID = env->GetStaticFieldID(bitmapConfig, config.c_str(),
                                                   "Landroid/graphics/Bitmap$Config;");
    jobject rgb565Obj = env->GetStaticObjectField(bitmapConfig, configFieldID);
    jclass bitmapClass = env->FindClass("android/graphics/Bitmap");
    jmethodID createBitmapMethodID = env->GetStaticMethodID(bitmapClass, "createBitmap",
                                                            "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");
    jobject bitmapObj = env->CallStaticObjectMethod(bitmapClass, createBitmapMethodID,
                                                    width, height, rgb565Obj);
    env->DeleteLocalRef(bitmapConfig);
    env->DeleteLocalRef(bitmapClass);
    return bitmapObj;
}

/**
 * bitmap转mat
 * @param env
 * @param bitmap
 * @param dst
 */
void bitmapToMat(JNIEnv *env, jobject bitmap, cv::Mat &dst) {
    AndroidBitmapInfo info;
    void *pixels = 0;
    try {
        CV_Assert(AndroidBitmap_getInfo(env, bitmap, &info) >= 0);
        CV_Assert(info.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                  info.format == ANDROID_BITMAP_FORMAT_RGB_565);
        CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
        CV_Assert(pixels);
        dst.create(info.height, info.width, CV_8UC4);
        if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
            cv::Mat tmp(info.height, info.width, CV_8UC4, pixels);
            tmp.copyTo(dst);
        } else {
            cv::Mat tmp(info.height, info.width, CV_8UC2, pixels);
            cvtColor(tmp, dst, CV_BGR5652RGBA);
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nBitmapToMat}");
        return;
    }
}

/**
 * mat转bitmap
 * @param env
 * @param src
 * @param bitmap
 */
void matToBitmap(JNIEnv *env, cv::Mat &src, jobject bitmap) {
    AndroidBitmapInfo info;
    void *pixels = 0;
    try {
        if (AndroidBitmap_getInfo(env, bitmap, &info) < 0) {
            return;
        }
        if (info.format != ANDROID_BITMAP_FORMAT_RGBA_8888 &&
            info.format != ANDROID_BITMAP_FORMAT_RGB_565) {
            return;
        }
        if (src.dims != 2 || info.height != (uint32_t) src.rows ||
            info.width != (uint32_t) src.cols) {
            return;
        }
        if (src.type() != CV_8UC1 && src.type() != CV_8UC3 && src.type() != CV_8UC4) {
            return;
        }
        if (AndroidBitmap_lockPixels(env, bitmap, &pixels) < 0) {
            return;
        }
        if (pixels == 0) {
            return;
        }
        if (info.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
            cv::Mat tmp(info.height, info.width, CV_8UC4, pixels);
            if (src.type() == CV_8UC1) {
                cvtColor(src, tmp, CV_GRAY2RGBA);
            } else if (src.type() == CV_8UC3) {
                cvtColor(src, tmp, CV_RGB2RGBA);
            } else if (src.type() == CV_8UC4) {
                src.copyTo(tmp);
            }
        } else {
            // info.format == ANDROID_BITMAP_FORMAT_RGB_565
            cv::Mat tmp(info.height, info.width, CV_8UC2, pixels);
            if (src.type() == CV_8UC1) {
                cvtColor(src, tmp, CV_GRAY2BGR565);
            } else if (src.type() == CV_8UC3) {
                cvtColor(src, tmp, CV_RGB2BGR565);
            } else if (src.type() == CV_8UC4) {
                cvtColor(src, tmp, CV_RGBA2BGR565);
            }
        }
        AndroidBitmap_unlockPixels(env, bitmap);
        return;
    } catch (const cv::Exception &e) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("org/opencv/core/CvException");
        if (!je) je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, e.what());
        return;
    } catch (...) {
        AndroidBitmap_unlockPixels(env, bitmap);
        jclass je = env->FindClass("java/lang/Exception");
        env->ThrowNew(je, "Unknown exception in JNI code {nMatToBitmap}");
        return;
    }
}

/**
* 锐化核函数实现
**/
void sharpen(const cv::Mat &myImage, cv::Mat &Result) {
    CV_Assert(myImage.depth() == CV_8U);  // 仅接受uchar图像
    Result.create(myImage.size(), myImage.type());
    const int nChannels = myImage.channels();
    for (int j = 1; j < myImage.rows - 1; ++j) {
        // 矩阵中的当前点
        const uchar *previous = myImage.ptr<uchar>(j - 1);
        // 矩阵中当前点的前一个点（当前列-1）
        const uchar *current = myImage.ptr<uchar>(j);
        // 矩阵中当前点的下一个点（当前列+1）
        const uchar *next = myImage.ptr<uchar>(j + 1);
        uchar *output = Result.ptr<uchar>(j);
        for (int i = nChannels; i < nChannels * (myImage.cols - 1); ++i) {
            *output++ = cv::saturate_cast<uchar>(5 * current[i]
                                                 - current[i - nChannels] - current[i + nChannels] -
                                                 previous[i] - next[i]);
            // 或者使用其他锐化核函数
//            *output++ = cv::saturate_cast<uchar>(9 * current[i]
//                                                 - current[i - nChannels] - current[i + nChannels]
//                                                 -previous[i] - previous[i - nChannels] - previous[i + nChannels]
//                                                 -next[i] - next[i - nChannels] - next[i + nChannels]);
        }
    }
    // 不对边界点使用掩码，直接把它们设为0
    Result.row(0).setTo(cv::Scalar(0)); // 上边界
    Result.row(Result.rows - 1).setTo(cv::Scalar(0)); // 下边界
    Result.col(0).setTo(cv::Scalar(0)); // 左边界
    Result.col(Result.cols - 1).setTo(cv::Scalar(0));// 右边界
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_bitmap2Mat(JNIEnv *env, jobject thiz,
                                                                  jobject bitmap) {
    cv::Mat rgbMat;
    // 把java层的bitmap转换为Mat
    bitmapToMat(env, bitmap, rgbMat);
    // log输出Mat的信息
    AndroidLOGD("native mat channels:%d, cols:%d, rows:%d", rgbMat.channels(), rgbMat.cols,
                rgbMat.rows);
}



extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_greyBitmap(JNIEnv *env, jobject thiz,
                                                                  jobject bitmap) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    // 灰度图是单通道
    cv::Mat dst = cv::Mat(rgbMat.size(), CV_8UC1);
    // grey = 0.2126 * r + 0.7152 * g + 0.0722 * b;
    for (int y = 0; y < rgbMat.rows; y++) {
        for (int x = 0; x < rgbMat.cols; x++) {
            // 灰度图是单通道，所以取的(y,x)是uchar类型
            // rgbMat是四通道，所以取的(y,x)是Vec4b类型
            dst.at<uchar>(y, x) =
                    cv::saturate_cast<uchar>(
                            0.2126f * rgbMat.at<cv::Vec4b>(y, x)[0]
                            + 0.7152f * rgbMat.at<cv::Vec4b>(y, x)[1]
                            + 0.0722f * rgbMat.at<cv::Vec4b>(y, x)[2]);
        }
    }
    jobject resultBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, resultBitmap);
    return resultBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_lightenBitmap(JNIEnv *env, jobject thiz,
                                                                     jobject bitmap) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    // 创建一个同样大小的结果Mat
    cv::Mat dst = cv::Mat(rgbMat.size(), rgbMat.type());
    /// 执行运算 new_image(i,j) = alpha*image(i,j) + beta
    float alpha = 2.2f;
    int beta = 50;
    for (int y = 0; y < rgbMat.rows; y++) {
        for (int x = 0; x < rgbMat.cols; x++) {
            for (int c = 0; c < rgbMat.channels(); c++) {
                // Vec4b因为我们的使用的是RGBA通道，如果RGB则是Vec3b
                // 实现计算逻辑
                dst.at<cv::Vec4b>(y, x)[c] = cv::saturate_cast<uchar>(
                        alpha * (rgbMat.at<cv::Vec4b>(y, x)[c]) + beta);
            }
        }
    }
    jobject lightenBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, lightenBitmap);
    return lightenBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_sharpenBitmap(JNIEnv *env, jobject thiz,
                                                                     jobject bitmap) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst;
    sharpen(rgbMat, dst);
    jobject sharpenBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, sharpenBitmap);
    return sharpenBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_blurBitmap(JNIEnv *env, jobject thiz,
                                                                  jobject bitmap,
                                                                  jint kernel_length,
                                                                  jint type) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst = cv::Mat(rgbMat.size(), rgbMat.type());
    cv::Mat result = rgbMat.clone();
    // 双边平滑只支持1通道或者3通道
    if (type == 4) {
        cv::cvtColor(rgbMat, rgbMat, CV_RGBA2RGB);
    }
    for (int i = 1; i < kernel_length; i = i + 2) {
        switch (type) {
            case 1:
                // 均值平滑
                cv::blur(rgbMat, result, cv::Size(i, i), cv::Point(-1, -1));
                break;
            case 2:
                // 高斯平滑
                cv::GaussianBlur(rgbMat, result, cv::Size(i, i), 0, 0);
                break;
            case 3:
                // 中值平滑
                cv::medianBlur(rgbMat, result, i);
                break;
            case 4:
                // 双边平滑
                cv::bilateralFilter(rgbMat, result, i, i * 2, i / 2);
                break;
            default:
                break;
        }
    }
    jobject resultBitmap = createBitmap(env, result.cols, result.rows, "ARGB_8888");
    matToBitmap(env, result, resultBitmap);
    return resultBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_filter2DBitmap(JNIEnv *env, jobject thiz,
                                                                      jobject bitmap,
                                                                      jint kernel_size) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst = cv::Mat(rgbMat.size(), rgbMat.type());
    cv::Mat result = rgbMat.clone();
    /// 初始化滤波器参数
    cv::Point anchor = cv::Point(-1, -1);
    double delta = 0;
    int ddepth = -1;
    /// 更新归一化块滤波器的核大小
    cv::Mat kernel =
            cv::Mat::ones(kernel_size, kernel_size, CV_32F) / (float) (kernel_size * kernel_size);
    /// 使用滤波器
    filter2D(rgbMat, dst, ddepth, kernel, anchor, delta, cv::BORDER_DEFAULT);
    jobject resultBitmap = createBitmap(env, result.cols, result.rows, "ARGB_8888");
    matToBitmap(env, result, resultBitmap);
    return resultBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_erodeBitmap(JNIEnv *env, jobject thiz,
                                                                   jobject bitmap,
                                                                   jint kernel_size) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst;
    // 腐蚀操作的内核;如果不指定，默认为一个简单的 3x3 矩阵
    cv::Mat element = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(kernel_size, kernel_size));
    // 腐蚀操作
    cv::erode(rgbMat, dst, element);
    jobject resultBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, resultBitmap);
    return resultBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_dilateBitmap(JNIEnv *env, jobject thiz,
                                                                    jobject bitmap,
                                                                    jint kernel_size) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst;
    // 膨胀操作的内核;如果不指定，默认为一个简单的 3x3 矩阵
    cv::Mat element = cv::getStructuringElement(cv::MORPH_RECT, cv::Size(kernel_size, kernel_size));
    // 膨胀操作
    cv::dilate(rgbMat, dst, element);
    jobject resultBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, resultBitmap);
    return resultBitmap;
}
extern "C"
JNIEXPORT jobject JNICALL
Java_com_example_opencvdemo_OpenCVUtils_00024Companion_resizeBitmap(JNIEnv *env, jobject thiz,
                                                                    jobject bitmap, jfloat size,
                                                                    jint type) {
    cv::Mat rgbMat;
    bitmapToMat(env, bitmap, rgbMat);
    cv::Mat dst;
    //放大或者缩小操作
    cv::resize(rgbMat,dst,cv::Size(rgbMat.cols * size,rgbMat.rows * size));
    jobject resultBitmap = createBitmap(env, dst.cols, dst.rows, "ARGB_8888");
    matToBitmap(env, dst, resultBitmap);
    return resultBitmap;
}