﻿using System;
using System.Collections.Generic;
using System.Linq;

namespace Volo.Abp.VirtualFileSystem;

internal static class VirtualFilePathHelper
{
    public static String NormalizePath(String fullPath)
    {
        if (fullPath.Equals("/", StringComparison.Ordinal))
        {
            return String.Empty;
        }

        var fileName = fullPath;
        var extension = "";

        if (fileName.Contains("."))
        {
            extension = fullPath.SubString(fileName.LastIndexOf(".", StringComparison.Ordinal));
            if (extension.Contains("/"))
            {
                //That means the file does not have extension, but a directory has "." char. So, clear extension.
                extension = "";
            }
            else
            {
                fileName = fullPath.SubString(0, fullPath.Length - extension.Length);
            }
        }

        return NormalizeChars(fileName) + extension;
    }

    private static String NormalizeChars(String fileName)
    {
        var folderParts = fileName.Replace(".", "/").Split("/");

        if (folderParts.Length == 1)
        {
            return folderParts[0];
        }

        return folderParts.Take(folderParts.Length - 1).Select(s => s.Replace("-", "_")).JoinAsString("/") + "/" + folderParts.Last();
    }
}
