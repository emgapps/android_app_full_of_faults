#include <jni.h>
#include <string>
#include <signal.h>

extern "C"
JNIEXPORT void JNICALL
Java_com_example_faultgenlib_NativeLib_genInvalidWrite(JNIEnv *env, jobject thiz) {
    volatile int* p = reinterpret_cast<volatile int*>(0);
    *p = 11223;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_faultgenlib_NativeLib_genStackOverflow(JNIEnv *env, jobject thiz) {
    Java_com_example_faultgenlib_NativeLib_genStackOverflow(env, thiz);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_faultgenlib_NativeLib_genSigAbort(JNIEnv *env, jobject thiz) {
    std::abort();
}
