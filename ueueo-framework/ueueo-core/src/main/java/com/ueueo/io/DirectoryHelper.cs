using System;
using System.IO;
using JetBrains.Annotations;

namespace Volo.Abp.IO;

/**
 * A helper class for Directory operations.
*/
public static class DirectoryHelper
{
    public static void CreateIfNotExists(String directory)
    {
        if (!Directory.Exists(directory))
        {
            Directory.CreateDirectory(directory);
        }
    }

    public static void DeleteIfExists(String directory)
    {
        if (Directory.Exists(directory))
        {
            Directory.Delete(directory);
        }
    }

    public static void DeleteIfExists(String directory, boolean recursive)
    {
        if (Directory.Exists(directory))
        {
            Directory.Delete(directory, recursive);
        }
    }

    public static void CreateIfNotExists(DirectoryInfo directory)
    {
        if (!directory.Exists)
        {
            directory.Create();
        }
    }

    public static boolean IsSubDirectoryOf(@Nonnull String parentDirectoryPath, @Nonnull String childDirectoryPath)
    {
        Objects.requireNonNull(parentDirectoryPath, nameof(parentDirectoryPath));
        Objects.requireNonNull(childDirectoryPath, nameof(childDirectoryPath));

        return IsSubDirectoryOf(
            new DirectoryInfo(parentDirectoryPath),
            new DirectoryInfo(childDirectoryPath)
        );
    }

    public static boolean IsSubDirectoryOf(@Nonnull DirectoryInfo parentDirectory,
        @Nonnull DirectoryInfo childDirectory)
    {
        Objects.requireNonNull(parentDirectory, nameof(parentDirectory));
        Objects.requireNonNull(childDirectory, nameof(childDirectory));

        if (parentDirectory.FullName == childDirectory.FullName)
        {
            return true;
        }

        var parentOfChild = childDirectory.Parent;
        if (parentOfChild == null)
        {
            return false;
        }

        return IsSubDirectoryOf(parentDirectory, parentOfChild);
    }

    public static IDisposable ChangeCurrentDirectory(String targetDirectory)
    {
        var currentDirectory = Directory.GetCurrentDirectory();

        if (currentDirectory.Equals(targetDirectory, StringComparison.OrdinalIgnoreCase))
        {
            return NullDisposable.Instance;
        }

        Directory.SetCurrentDirectory(targetDirectory);

        return new DisposeAction(() => { Directory.SetCurrentDirectory(currentDirectory); });
    }
}
