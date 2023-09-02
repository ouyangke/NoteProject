# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.

# 设置Cmake最低版本
cmake_minimum_required(VERSION 3.22.1)

# 设置项目名
project("opencvdemo")

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../../../libs/${ANDROID_ABI})

# 设置头文件目录路径
include_directories(${CMAKE_SOURCE_DIR}/include)

# 添加库
add_library(
        # 库名称
        libopencv_java4
        # 静态 or 动态
        SHARED
        # 导入模式
        IMPORTED
)

# 设置动态库路径
set_target_properties(libopencv_java4
        PROPERTIES
        IMPORTED_LOCATION
        ${CMAKE_SOURCE_DIR}/../../../libs/${CMAKE_ANDROID_ARCH_ABI}/libopencv_java4.so
        )

# 添加库
add_library(
        # 库名称
        ${CMAKE_PROJECT_NAME}
        # 静态库 or 动态库
        SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        opencvdemo.cpp)

# 链接库
target_link_libraries(${CMAKE_PROJECT_NAME}
        # List libraries link to the target library
        libopencv_java4
        android
        log)