#include <jni.h>
#include <android/log.h>
#include <string>

#define TAG "oyk"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE,TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG,__VA_ARGS__)

static jobject sObject;

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *unused) {

}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_oyke_nativelib_NativeLib_stringFromJNI(JNIEnv *env, jobject instance) {
    LOGI("stringFormJNI %s", "dsafd");
    jclass a_class = env->GetObjectClass(instance);
    jmethodID a_method = env->GetMethodID(a_class, "stringFromJava", "()Ljava/lang/String;");
    jobject a_object = env->AllocObject(a_class);
    jstring a_string = (jstring) env->CallObjectMethod(a_object, a_method);
    char *print = (char *) env->GetStringUTFChars(a_string, 0);
    //创建一个全局引用
    sObject = env->NewGlobalRef(instance);
    return env->NewStringUTF(print);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_oyke_nativelib_NativeLib_staticStringFromJNI(JNIEnv *env, jclass type) {
    jmethodID jmethodId = env->GetMethodID(type, "stringFromJava", "()Ljava/lang/String;");
    jobject jobj = env->AllocObject(type);
    jstring jstr = (jstring) env->CallObjectMethod(jobj, jmethodId);
    char *print = (char *) env->GetStringUTFChars(jstr, 0);
    return env->NewStringUTF(print);
}
extern "C"
JNIEXPORT jlong JNICALL Java_com_oyke_nativelib_NativeLib_newJavaData(JNIEnv *env, jobject thiz) {
    jclass class_date = env->FindClass("java/util/Date");
    jmethodID method_init = env->GetMethodID(class_date, "<init>", "()V");
    jobject jobj = env->NewObject(class_date, method_init);
    jmethodID method_gettime = env->GetMethodID(class_date, "getTime", "()J");
    jlong long_time = env->CallLongMethod(jobj, method_gettime);
    return long_time;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_oyke_nativelib_NativeLib_callFatherMethod(JNIEnv *env, jobject thiz) {
    jclass clazz = env->GetObjectClass(thiz);
    jfieldID jfield = env->GetFieldID(clazz, "father", "Lcom/oyke/nativelib/Father;");
    jobject jobj_father = env->GetObjectField(thiz, jfield);
    jclass jclass_father = env->FindClass("com/oyke/nativelib/Father");
    jmethodID method_tostring = env->GetMethodID(jclass_father, "toString", "()Ljava/lang/String;");
    jstring prin = (jstring) env->CallNonvirtualObjectMethod(jobj_father, jclass_father,
                                                             method_tostring);
    return prin;
}
extern "C"
JNIEXPORT void JNICALL
Java_com_oyke_nativelib_NativeLib_changeNumber(JNIEnv *env, jobject thiz) {
    jclass clazz = env->GetObjectClass(thiz);
    jfieldID number = env->GetFieldID(clazz, "number", "I");
    env->SetIntField(thiz, number, 100);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_oyke_nativelib_NativeLib_getNativeString(JNIEnv *env, jobject thiz) {
    jchar *data = new jchar[7];
    data[0] = 'o';
    data[1] = 'y';
    data[2] = 'k';
    data[3] = '1';
    data[4] = '3';
    data[5] = '1';
    data[6] = '4';
    return env->NewString(data, 5);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_oyke_nativelib_NativeLib_getNativeStingUTF(JNIEnv *env, jobject thiz) {
    std::string data = "oyk 1314";
    return env->NewStringUTF(data.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_oyke_nativelib_NativeLib_getStringLength(JNIEnv *env, jobject thiz, jstring string) {
    jint length = env->GetStringLength(string);
    jint lengthUTF = env->GetStringUTFLength(string);
    return length;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_oyke_nativelib_NativeLib_getStringUTFLength(JNIEnv *env, jobject thiz,
                                                     jstring string_utf) {
    jint length = env->GetStringLength(string_utf);
    jint lengthUTF = env->GetStringUTFLength(string_utf);
    return lengthUTF;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_oyke_nativelib_NativeLib_testThrowException(JNIEnv *env, jobject thiz) {
    jclass clazz = env->GetObjectClass(thiz);
    jmethodID method = env->GetMethodID(clazz, "throwException", "()V");
    if (method == nullptr) return;
    env->CallVoidMethod(thiz, method);
    jthrowable jthrowable1 = env->ExceptionOccurred();
    if (jthrowable1) {
        jclass newExcCls;
        env->ExceptionDescribe();//打印异常堆栈信息
        env->ExceptionClear();
        jclass newExcClazz = env->FindClass("java/lang/IllegalArgumentException");
        if (newExcClazz == NULL) return;
        env->ThrowNew(newExcClazz, "this is a IllegalArgumentException");
    }

}