package io.github.eterverda.playless;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import io.github.eterverda.util.checksum.Checksum;

public final class InstalledDistribution {
    @NotNull
    public final String applicationId;
    public final int versionCode;
    @NotNull
    public final String versionName;
    public final boolean locked;
    @Nullable
    public final Checksum checksum;
    public final boolean debug;

    private InstalledDistribution(
            @NotNull PackageInfo info,
            boolean locked,
            @Nullable Checksum checksum) {

        this(
                info.packageName,
                info.versionCode,
                info.versionName,
                locked,
                checksum,
                (info.applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }

    public InstalledDistribution(
            @NotNull String applicationId,
            int versionCode,
            @NotNull String versionName,
            boolean locked,
            @Nullable Checksum checksum,
            boolean debug) {

        this.applicationId = applicationId;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.locked = locked;
        this.checksum = checksum;
        this.debug = debug;
    }

    public static InstalledDistribution load(PackageManager packageManager, String packageName) throws PackageManager.NameNotFoundException {
        final @NotNull PackageInfo info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        final @Nullable Checksum checksum = checksum(new File(info.applicationInfo.sourceDir));
        final boolean locked = checksum != null;

        return new InstalledDistribution(
                info,
                locked,
                checksum);
    }

    @SuppressLint("NewApi")
    @Nullable
    private static Checksum checksum(@NotNull File sourceFile) {
        try (InputStream in = new FileInputStream(sourceFile)) {
            return Checksum.sha1(in);

        } catch (Exception e) {
            return null;
        }
    }
}