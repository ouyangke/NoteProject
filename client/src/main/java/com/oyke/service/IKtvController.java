/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.oyke.service;
// Declare any non-default types here with import statements

public interface IKtvController extends android.os.IInterface
{
  /** Default implementation for IKtvController. */
  public static class Default implements IKtvController
  {
    @Override public void setPlay(String play) throws android.os.RemoteException
    {
    }
    @Override public void setPause(String pause) throws android.os.RemoteException
    {
    }
    @Override public int add(int arg1, int arg2) throws android.os.RemoteException
    {
      return 0;
    }
    @Override public String inKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
    {
      return null;
    }
    @Override public String outKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
    {
      return null;
    }
    @Override public String inOutKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
    {
      return null;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements IKtvController
  {
    private static final String DESCRIPTOR = "com.oyke.service.IKtvController";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.oyke.service.IKtvController interface,
     * generating a proxy if needed.
     */
    public static IKtvController asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof IKtvController))) {
        return ((IKtvController)iin);
      }
      return new Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_setPlay:
        {
          data.enforceInterface(descriptor);
          String _arg0;
          _arg0 = data.readString();
          this.setPlay(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_setPause:
        {
          data.enforceInterface(descriptor);
          String _arg0;
          _arg0 = data.readString();
          this.setPause(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_add:
        {
          data.enforceInterface(descriptor);
          int _arg0;
          _arg0 = data.readInt();
          int _arg1;
          _arg1 = data.readInt();
          int _result = this.add(_arg0, _arg1);
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        case TRANSACTION_inKtvInfo:
        {
          data.enforceInterface(descriptor);
          com.oyke.service.KtvData _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.oyke.service.KtvData.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          String _result = this.inKtvInfo(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          return true;
        }
        case TRANSACTION_outKtvInfo:
        {
          data.enforceInterface(descriptor);
          com.oyke.service.KtvData _arg0;
          _arg0 = new com.oyke.service.KtvData();
          String _result = this.outKtvInfo(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          if ((_arg0!=null)) {
            reply.writeInt(1);
            _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        case TRANSACTION_inOutKtvInfo:
        {
          data.enforceInterface(descriptor);
          com.oyke.service.KtvData _arg0;
          if ((0!=data.readInt())) {
            _arg0 = com.oyke.service.KtvData.CREATOR.createFromParcel(data);
          }
          else {
            _arg0 = null;
          }
          String _result = this.inOutKtvInfo(_arg0);
          reply.writeNoException();
          reply.writeString(_result);
          if ((_arg0!=null)) {
            reply.writeInt(1);
            _arg0.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
          }
          else {
            reply.writeInt(0);
          }
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements IKtvController
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void setPlay(String play) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(play);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPlay, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setPlay(play);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void setPause(String pause) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(pause);
          boolean _status = mRemote.transact(Stub.TRANSACTION_setPause, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().setPause(pause);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public int add(int arg1, int arg2) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(arg1);
          _data.writeInt(arg2);
          boolean _status = mRemote.transact(Stub.TRANSACTION_add, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().add(arg1, arg2);
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public String inKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((ktvData!=null)) {
            _data.writeInt(1);
            ktvData.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_inKtvInfo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().inKtvInfo(ktvData);
          }
          _reply.readException();
          _result = _reply.readString();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public String outKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_outKtvInfo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().outKtvInfo(ktvData);
          }
          _reply.readException();
          _result = _reply.readString();
          if ((0!=_reply.readInt())) {
            ktvData.readFromParcel(_reply);
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      @Override public String inOutKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        String _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          if ((ktvData!=null)) {
            _data.writeInt(1);
            ktvData.writeToParcel(_data, 0);
          }
          else {
            _data.writeInt(0);
          }
          boolean _status = mRemote.transact(Stub.TRANSACTION_inOutKtvInfo, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().inOutKtvInfo(ktvData);
          }
          _reply.readException();
          _result = _reply.readString();
          if ((0!=_reply.readInt())) {
            ktvData.readFromParcel(_reply);
          }
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static IKtvController sDefaultImpl;
    }
    static final int TRANSACTION_setPlay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_setPause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_inKtvInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_outKtvInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
    static final int TRANSACTION_inOutKtvInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
    public static boolean setDefaultImpl(IKtvController impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static IKtvController getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
  }
  public void setPlay(String play) throws android.os.RemoteException;
  public void setPause(String pause) throws android.os.RemoteException;
  public int add(int arg1, int arg2) throws android.os.RemoteException;
  public String inKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException;
  public String outKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException;
  public String inOutKtvInfo(com.oyke.service.KtvData ktvData) throws android.os.RemoteException;
}
