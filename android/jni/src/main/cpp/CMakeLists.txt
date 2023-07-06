cmake_minimum_required(VERSION 3.22.1)

project("Native")

add_library(
        native
        SHARED
        native-lib.cpp
)

find_library(
        log-lib
        log
)

target_link_libraries(
        native
        ${log-lib}
)