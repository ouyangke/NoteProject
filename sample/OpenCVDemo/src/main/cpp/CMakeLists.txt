# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html.
# For more examples on how to use CMake, see https://github.com/android/ndk-samples.
###########################################基础配置##################################################
# 设置Cmake最低版本
cmake_minimum_required(VERSION 3.22.1)

# 设置项目名
project("opencv_utils")

# 输出日志
message(STATUS "******************************************************************")
message(STATUS "CMAKE_SOURCE_DIR->" ${CMAKE_SOURCE_DIR})
message(STATUS "PROJECT_SOURCE_DIR->)" ${PROJECT_SOURCE_DIR})
message(STATUS "******************************************************************")

# 设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI}/)

# 设置opencv头文件目录
set(CMAKE_OPENCV_INCLUDE_DIRECTORY ${CMAKE_SOURCE_DIR}/include/)

# 设置opencv库文件目录
set(CMAKE_OPENCV_LIBRARY_DIRECTORY ${CMAKE_SOURCE_DIR}/libs/${CMAKE_ANDROID_ARCH_ABI}/)

#########################################第三方库添加#################################################
# 1.设置第三方库头文件目录路径
include_directories(${CMAKE_OPENCV_INCLUDE_DIRECTORY})

# 2.导入第三方库文件
#########################第一种方式导入#########################################
# 2.1.1添加第三方库
add_library(
        # 库名称
        opencv_java4
        # 静态 or 动态
        SHARED
        # 导入模式
        IMPORTED
)
# 2.1.2设置库属性(配置库文件路径)
set_target_properties(
        opencv_java4
        PROPERTIES IMPORTED_LOCATION
        ${CMAKE_OPENCV_LIBRARY_DIRECTORY}/libopencv_java4.so
)
#########################第二种方式导入############################################
# 2.2.1 配置要链接的库目录
#link_directories(${CMAKE_OPENCV_LIBRARY_DIRECTORY})
# 2.2.2 配置要链接的库列表
#link_libraries(libopencv_java4.so)

##########################################打包自己的库################################################

# 添加库
add_library(
        # 库名称
        ${CMAKE_PROJECT_NAME}
        # 静态库 or 动态库
        SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        OpenCVUtils.cpp)

# 查找库(一般用于查找系统内置库)
find_library(
        # 库名
        log-lib
        # 路径列表
        log
)

# 链接库(把系统库或者第三方库链接到自己的库)
target_link_libraries(
        #目标库
        ${CMAKE_PROJECT_NAME}
        # 需要链接到目标库的列表
        opencv_java4
        android
        jnigraphics
        ${log-lib})