// IKtvController.aidl
package com.oyke.service;
import com.oyke.service.KtvData;
// Declare any non-default types here with import statements

interface IKtvController {

        void setPlay(String play);

        void setPause(String pause);

        int add(int arg1, int arg2);

        String inKtvInfo(in KtvData ktvData);

        String outKtvInfo(out KtvData ktvData);

        String inOutKtvInfo(inout KtvData ktvData);

}