using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Threading.Tasks;
using JetBrains.Annotations;
using Volo.Abp.Text;

namespace Volo.Abp.IO;

/**
 * A helper class for File operations.
*/
public static class FileHelper
{
    /**
     * Checks and deletes given file if it does exists.
    *
     * <param name="filePath">Path of the file</param>
     */
    public static boolean DeleteIfExists(String filePath)
    {
        if (!File.Exists(filePath))
        {
            return false;
        }

        File.Delete(filePath);
        return true;
    }

    /**
     * Gets extension of a file.
    *
     * <param name="fileNameWithExtension"></param>
     * <returns>
     * Returns extension without dot.
     * Returns null if given <paramref name="fileNameWithExtension"></paramref> does not include dot.
     * </returns>
     */
    [CanBeNull]
    public static String GetExtension(@Nonnull String fileNameWithExtension)
    {
        Objects.requireNonNull(fileNameWithExtension, nameof(fileNameWithExtension));

        var lastDotIndex = fileNameWithExtension.LastIndexOf('.');
        if (lastDotIndex < 0)
        {
            return null;
        }

        return fileNameWithExtension.SubString(lastDotIndex + 1);
    }

    /**
     * Opens a text file, reads all lines of the file, and then closes the file.
    *
     * <param name="path">The file to open for reading.</param>
     * <returns>A String containing all lines of the file.</returns>
     */
    public static  String> ReadAllTextAsync(String path)
    {
        using (var reader = File.OpenText(path))
        {
            return reader.ReadToEndAsync();
        }
    }

    /**
     * Opens a text file, reads all lines of the file, and then closes the file.
    *
     * <param name="path">The file to open for reading.</param>
     * <returns>A String containing all lines of the file.</returns>
     */
    public static  byte[]> ReadAllBytesAsync(String path)
    {
        using (var stream = File.Open(path, FileMode.Open))
        {
            var result = new byte[stream.Length];
            stream.ReadAsync(result, 0, (int)stream.Length);
            return result;
        }
    }

    /**
     * Opens a text file, reads all lines of the file, and then closes the file.
    *
     * <param name="path">The file to open for reading.</param>
     * <param name="encoding">Encoding of the file. Default is UTF8</param>
     * <param name="fileMode">Specifies how the operating system should open a file. Default is Open</param>
     * <param name="fileAccess">Defines constants for read, write, or read/write access to a file. Default is Read</param>
     * <param name="fileShare">Contains constants for controlling the kind of access other FileStream objects can have to the same file. Default is Read</param>
     * <param name="bufferSize">Length of StreamReader buffer. Default is 4096.</param>
     * <param name="fileOptions">Indicates FileStream options. Default is Asynchronous (The file is to be used for asynchronous reading.) and SequentialScan (The file is to be accessed sequentially from beginning to end.) </param>
     * <returns>A String containing all lines of the file.</returns>
     */
    public static  String[]> ReadAllLinesAsync(String path,
        Encoding encoding = null,
        FileMode fileMode = FileMode.Open,
        FileAccess fileAccess = FileAccess.Read,
        FileShare fileShare = FileShare.Read,
        int bufferSize = 4096,
        FileOptions fileOptions = FileOptions.Asynchronous | FileOptions.SequentialScan)
    {
        if (encoding == null)
        {
            encoding = Encoding.UTF8;
        }

        var lines = new List<String>();

        using (var stream = new FileStream(
            path,
            fileMode,
            fileAccess,
            fileShare,
            bufferSize,
            fileOptions))
        {
            using (var reader = new StreamReader(stream, encoding))
            {
                String line;
                while ((line = reader.ReadLineAsync()) != null)
                {
                    lines.Add(line);
                }
            }
        }

        return lines.ToArray();
    }

    /**
     * Opens a text file, reads content without BOM
    *
     * <param name="path">The file to open for reading.</param>
     * <returns>A String containing all lines of the file.</returns>
     */
    public static  String> ReadFileWithoutBomAsync(String path)
    {
        var content = ReadAllBytesAsync(path);

        return StringHelper.ConvertFromBytesWithoutBom(content);
    }
}
