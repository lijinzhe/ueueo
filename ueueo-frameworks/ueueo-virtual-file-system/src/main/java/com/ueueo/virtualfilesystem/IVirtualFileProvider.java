package com.ueueo.virtualfilesystem;

import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public interface IVirtualFileProvider {

    File getDirectoryInfo(String path);

    File getFileInfo(String path);

    void watch(FileAlterationObserver observer);
}
