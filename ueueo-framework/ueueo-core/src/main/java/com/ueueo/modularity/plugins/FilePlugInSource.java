package com.ueueo.modularity.plugins;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 未实现
 *
 * @author Lee
 * @date 2022-05-29 13:03
 */
public class FilePlugInSource implements IPlugInSource{

    private final List<String> filePaths;

    public FilePlugInSource(List<String> filePaths) {
        this.filePaths = filePaths!= null? filePaths:new ArrayList<>();
    }

    @Override
    public List<Class<?>> getModules() {
        List<Class<?>> modules = new ArrayList<>();
        for(String filePath : filePaths){
            //TODO 遍历本地文件，加载所有类
        }
        return modules;
    }
}
